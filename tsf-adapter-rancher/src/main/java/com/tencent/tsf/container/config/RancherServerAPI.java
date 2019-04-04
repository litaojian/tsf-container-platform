/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.config;

/**
 * @title RancherServerAPI
 * @title Ethan
 * @date 2019/3/29 18:23
 * @description TODO
 * @version Version 1.0
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
	/** 获取单个命名空间 **/
	public static String RANCHER_GET_NAMESPACE_INFO     = "/cluster/%1$s/namespaces/%2$s";
	/** 获取某个集群下的所有命名空间 **/
	public static String RANCHER_GET_ALL_NAMESPACES     = "/cluster/%1$s/namespaces";
	/** 获取添加节点的命令 **/
	public static String RANCHER_CLUSTER_REGISTRATION_TOKENS = "/clusters/%1$s/clusterregistrationtokens";
	/** 获取集群节点信息 **/
	public static String RANCHER_CLUSTER_NODES          = "/clusters/%1$s/nodes";

}
