/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.tsf.container.config.RancherKubernetesConfig;
import com.tencent.tsf.container.config.RancherServerPath;
import com.tencent.tsf.container.dto.ClusterInfoDto;
import com.tencent.tsf.container.dto.ClusterNodeDto;
import com.tencent.tsf.container.dto.ClusterVMDto;
import com.tencent.tsf.container.models.Capacity;
import com.tencent.tsf.container.models.ClusterInfo;
import com.tencent.tsf.container.models.Limits;
import com.tencent.tsf.container.models.Requested;
import com.tencent.tsf.container.service.ClusterManagerService;
import com.tencent.tsf.container.utils.HttpClientUtil;
import com.tencent.tsf.container.utils.SSHHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @version Version 1.0
 * @title ClusterManagerServiceImpl
 * @title Ethan
 * @date 2019/3/29 13:20
 * @description TODO
 */

@Slf4j
@Service
public class ClusterManagerServiceImpl implements ClusterManagerService {

	@Autowired
	private RancherServerPath rancherServerPath;

	@Autowired
	private RancherKubernetesConfig rancherKubernetesConfig;

	private final String NODE_ROLE_MASTER = " --etcd --controlplane";
	private final String NODE_ROLE_NODE = " --worker";


	@Override
	public String createCluster(Map<String, String> headers, String name) {
		Assert.hasLength(name, "集群名称不能为空！");

		RancherKubernetesConfig config = new RancherKubernetesConfig();
		BeanUtils.copyProperties(rancherKubernetesConfig, config);
		String param = createClusterDefaultParam(name, config);
		headers.put("Content-Type", "application/json");
		String url = rancherServerPath.createClusterUrl();
		String result = HttpClientUtil.doPost(url, headers, param);
		JSONObject obj = JSON.parseObject(result);
		String id = obj.getString("id");
		return "{\"id\": \"" + id + "\"}";
	}

	private String createClusterDefaultParam(String name, RancherKubernetesConfig config) {
		Map<String, Object> param = new HashMap<>();
		param.put("name", name);
		param.put("dockerRootDir", "/var/lib/docker");
		param.put("enableNetworkPolicy", false);
		param.put("type", "cluster");
		param.put("rancherKubernetesEngineConfig", config);
		return JSON.toJSONString(param);
	}

	@Override
	public String getClusterById(Map<String, String> headers, String clusterId) {
		Assert.hasLength(clusterId, "集群ID不能为空！");

		String url = rancherServerPath.clusterInfoUrl(clusterId);
		String result = HttpClientUtil.doGet(url, headers);
		return result;
	}

	@Override
	public List<ClusterInfoDto> getClusters(final Map<String, String> headers, Map<String, Object> params) {
		String url = rancherServerPath.getAllClustersUrl(params);
		String result = HttpClientUtil.doGet(url, headers);
		JSONObject resultObj = JSON.parseObject(result);
		JSONArray clusterList = resultObj.getJSONArray("data");
		if (clusterList == null || clusterList.size() == 0) {
			return Collections.EMPTY_LIST;
		}

		List<ClusterInfoDto> list = new ArrayList<>();

		clusterList.stream().forEach(it -> {
			ClusterInfoDto clusterInfoDto = new ClusterInfoDto();

			JSONObject cluster = (JSONObject) it;
			getClusterBaseInfo(clusterInfoDto, cluster);

			String id = cluster.getString("id");
			getNodePodInfo(id, headers, clusterInfoDto);

			list.add(clusterInfoDto);
		});


		return list;
	}

	private void getClusterBaseInfo(ClusterInfoDto clusterInfoDto, JSONObject cluster) {
		String createdAt = cluster.getString("created");
		String id = cluster.getString("id");
		String name = cluster.getString("name");
		JSONObject requested = cluster.getJSONObject("requested");
		String pods = "0";
		String status = "";
		if (requested != null) {
			pods = requested.getString("pods");
		}
		Integer runningPodNum = Integer.valueOf(pods);
		String state = cluster.getString("state");
		if (StringUtils.isNotBlank(state)) {
			switch (state) {
//					Uninitialized
//				    Creating
//					Running
//					Abnormal
				case "active":
					status = "Running";
			}
		}
		clusterInfoDto.setCreatedAt(createdAt);
		clusterInfoDto.setId(id);
		clusterInfoDto.setName(name);
		clusterInfoDto.setRunningPodNum(runningPodNum);
		clusterInfoDto.setStatus(status);
	}

