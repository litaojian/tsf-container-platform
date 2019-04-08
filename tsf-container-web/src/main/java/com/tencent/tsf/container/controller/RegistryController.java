package com.tencent.tsf.container.controller;

import com.tencent.tsf.container.dto.BaseResponse;
import com.tencent.tsf.container.service.NamespaceManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/registry")
@Api(value = "RegistryController", description = "镜像仓库管理")
public class RegistryController extends BaseController {
    @Autowired
    NamespaceManagerService namespaceManagerService;

    @PostMapping("/namespaces")
    @ApiOperation(value = "创建镜像仓库命名空间", httpMethod = "POST",
            notes = "创建镜像仓库命名空间<br/>" +
                    "请求参数描述：" +
                    "<ul>" +
                    "<li>name：命名空间名称（String），必填</li>" +
                    "</ul>" +
                    "返回参数描述：<br/>", response = BaseResponse.class)
    public BaseResponse createNamespace(@RequestParam("name") String name){
        return createSuccessResult();
    }

    @GetMapping("/namespaces/{namespaceName}")
    @ApiOperation(value = "获取镜像仓库命名空间", httpMethod = "POST",
            notes = "获取镜像仓库命名空间<br/>" +
                    "请求参数描述：" +
                    "<ul>" +
                    "<li>namespaceName：命名空间名称</li>" +
                    "</ul>" +
                    "返回参数描述：<br/>", response = BaseResponse.class)
    public BaseResponse getNamespaces(@PathVariable("namespaceName") String namespaceName){
        Map<String, Object> map = new HashMap<>();
        map.put("name", namespaceName);
        return createSuccessResult(map);
    }
}
