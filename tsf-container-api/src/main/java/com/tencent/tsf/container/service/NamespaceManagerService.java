package com.tencent.tsf.container.service;

import java.util.Map;

public interface NamespaceManagerService {

    String createNamespace(Map<String, String> headers,String clusterId, Map<String, Object> params);

    String getNamespaceById(Map<String, String> headers, String clusterId, String namespaceId);

    String getNamespaces(Map<String, String> headers, Map<String, Object> params, String clusterId);

    String deleteNamespace(Map<String, String> headers, String clusterId, String namespaceId);

    String getclusterProject(Map<String, String> headers, String clusterId);
}
