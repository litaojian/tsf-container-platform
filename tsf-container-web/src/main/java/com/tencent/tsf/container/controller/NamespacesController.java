package com.tencent.tsf.container.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.tsf.container.dto.BaseResponse;
import com.tencent.tsf.container.dto.NamespaceDTO;
import com.tencent.tsf.container.service.NamespaceManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/clusters")
@Api(value = "NamespacesController", description = "命名空间管理")
public class NamespacesController extends BaseController {

    @Autowired
    NamespaceManagerService namespaceManagerService;

    @GetMapping("/{clusterId}/namespaces/{namespaceId}")
    @ApiOperation(value = "获取单个命名空间", httpMethod = "GET",
            notes = "获取单个命名空间<br/>" +
                    "请求参数描述：" +
                    "<ul>" +
                    "<li>clusterId：集群ID</li>" +
                    "<li>namespaceId：命名空间ID</li>" +
                    "</ul>" +
                    "返回参数描述：<br/>", response = BaseResponse.class)
    public BaseResponse getNamespace(@PathVariable("clusterId") String clusterId, @PathVariable("namespaceId") String namespaceId, HttpServletRequest request){
        Map<String, String> headers = getCustomHeaders(request);
        String data = namespaceManagerService.getNamespaceById(headers, clusterId, namespaceId);
        JSONObject jsonObject = JSON.parseObject(data);
        NamespaceDTO namespaceDTO = new NamespaceDTO();
        namespaceDTO.setId(jsonObject.get("id").toString());
        namespaceDTO.setName(jsonObject.get("name").toString());
        namespaceDTO.setStatus(jsonObject.get("state").toString());
        namespaceDTO.setCreatedAt(jsonObject.get("created").toString());
        log.info("---- namespace gotten, namespaceDTO: {}", namespaceDTO);
        return createSuccessResult(namespaceDTO);
    }

    @GetMapping("/{clusterId}/namespaces")
    @ApiOperation(value = "获取命名空间列表", httpMethod = "GET",
            notes = "获取命名空间列表<br/>" +
                    "请求参数描述：" +
                    "<ul>" +
                    "<li>clusterId：集群ID</li>" +
                    "</ul>" +
                    "返回参数描述：<br/>", response = BaseResponse.class)
    public BaseResponse getNamespaces(@PathVariable("clusterId") String clusterId, HttpServletRequest request){
        Map<String, String> headers = getCustomHeaders(request);
        Map<String, Object> params = getRequestParams(request);
        String data = namespaceManagerService.getNamespaces(headers, params, clusterId);
        JSONObject jsonObject = JSON.parseObject(data);
        Map<String, Object> resultMap = new HashMap<>();
        if (jsonObject.get("data") == null) {
            resultMap.put("totalCount", 0);
            resultMap.put("content", null);
            return createSuccessResult(resultMap);
        }
        List<Map<String, Object>> dataList = (List) jsonObject.get("data");
        List<NamespaceDTO> namespaceList = new ArrayList<>();
        dataList.forEach(map -> {
            NamespaceDTO namespaceDTO = new NamespaceDTO();
            namespaceDTO.setId(map.get("id").toString());
            namespaceDTO.setName(map.get("name").toString());
            namespaceDTO.setStatus(map.get("state").toString());
            namespaceDTO.setCreatedAt(map.get("created").toString());
            namespaceList.add(namespaceDTO);
        });

        resultMap.put("totalCount", namespaceList.size());
        resultMap.put("content", namespaceList);
        log.info("---- namespaces gotten, resultMap: {}", resultMap);
        return createSuccessResult(resultMap);
    }
}
