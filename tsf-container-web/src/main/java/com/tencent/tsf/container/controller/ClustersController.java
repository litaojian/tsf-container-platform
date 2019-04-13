/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.controller;

import ch.ethz.ssh2.Connection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.tsf.container.dto.*;
import com.tencent.tsf.container.service.ClusterManagerService;
import com.tencent.tsf.container.utils.RemoteCommandUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version Version 1.0
 * @title ClustersController
 * @title Ethan
 * @date 2019/3/29 00:08
 * @description 集群管理
 */

@Slf4j
@RestController
@RequestMapping("/clusters")
@Api(value = "ClustersController", description = "集群管理")
public class ClustersController extends BaseController {

	@Autowired
	private ClusterManagerService clusterManagerService;

	@PostMapping("")
	@ApiOperation(value = "创建空集群", httpMethod = "POST",
			notes = "创建一个空的集群，供后面加入机器资源及运行 Kubernetes。\n" +
					"容器平台需要检查 CIDR 及名称是否有冲突<br/>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>name：集群名称（String），必填</li>" +
					"</ul>" +
					"返回参数描述：<br/>", response = BaseResponse.class)
	public BaseResponse createCluster(HttpServletRequest request, @RequestBody Map<String, String> param) {
		log.info("创建空集群, params： {}", param);
		Map<String, String> headers = getCustomHeaders(request);
		String data = clusterManagerService.createCluster(headers, param.get("name"));
		log.debug("创建空集群, response data: {}", data);

		return createSuccessResult(JSON.parseObject(data));
	}

	@GetMapping("/{clusterId}")
	@ApiOperation(value = "查询单个集群信息\n", httpMethod = "GET",
			notes = "查询单个集群信息<br/>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>clusterId：集群ID（String），必填</li>" +
					"</ul>" +
					"返回参数描述：<br/>", response = BaseResponse.class)
	public BaseResponse getCluster(HttpServletRequest request, @PathVariable("clusterId") String clusterId) {
		log.info("查询单个集群信息, 集群ID: {}", clusterId);

		Map<String, String> headers = getCustomHeaders(request);
		ClusterInfoDto result = clusterManagerService.getClusterById(headers, clusterId);
		log.debug("查询单个集群信息 -> ", result);
		return createSuccessResult(result);
	}

	@GetMapping("")
	@ApiOperation(value = "获取集群列表", httpMethod = "GET",
			notes = "获取集群列表<br/>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>name：集群名称（String），必填</li>" +
					"</ul>" +
					"返回参数描述：<br/>", response = BaseResponse.class)
	public BaseResponse getClusters(HttpServletRequest request) {
		log.info("-> 获取集群列表");

		Map<String, String> headers = getCustomHeaders(request);
		Map<String, Object> params = getRequestParams(request);
		List<ClusterInfoDto> data = clusterManagerService.getClusters(headers, params);
		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", data.size());
		map.put("content", data);

		log.debug("clusters gotten, data: {}", data);

		return createSuccessResult(map);
	}


	@DeleteMapping("/{clusterId}")
	@ApiOperation(value = "删除集群", httpMethod = "DELETE",
			notes = "删除集群<br/>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>clusterId：集群ID（String），必填</li>" +
					"</ul>" +
					"返回参数描述：<br/>", response = BaseResponse.class)
	public BaseResponse deleteCluster(HttpServletRequest request,
	                                  @PathVariable("clusterId") String clusterId) {
		log.info("删除集群, 集群ID: {}", clusterId);

		Map<String, String> headers = getCustomHeaders(request);
		String data = clusterManagerService.deleteClusterById(headers, clusterId);

		log.debug("cluster deleted successfully, data: {}", data);

		return createSuccessResult(data);
	}

	@GetMapping("/{clusterId}/usage")
	@ApiOperation(value = "集群资源对使用情况", httpMethod = "GET",
			notes = "集群资源对使用情况<br/>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>clusterId：集群ID（String），必填</li>" +
					"</ul>" +
					"返回参数描述：<br/>", response = BaseResponse.class)
	public BaseResponse clusterUsage(HttpServletRequest request,
	                                 @PathVariable("clusterId") String clusterId) {
		log.info("集群资源对使用情况, 集群ID: {}", clusterId);

		Map<String, String> headers = getCustomHeaders(request);
		String data = clusterManagerService.clusterUsage(headers, clusterId);
		log.debug("cluster usage, result -> {}", data);
		return createSuccessResult(JSON.parseObject(data));
	}


	@PostMapping("/{clusterId}:setupMaster")
	@ApiOperation(value = "设置master节点", httpMethod = "POST",
			notes = "设置master节点<br>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>URI参数: clusterId——集群ID，必填</li>" +
					"<li>请求体参数: {\"instances\": [\n" +
					"{\"ip\": \"172.16.1.20\",\"port\": 22," +
					"\"username\": \"root\",\"password\": \"pa55w0rd\"\n" +
					"}]}, 必填</li>" +
					"</ul>" +
					"返回参数描述<p></p>", response = BaseResponse.class)
	public BaseResponse setupMaster(HttpServletRequest request,
	                                @PathVariable("clusterId") String clusterId,
	                                @RequestBody Map<String, List<ClusterVMDto>> params) {
		log.info("设置master节点, 集群ID: {}, host: {}", clusterId, JSON.toJSONString(params));

		Map<String, String> headers = getCustomHeaders(request);
		List<ClusterVMDto> nodes = params.get("instances");
		nodes.stream().forEach(it -> {
			it.setIsMaster(true);
			it.setClusterId(clusterId);
		});
		clusterManagerService.setMasterNode(headers, nodes);
		return createSuccessResult("{}");
	}

