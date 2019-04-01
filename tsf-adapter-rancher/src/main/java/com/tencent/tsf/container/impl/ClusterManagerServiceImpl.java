/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.impl;

import com.tencent.tsf.container.config.RancherKubernetesConfig;
import com.tencent.tsf.container.config.RancherServerPath;
import com.tencent.tsf.container.service.ClusterManagerService;
import com.tencent.tsf.container.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
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
}