	private void getNodePodInfo(String id, Map<String, String> headers, ClusterInfoDto clusterInfoDto) {
		String clusterNodeUrl = rancherServerPath.clusterNodeUrl(id);
		String response = HttpClientUtil.doGet(clusterNodeUrl, headers);
		JSONObject obj = JSON.parseObject(response);
		JSONArray data = (JSONArray) obj.get("data");

		StringBuilder cidr = new StringBuilder();
		AtomicInteger totalNodeNum = new AtomicInteger(0);
		AtomicInteger runningNodeNum = new AtomicInteger(0);
		StringBuilder updatedAt = new StringBuilder();

		final AtomicLong tmp = new AtomicLong(0);
		data.stream().forEach(item -> {
			JSONObject node = (JSONObject) item;
			Boolean worker = node.getBooleanValue("worker");
			if (Boolean.TRUE.equals(worker)) {
				totalNodeNum.addAndGet(1);
				String nodeState = node.getString("state");
				if ("active".equalsIgnoreCase(nodeState)) {
					runningNodeNum.addAndGet(1);
				}
				String podCidr = node.getString("podCidr");
				if(StringUtils.isNotBlank(cidr.toString())){
					cidr.append(",");
				}
				cidr.append(podCidr);
				long createdTS = node.getLongValue("createdTS");
				if(createdTS > tmp.longValue()) {
					tmp.set(createdTS);
					updatedAt.setLength(0);
					updatedAt.append(node.getString("created"));
				}
			}
		});
		clusterInfoDto.setRunningNodeNum(runningNodeNum.get());
		clusterInfoDto.setTotalNodeNum(totalNodeNum.get());
		clusterInfoDto.setCidr(cidr.toString());
		clusterInfoDto.setUpdatedAt(updatedAt.toString());
	}

	@Override
	public String deleteClusterById(Map<String, String> headers, String clusterId) {
		Assert.hasLength(clusterId, "集群ID不能为空！");

		String url = rancherServerPath.deleteClusterUrl(clusterId);
		String result = HttpClientUtil.doDelete(url, headers);
		return result;
	}

	@Override
	public String clusterUsage(Map<String, String> headers, String clusterId) {
		Assert.hasLength(clusterId, "集群ID不能为空！");
		String url = rancherServerPath.clusterInfoUrl(clusterId);
		String result = HttpClientUtil.doGet(url, headers);
		if (StringUtils.isBlank(result)) return StringUtils.EMPTY;

		String data = conversionToClusterUsage(result);

		return data;
	}

	@Override
	public void setMasterNode(Map<String, String> headers, List<ClusterVMDto> masterNodes) {
		Assert.notEmpty(masterNodes, "添加master节点：节点信息不能为空！");
		ClusterVMDto masterNode = masterNodes.get(0);
		String clusterId = masterNode.getClusterId();
		String basicCommand = requestAddNodeSSH(headers, clusterId);
		final String command = basicCommand + NODE_ROLE_MASTER;

		masterNodes.stream().forEach(it ->
				SSHHelper.execCommand(command, it)
		);

	}

	private String requestAddNodeSSH(Map<String, String> headers, String clusterId) {
		Assert.hasLength(clusterId, "添加节点：集群ID不能为空！");
		String tokensUrl = rancherServerPath.clusterRegistrationTokenUrl(clusterId);
		Map<String, Object> params = new HashMap<>();
		params.put("clusterId", clusterId);
		String result = HttpClientUtil.doPost(tokensUrl, headers, params);
		JSONObject tokenResult = JSON.parseObject(result);
		String command = String.valueOf(tokenResult.get("nodeCommand"));
		if (StringUtils.isBlank(command)) {
			throw new RuntimeException(String.valueOf(tokenResult.get("message")));
		}
		return command;
	}

