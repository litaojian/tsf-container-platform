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
import com.tencent.tsf.container.models.Capacity;
import com.tencent.tsf.container.models.ClusterInfo;
import com.tencent.tsf.container.models.Limits;
import com.tencent.tsf.container.models.Requested;
import com.tencent.tsf.container.service.ClusterManagerService;
import com.tencent.tsf.container.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;


/**
 * @title ClusterManagerServiceImpl
 * @title Ethan
 * @date 2019/3/29 13:20
 * @description TODO
 * @@version Version 1.0
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
		RancherKubernetesConfig config = new RancherKubernetesConfig();
		BeanUtils.copyProperties(rancherKubernetesConfig, config);
		param.put("rancherKubernetesEngineConfig", config);

		System.out.println(JSON.toJSONString(param));

		headers.put("Content-Type", "application/json");
		String url = rancherServerPath.createClusterUrl();
		String result = HttpClientUtil.doPost(url, headers, JSON.toJSONString(param));
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
		JSONObject obj = JSON.parseObject(result);
		JSONArray arr = (JSONArray) obj.get("data");
		if(arr == null || arr.size() == 0)
			return StringUtils.EMPTY;
		ClusterInfo clusterInfo = JSON.parseObject(arr.get(0).toString(), ClusterInfo.class);
		if(clusterInfo == null) {
			return StringUtils.EMPTY;
		}
		JSONObject usage = new JSONObject();
		Capacity capacity = clusterInfo.getCapacity();
		Requested requested = clusterInfo.getRequested();
		Limits limits = clusterInfo.getLimits();
		if(capacity != null) {
			usage.put("cpuTotal", capacity.getCpu());
			usage.put("memTotal", capacity.getMemory());
		}
		if(requested != null) {
			usage.put("cpuRequest", requested.getCpu());
			usage.put("memRequest", capacity.getMemory());
		}
		if(limits != null) {
			usage.put("cpuLimit", limits.getCpu());
			usage.put("memLimit", capacity.getMemory());
		}
		return usage.toString();
	}

}