	@PostMapping("/{clusterId}:addNodes")
	@ApiOperation(value = "将机器作为 node 节点加入 Kubernetes 集群", httpMethod = "POST",
			notes = "将机器作为 node 节点加入 Kubernetes 集群<br>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>URI参数: clusterId——集群ID，必填</li>" +
					"<li>请求体参数: \"instances\": [\n" +
					"{\"ip\": \"172.16.1.20\",\"port\": 22," +
					"\"username\": \"root\",\"password\": \"pa55w0rd\"\n" +
					"}], 必填</li>" +
					"</ul>" +
					"返回参数描述<p></p>", response = BaseResponse.class)
	public BaseResponse addNodes(HttpServletRequest request,
	                             @PathVariable("clusterId") String clusterId,
	                             @RequestBody Map<String, List<ClusterVMDto>> params) {
		log.info("添加node节点, 集群ID: {}, host: {}", clusterId, JSON.toJSONString(params));

		Map<String, String> headers = getCustomHeaders(request);
		List<ClusterVMDto> nodes = params.get("instances");
		nodes.stream().forEach(it -> {
			it.setIsMaster(false);
			it.setClusterId(clusterId);
		});
		clusterManagerService.addNodes(headers, nodes);
		return createSuccessResult("{}");
	}

	@DeleteMapping("/{clusterId}:removeNodes")
	@ApiOperation(value = "将 node 节点从 Kubernetes 集群中移除", httpMethod = "DELETE",
			notes = "将 node 节点从 Kubernetes 集群中移除<br>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>URI参数: clusterId——集群ID，必填</li>" +
					"<li>请求体参数: \"instances\": [\n" +
					"{\"ip\": \"172.16.1.20\",\"ip\": \"172.16.1.21\"" +
					"}], 必填</li>" +
					"</ul>" +
					"返回参数描述<p></p>", response = BaseResponse.class)
	public BaseResponse removeNodes(HttpServletRequest request,
	                          @PathVariable("clusterId") String clusterId,
	                          @RequestBody Map<String, List<Map<String, String>>> params) {
		log.info("将 node 节点从 Kubernetes 集群中移除, 集群ID: {}, ips: {}", clusterId, JSON.toJSONString(params));

		Map<String, String> headers = getCustomHeaders(request);
		List<Map<String, String>> ipMap = params.get("instances");
		final List<String> ipList = new ArrayList<>();
		ipMap.stream().forEach(it -> ipList.add(it.get("ip")));
		clusterManagerService.removeNodes(headers, clusterId, ipList);
		return createSuccessResult("{}");
	}

	@GetMapping("/{clusterId}/nodes")
	@ApiOperation(value = "获取集群 node 节点信息", httpMethod = "GET",
			notes = "获取集群内 node 节点的基本信息和资源使用。这个接口不返回 master 结点的状态信息。<br>" +
					"请求参数描述：" +
					"<ul>" +
					"<li>URI参数: clusterId——集群ID，必填</li>" +
					"</ul>" +
					"返回参数描述<p></p>", response = BaseResponse.class)
	public BaseResponse nodes(HttpServletRequest request,
	                          @PathVariable("clusterId") String clusterId) {
		log.info("获取集群 node 节点信息, 集群ID: {}", clusterId);

		Map<String, String> headers = getCustomHeaders(request);
		List<ClusterNodeDto> list = clusterManagerService.clusterNodes(headers, clusterId);
		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", list.size());
		map.put("content", list);

		log.debug("nodes info, result: {}", JSON.toJSONString(list));
		return createSuccessResult(map);
	}

	@GetMapping("/{clusterId}/apiServer")
	@ApiOperation(value = "获取集群的 Kubernetes API Server 地址", httpMethod = "GET",
			notes = "获取集群的 Kubernetes API Server 地址" +
					"请求参数描述：" +
					"<ul>" +
					"<li>URI参数: clusterId——集群ID，必填</li>" +
					"</ul>" +
					"返回参数描述<p></p>", response = BaseResponse.class)
	public BaseResponse kubeAPIServver(HttpServletRequest request,
	                          @PathVariable("clusterId") String clusterId) {
		log.info("获取集群的 Kubernetes API Server 地址, 集群ID: {}", clusterId);

		Map<String, String> headers = getCustomHeaders(request);
		KubeAPIServerDto kubeAPIServer = clusterManagerService.getKubernetesAPIServer(headers, clusterId);

		log.debug("nodes info, result: {}", JSON.toJSONString(kubeAPIServer));
		return createSuccessResult(kubeAPIServer);
	}

	@GetMapping("/getIp/{inIp}")
	public String getIp(@PathVariable String inIp){
		String com = "curl ifconfig.me";
		Connection connection = RemoteCommandUtil.login(inIp, "root", "tsf12345678+");
		String ip = RemoteCommandUtil.execute(connection, com);
		return ip;
	}

}
