/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.service;

import java.util.Map;

/**
 * @Title ClusterManagerService
 * @Author Ethan
 * @Date 2019/3/29 13:19
 * @Description TODO
 * @Version Version 1.0
 */

public interface ClusterManagerService {

	String createCluster(Map<String, String> headers, String name);

	String getClusterById(Map<String, String> headers, String clusterId);

	String getClusters(Map<String, String> headers, Map<String, Object> params);

	String deleteClusterById(Map<String, String> headers, String clusterId);

}