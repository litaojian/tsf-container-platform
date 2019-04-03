package com.tencent.tsf.container.service;

import java.util.Map;

public interface NamespaceManagerService {

    String getNamespaceById(Map<String, String> headers, String clusterId, String namespaceId);

    String getNamespaces(Map<String, String> headers, Map<String, Object> params, String clusterId);
}
