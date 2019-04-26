package com.tencent.tsf.container.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.tsf.container.config.RancherServerAPI;
import com.tencent.tsf.container.config.RancherServerPath;
import com.tencent.tsf.container.service.NamespaceManagerService;
import com.tencent.tsf.container.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NamespaceManagerServiceImpl implements NamespaceManagerService {

    @Autowired
    RancherServerPath rancherServerPath;

    @Override
    public String createNamespace(Map<String, String> headers, String clusterId, Map<String, Object> params) {
        Assert.hasLength(clusterId, "集群ID不能为空！");

        String url = rancherServerPath.createNamespaceUrl(clusterId);
        String result = HttpClientUtil.doPost(url, headers, params);
        return result;
    }

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

    @Override
    public String deleteNamespace(Map<String, String> headers, String clusterId, String namespaceId) {
        Assert.hasLength(clusterId, "集群ID不能为空！");
        Assert.hasLength(namespaceId, "命名空间ID不能为空！");

        String url = rancherServerPath.deleteNamespaceUrl(clusterId, namespaceId);
        String result = HttpClientUtil.doDelete(url, headers);
        return result;
    }

    @Override
    public String getclusterProject(Map<String, String> headers, String clusterId) {
        Assert.hasLength(clusterId, "集群ID不能为空！");

        String url = rancherServerPath.getClusterProjectsUrl(clusterId);
        String result = HttpClientUtil.doGet(url, headers);
        JSONArray jsonArray = JSON.parseObject(result).getJSONArray("data");
        JSONObject project = null;
        for (Object o : jsonArray) {
            JSONObject item = (JSONObject) o;
            if ("Default".equals(item.getString("name"))) {
                project = item;
            }
        }
        return JSON.toJSONString(project);
    }

}
