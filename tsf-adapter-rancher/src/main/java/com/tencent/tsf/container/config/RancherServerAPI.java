/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.config;

/**
 * @Title RancherServerAPI
 * @Author Ethan
 * @Date 2019/3/29 18:23
 * @Description TODO
 * @Version Version 1.0
 */

public class RancherServerAPI {

	/** 创建集群 **/
	public static String RANCHER_CREATE_CLUSTER         = "/cluster";
	/** 获取单个集群信息 **/
	public static String RANCHER_GET_THE_CLUSTER_INFO   = "/clusters?id_eq=%1$s";
	/** 获取集群列表 **/
	public static String RANCHER_GET_ALL_CLUSTERS       = "/clusters";
	/** 删除集群 **/
	public static String RANCHER_DELETE_CLUSTER         = "/clusters/%1$s";

}
