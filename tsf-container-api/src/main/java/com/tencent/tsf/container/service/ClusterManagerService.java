/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.service;

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

	String getClusterById(Map<String, String> headers, String clusterId);

	String getClusters(Map<String, String> headers, Map<String, Object> params);

	String deleteClusterById(Map<String, String> headers, String clusterId);

	String clusterUsage(Map<String, String> headers, String clusterId);

	Map getNamespaceById(Map<String, String> headers, String clusterId, String namespaceId);

	Map getNamespaces(Map<String, String> headers, Map<String, Object> params, String clusterId);

}
