/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.controller;

import com.tencent.tsf.container.dto.BaseResponse;
import com.tencent.tsf.container.service.ClusterManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Title ClustersController
 * @Author Ethan
 * @Date 2019/3/29 00:08
 * @Description 集群管理
 * @Version Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/clusters")
@Api(value = "ClustersController", description = "集群管理")
public class ClustersController extends BaseController{

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
	public BaseResponse createCluster(HttpServletRequest request, @RequestParam("name") String name){
		log.info("---- create the empty of cluster and name is {}", name);

		Map<String, String> headers = getCustomHeaders(request);
		String data = clusterManagerService.createCluster(headers, name);

		log.info("---- create successfully, data: {}", data);

		return createSuccessResult(data);
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

		Map<String, String> headers = getCustomHeaders(request);
		String result = clusterManagerService.getClusterById(headers, clusterId);

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
	public BaseResponse getClusters(HttpServletRequest request){

		Map<String, String> headers = getCustomHeaders(request);
		Map<String, Object> params = getRequestParams(request);
		String data = clusterManagerService.getClusters(headers, params);

		log.info("---- clusters gotten, data: {}", data);

		return createSuccessResult(data);
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
	                                  @PathVariable("clusterId") String clusterId){

		Map<String, String> headers = getCustomHeaders(request);
		String data = clusterManagerService.deleteClusterById(headers, clusterId);

		log.info("---- delete successfully, data: {}", data);

		return createSuccessResult(data);
	}










	@PostMapping("/{clusterId}:setupMaster")
	@ApiOperation(value = "设置master节点", httpMethod = "POST",
			notes = "设置master节点<br>" +
			"请求参数描述：" +
			"<ul>" +
				"<li>clusterId：集群ID</li>" +
			"</ul>"
			+ "返回参数描述<p></p>", response = BaseResponse.class)
	public String setupMaster(@PathVariable("clusterId") String clusterId) {
		log.info("===============clusterId: {}", clusterId);
		return clusterId;
	}

	@PostMapping("/{clusterId}:addNodes")
	public String addNodes(@PathVariable("clusterId") String clusterId){
		log.info("--------------clusterId: {}", clusterId);
		return "";
	}
}
