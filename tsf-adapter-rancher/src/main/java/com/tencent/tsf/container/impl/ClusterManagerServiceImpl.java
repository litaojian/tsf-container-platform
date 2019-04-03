/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.tsf.container.config.RancherKubernetesConfig;
import com.tencent.tsf.container.config.RancherServerPath;
import com.tencent.tsf.container.dto.NamespaceDTO;
import com.tencent.tsf.container.service.ClusterManagerService;
import com.tencent.tsf.container.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title ClusterManagerServiceImpl
 * @Author Ethan
 * @Date 2019/3/29 13:20
 * @Description TODO
 * @Version Version 1.0
 */

@Slf4j
@Service
public class ClusterManagerServiceImpl implements ClusterManagerService {

	@Autowired
	private RancherServerPath rancherServerPath;

	@Autowired
	private RancherKubernetesConfig rancherKubernetesConfig;

	@Override
	public String createCluster(Map<String, String> headers, String name) {
		Assert.hasLength(name, "集群名称不能为空！");
		Map<String, Object> param = new HashMap<>();
		param.put("name", name);
		param.put("dockerRootDir", "/var/lib/docker");
		param.put("enableNetworkPolicy", false);
		param.put("type", "cluster");
		param.put("rancherKubernetesEngineConfig", rancherKubernetesConfig);

		System.out.println(JSONObject.fromObject(param).toString());

		headers.put("Content-Type", "application/json");
		String url = rancherServerPath.createClusterUrl();
		String result = HttpClientUtil.doPost(url, headers, JSONObject.fromObject(param).toString());
		return result;
	}

	@Override
	public String getClusterById(Map<String, String> headers, String clusterId) {
		Assert.hasLength(clusterId, "集群ID不能为空！");

		String url = rancherServerPath.clusterInfoUrl(clusterId);
		String result = HttpClientUtil.doGet(url, headers);
		return result;
	}

	@Override
	public String getClusters(Map<String, String> headers, Map<String, Object> params) {
		String url = rancherServerPath.getAllClustersUrl(params);
		String result = HttpClientUtil.doGet(url, headers);
		return result;
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
		if(StringUtils.isBlank(result)) return StringUtils.EMPTY;

		String data = conversionToClusterUsage(result);

		return data;
	}

	private String conversionToClusterUsage(String result) {
		JSONObject obj = JSONObject.fromObject(result);
		JSONArray arr = (JSONArray) obj.get("data");
		JSONObject clusterInfo = JSONObject.fromObject(arr.get(0).toString());
		if(clusterInfo == null || clusterInfo.size() == 0) {
			return StringUtils.EMPTY;
		}
		JSONObject usage = new JSONObject();
		JSONObject capacity = (JSONObject) clusterInfo.get("capacity");
		JSONObject requested = (JSONObject) clusterInfo.get("requested");
		JSONObject limits = (JSONObject) clusterInfo.get("limits");
		if(!CollectionUtils.isEmpty(capacity)) {
			usage.put("cpuTotal", capacity.get("cpu"));
			usage.put("memTotal", capacity.get("memory"));
		}
		if(!CollectionUtils.isEmpty(requested)) {
			usage.put("cpuRequest", requested.get("cpu"));
			usage.put("memRequest", capacity.get("memory"));
		}
		if(!CollectionUtils.isEmpty(limits)) {
			usage.put("cpuLimit", limits.get("cpu"));
			usage.put("memLimit", capacity.get("memory"));
		}
		return usage.toString();
	}

	@Override
	public Map getNamespaceById(Map<String, String> headers, String clusterId, String namespaceId) {
		Assert.hasLength(clusterId, "集群ID不能为空！");
		Assert.hasLength(namespaceId, "命名空间ID不能为空！");

		String url = rancherServerPath.namespaceInfoUrl(clusterId, namespaceId);
		String result = HttpClientUtil.doGet(url, headers);
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
		if (jsonObject.get("data") == null) {
			return null;
		}
		return (Map) jsonObject.get("data");
	}

	@Override
	public Map<String, Object> getNamespaces(Map<String, String> headers, Map<String, Object> params, String clusterId) {
		Assert.hasLength(clusterId, "集群ID不能为空！");

		Map<String, Object> resultMap = new HashMap<>();
		String url = rancherServerPath.getAllNamespacesUrl(clusterId, params);
		String result = HttpClientUtil.doGet(url, headers);
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
		if (jsonObject.get("data") == null) {
			resultMap.put("totalCount", 0);
			resultMap.put("content", null);
			return resultMap;
		}
		List<Map<String, Object>> dataList = (List) jsonObject.get("data");
		List<NamespaceDTO> namespaceList = new ArrayList<>();
		dataList.forEach(map -> {
			NamespaceDTO namespaceDTO = new NamespaceDTO();
			namespaceDTO.setId(map.get("id").toString());
			namespaceDTO.setName(map.get("name").toString());
			namespaceDTO.setStatus(map.get("state").toString());
			namespaceDTO.setCreatedAt(map.get("created").toString());
			namespaceList.add(namespaceDTO);
		});

		resultMap.put("totalCount", namespaceList.size());
		resultMap.put("content", namespaceList);
		return resultMap;
	}


}
