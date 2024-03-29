/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.service;

import com.tencent.tsf.container.dto.ClusterInfoDto;
import com.tencent.tsf.container.dto.ClusterNodeDto;
import com.tencent.tsf.container.dto.ClusterVMDto;
import com.tencent.tsf.container.dto.KubeAPIServerDto;

import java.util.List;
import java.util.Map;

/**
 * @title ClusterManagerService
 * @title Ethan
 * @date 2019/3/29 13:19
 * @description TODO
 * @version Version 1.0
 */

public interface ClusterManagerService {

	String createCluster(Map<String, String> headers, String name);

	ClusterInfoDto getClusterById(Map<String, String> headers, String clusterId);

	List<ClusterInfoDto> getClusters(Map<String, String> headers, Map<String, Object> params);

	String deleteClusterById(Map<String, String> headers, String clusterId);

	String clusterUsage(Map<String, String> headers, String clusterId);

	void setMasterNode(Map<String, String> headers, List<ClusterVMDto> masterNodes);

	List<ClusterNodeDto> clusterNodes(Map<String, String> headers, String clusterId);

	void addNodes(Map<String, String> headers, List<ClusterVMDto> nodes);

	void removeNodes(Map<String, String> headers, String clusterId, List<String> ipList);

	KubeAPIServerDto getKubernetesAPIServer(Map<String, String> headers, String clusterId);
}
