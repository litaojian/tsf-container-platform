package com.tencent.tsf.container.impl;

import com.tencent.tsf.container.config.RancherServerPath;
import com.tencent.tsf.container.service.NamespaceManagerService;
import com.tencent.tsf.container.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

@Slf4j
@Service
public class NamespaceManagerServiceImpl implements NamespaceManagerService {
    @Autowired
    RancherServerPath rancherServerPath;
    @Override
    public String getNamespaceById(Map<String, String> headers, String clusterId, String namespaceId) {
        Assert.hasLength(clusterId, "集群ID不能为空！");
        Assert.hasLength(namespaceId, "命名空间ID不能为空！");

        String url = rancherServerPath.namespaceInfoUrl(clusterId, namespaceId);
        String result = HttpClientUtil.doGet(url, headers);
        return result;
    }

    @Override
    public String getNamespaces(Map<String, String> headers, Map<String, Object> params, String clusterId) {
        Assert.hasLength(clusterId, "集群ID不能为空！");

        String url = rancherServerPath.getAllNamespacesUrl(clusterId, params);
        String result = HttpClientUtil.doGet(url, headers);
        return result;
    }

}