	private String conversionToClusterUsage(String result) {
		JSONObject obj = JSON.parseObject(result);
		JSONArray arr = (JSONArray) obj.get("data");
		if (arr == null || arr.size() == 0)
			return StringUtils.EMPTY;
		ClusterInfo clusterInfo = JSON.parseObject(arr.get(0).toString(), ClusterInfo.class);
		if (clusterInfo == null) {
			return StringUtils.EMPTY;
		}
		JSONObject usage = new JSONObject();
		Capacity capacity = clusterInfo.getCapacity();
		Requested requested = clusterInfo.getRequested();
		Limits limits = clusterInfo.getLimits();
		if (capacity != null) {
			usage.put("cpuTotal", capacity.getCpu());
			usage.put("memTotal", capacity.getMemory());
		}
		if (requested != null) {
			usage.put("cpuRequest", requested.getCpu());
			usage.put("memRequest", requested.getMemory());
		}
		if (limits != null) {
			usage.put("cpuLimit", limits.getCpu());
			usage.put("memLimit", limits.getMemory());
		}
		return usage.toString();
	}

	@Override
	public String clusterNodes(Map<String, String> headers, String clusterId) {
		Assert.hasLength(clusterId, "获取集群节点列表：集群ID不能为空！");
		String clusterNodeUrl = rancherServerPath.clusterNodeUrl(clusterId);
		String response = HttpClientUtil.doGet(clusterNodeUrl, headers);
		JSONObject obj = JSON.parseObject(response);
		JSONArray data = (JSONArray) obj.get("data");
		if (data == null || data.size() == 0) {
			log.info("集群节点列表为空，集群ID：{}", clusterId);
			return StringUtils.EMPTY;
		}
		List<ClusterNodeDto> list = new ArrayList<>();
		data.stream().forEach(it -> {
			if (Boolean.TRUE.equals(((JSONObject) it).get("worker"))) {
				JSONObject item = (JSONObject) it;
				ClusterNodeDto info = JSON.parseObject(JSON.toJSONString(it), ClusterNodeDto.class);
				info.setCpuLimit(null); //TODO 需获取pod信息 计算得出
				info.setCpuRequest(null);
				info.setCpuTotal(null);
				info.setMemLimit(null);
				info.setMemRequest(null);
				info.setMemTotal(null);
				info.setWanIp(item.getString("externalIpAddress"));
				info.setLanIp(item.getString("ipAddress"));
				info.setStatus(item.getString("state"));
				info.setCreatedAt(item.getString("created"));
				info.setUpdatedAt(null);
				list.add(info);
			}
		});

		return response;
	}

	@Override
	public void addNodes(Map<String, String> headers, List<ClusterVMDto> nodes) {
		Assert.notEmpty(nodes, "添加node节点：节点信息不能为空！");
		ClusterVMDto node = nodes.get(0);
		String clusterId = node.getClusterId();
		String basicCommand = requestAddNodeSSH(headers, clusterId);
		final String command = basicCommand + NODE_ROLE_NODE;
		nodes.stream().forEach(it ->
				SSHHelper.execCommand(command, it)
		);
	}

	@Override
	public void removeNodes(Map<String, String> headers, String clusterId, List<String> ipList) {
		Assert.hasLength(clusterId, "获取集群节点列表：集群ID不能为空！");
		String clusterNodeUrl = rancherServerPath.clusterNodeUrl(clusterId);
		String response = HttpClientUtil.doGet(clusterNodeUrl, headers);
		JSONObject obj = JSON.parseObject(response);
		JSONArray data = (JSONArray) obj.get("data");
		if (data == null || data.size() == 0) {
			log.info("集群节点列表为空，集群ID：{}", clusterId);
		}

		data.stream().forEach(it -> {
			JSONObject item = (JSONObject) it;
			ipList.stream().forEach(ip -> {
				if(ip.equalsIgnoreCase(item.getString("externalIpAddress")) ||
					ip.equalsIgnoreCase(item.getString("ipAddress"))) {
					String id = item.getString("id");
					String removeNodeUrl = rancherServerPath.removeNodeUrl(id);
					HttpClientUtil.doDelete(removeNodeUrl, headers);
				}
			});
		});
	}
}
