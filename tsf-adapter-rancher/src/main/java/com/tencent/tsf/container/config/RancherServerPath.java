/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @Title RancherServerPath
 * @Author Ethan
 * @Date 2019/3/29 18:39
 * @Description TODO
 * @Version Version 1.0
 */

@Data
@Component
public class RancherServerPath {

	@Autowired
	private RancherConfiguration rancherConfiguration;

	/**
	 * @return
	 * @Author Ethan
	 * @Date 2019/3/29 18:45
	 * @Param
	 * @Description  获取创建空集群的请求URL
	 **/
	public String createClusterUrl(){
		StringBuilder url = new StringBuilder();
		url.append(rancherConfiguration.getEndpoint())
				.append(RancherServerAPI.RANCHER_CREATE_CLUSTER);
		return url.toString();
	}

	/**
	 * @return
	 * @Author Ethan
	 * @Date 2019/3/29 19:09
	 * @Param [clusterId]
	 * @Description 获取单个集群的信息
	 **/
	public String clusterInfoUrl(String clusterId) {
		Assert.hasLength(clusterId, "集群ID不能为空！");
		StringBuilder urlBuilder = new StringBuilder(rancherConfiguration.getEndpoint());
		urlBuilder.append(RancherServerAPI.RANCHER_GET_THE_CLUSTER_INFO);
		return String.format(urlBuilder.toString(), clusterId);
	}

	public String getAllClustersUrl(final Map<String, Object> params){
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(rancherConfiguration.getEndpoint())
				.append(RancherServerAPI.RANCHER_GET_ALL_CLUSTERS);
		String paramUrl = assembleGetMethodParams(params);
		if(StringUtils.isNotBlank(paramUrl)) {
			urlBuilder.append("?")
					.append(paramUrl);
		}
		return urlBuilder.toString();

	}

    /**
     * 获取单个命名空间 URL
     * @param clusterId
     * @param namespaceId
     * @return
     */
	public String namespaceInfoUrl(String clusterId, String namespaceId) {
        Assert.hasLength(clusterId, "集群ID不能为空！");
        Assert.hasLength(namespaceId, "命名空间ID不能为空！");
        StringBuilder urlBuilder = new StringBuilder(rancherConfiguration.getEndpoint());
        urlBuilder.append(RancherServerAPI.RANCHER_GET_NAMESPACE_INFO);
        return String.format(urlBuilder.toString(), clusterId, namespaceId);
    }

	/**
	 * 获取某个集群下所有命名空间  URL
	 * @param clusterId
	 * @param params
	 * @return
	 */
	public String getAllNamespacesUrl(String clusterId, final Map<String, Object> params) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(rancherConfiguration.getEndpoint())
				.append(RancherServerAPI.RANCHER_GET_ALL_NAMESPACES);
		String paramUrl = assembleGetMethodParams(params);
		if (StringUtils.isNotBlank(paramUrl)) {
			urlBuilder.append("?")
					.append(paramUrl);
		}
		return String.format(urlBuilder.toString(), clusterId);
	}

	private static String assembleGetMethodParams(final Map<String, Object> params) {
		if(CollectionUtils.isEmpty(params)) return "";
		final StringBuilder urlBuilder = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
		Map.Entry<String, Object> entry = null;
		while (iterator.hasNext()) {
			entry = iterator.next();
			switch (entry.getKey()) {
				case "ids":
					String idValues = params.get("ids").toString();
					String[] idArr = idValues.split(",");
					Arrays.stream(idArr).forEach(it -> {
						urlBuilder.append("&id_in=")
								.append(it);
					});
					break;
				case "offset":
					urlBuilder.append("&offset=")
							.append(params.get("offset"));
					break;
				case "limit":
					urlBuilder.append("&limit=")
							.append(params.get("limit"));
					break;
				default:
			}
		}
		String paramUrl = urlBuilder.toString();
		if(paramUrl.indexOf("&") == 0) {
			paramUrl = paramUrl.substring(1);
		}
		return paramUrl;
	}

	public String deleteClusterUrl(String clusterId){
		Assert.hasLength(clusterId, "集群ID不能为空！");
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(rancherConfiguration.getEndpoint())
				.append(RancherServerAPI.RANCHER_DELETE_CLUSTER);
		return String.format(urlBuilder.toString(), clusterId);
	}

}
