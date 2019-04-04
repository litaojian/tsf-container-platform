/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.tsf.container.dto.ClusterNodeDto;
import com.tencent.tsf.container.service.ClusterManagerService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @version Version 1.0
 * @title ClusterManagerServiceTest
 * @title Ethan Pau
 * @date 2019/4/2 10:43
 * @description TODO
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RancherApplication.class)
@ContextConfiguration
public class ClusterManagerServiceTest {

	@Autowired
	private ClusterManagerService clusterManagerService;

	private String authorization = "Basic dG9rZW4tcGxqd2Y6cms0czRoNG1xMjhkdmNkY2p0NWs3ODh3d2IyNXh3dHZ2endrOHZ3a3R6bDU2N2g5OW41d2Y5";
	private String clusterId = "c-nd5w4";

	@Test
	@Ignore
	public void clusterUsageTest() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", authorization);
		String result = clusterManagerService.clusterUsage(headers, clusterId);
		System.out.println("------------------------------" + result);
	}

	@Test
	public void data() {
		String content = "{\n" +
				"    \"type\": \"collection\",\n" +
				"    \"links\": {\n" +
				"        \"self\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes\"\n" +
				"    },\n" +
				"    \"createTypes\": {\n" +
				"        \"node\": \"https://43.254.44.13/v3/nodes\"\n" +
				"    },\n" +
				"    \"actions\": {},\n" +
				"    \"pagination\": {\n" +
				"        \"limit\": 1000,\n" +
				"        \"total\": 3\n" +
				"    },\n" +
				"    \"sort\": {\n" +
				"        \"order\": \"asc\",\n" +
				"        \"reverse\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?order=desc\",\n" +
				"        \"links\": {\n" +
				"            \"description\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=description\",\n" +
				"            \"externalIpAddress\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=externalIpAddress\",\n" +
				"            \"hostname\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=hostname\",\n" +
				"            \"ipAddress\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=ipAddress\",\n" +
				"            \"name\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=name\",\n" +
				"            \"nodeName\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=nodeName\",\n" +
				"            \"podCidr\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=podCidr\",\n" +
				"            \"providerId\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=providerId\",\n" +
				"            \"sshUser\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=sshUser\",\n" +
				"            \"state\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=state\",\n" +
				"            \"transitioning\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=transitioning\",\n" +
				"            \"transitioningMessage\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=transitioningMessage\",\n" +
				"            \"uuid\": \"https://43.254.44.13/v3/clusters/c-4v4mw/nodes?sort=uuid\"\n" +
				"        }\n" +
				"    },\n" +
				"    \"filters\": {\n" +
				"        \"clusterId\": null,\n" +
				"        \"controlPlane\": null,\n" +
				"        \"created\": null,\n" +
				"        \"creatorId\": null,\n" +
				"        \"description\": null,\n" +
				"        \"etcd\": null,\n" +
				"        \"externalIpAddress\": null,\n" +
				"        \"hostname\": null,\n" +
				"        \"id\": null,\n" +
				"        \"imported\": null,\n" +
				"        \"ipAddress\": null,\n" +
				"        \"name\": null,\n" +
				"        \"namespaceId\": null,\n" +
				"        \"nodeName\": null,\n" +
				"        \"nodePoolId\": null,\n" +
				"        \"nodeTemplateId\": null,\n" +
				"        \"podCidr\": null,\n" +
				"        \"providerId\": null,\n" +
				"        \"removed\": null,\n" +
				"        \"requestedHostname\": null,\n" +
				"        \"sshUser\": null,\n" +
				"        \"state\": null,\n" +
				"        \"transitioning\": null,\n" +
				"        \"transitioningMessage\": null,\n" +
				"        \"unschedulable\": null,\n" +
				"        \"uuid\": null,\n" +
				"        \"worker\": null\n" +
				"    },\n" +
				"    \"resourceType\": \"node\",\n" +
				"    \"data\": [\n" +
				"        {\n" +
				"            \"actions\": {\n" +
				"                \"cordon\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-2bb09c67b3b6?action=cordon\",\n" +
				"                \"drain\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-2bb09c67b3b6?action=drain\"\n" +
				"            },\n" +
				"            \"allocatable\": {\n" +
				"                \"cpu\": \"2\",\n" +
				"                \"ephemeral-storage\": \"18902281390\",\n" +
				"                \"hugepages-2Mi\": \"0\",\n" +
				"                \"memory\": \"3778004Ki\",\n" +
				"                \"pods\": \"110\"\n" +
				"            },\n" +
				"            \"annotations\": {\n" +
				"                \"flannel.alpha.coreos.com/backend-data\": \"{\\\"VtepMAC\\\":\\\"92:16:3f:26:c7:3f\\\"}\",\n" +
				"                \"flannel.alpha.coreos.com/backend-type\": \"vxlan\",\n" +
				"                \"flannel.alpha.coreos.com/kube-subnet-manager\": \"true\",\n" +
				"                \"flannel.alpha.coreos.com/public-ip\": \"172.16.100.4\",\n" +
				"                \"node.alpha.kubernetes.io/ttl\": \"0\",\n" +
				"                \"rke.cattle.io/external-ip\": \"172.16.100.4\",\n" +
				"                \"rke.cattle.io/internal-ip\": \"172.16.100.4\",\n" +
				"                \"volumes.kubernetes.io/controller-managed-attach-detach\": \"true\"\n" +
				"            },\n" +
				"            \"baseType\": \"node\",\n" +
				"            \"capacity\": {\n" +
				"                \"cpu\": \"2\",\n" +
				"                \"ephemeral-storage\": \"20510288Ki\",\n" +
				"                \"hugepages-2Mi\": \"0\",\n" +
				"                \"memory\": \"3880404Ki\",\n" +
				"                \"pods\": \"110\"\n" +
				"            },\n" +
				"            \"clusterId\": \"c-4v4mw\",\n" +
				"            \"conditions\": [\n" +
				"                {\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Initialized\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"message\": \"registered with kubernetes\",\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Registered\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Provisioned\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T02:16:21Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554257781000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T02:10:00Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554257400000,\n" +
				"                    \"message\": \"kubelet has sufficient memory available\",\n" +
				"                    \"reason\": \"KubeletHasSufficientMemory\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"MemoryPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T02:16:21Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554257781000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T02:10:00Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554257400000,\n" +
				"                    \"message\": \"kubelet has no disk pressure\",\n" +
				"                    \"reason\": \"KubeletHasNoDiskPressure\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"DiskPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T02:16:21Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554257781000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T02:10:00Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554257400000,\n" +
				"                    \"message\": \"kubelet has sufficient PID available\",\n" +
				"                    \"reason\": \"KubeletHasSufficientPID\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"PIDPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T02:16:21Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554257781000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T02:11:41Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554257501000,\n" +
				"                    \"message\": \"kubelet is posting ready status\",\n" +
				"                    \"reason\": \"KubeletReady\",\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Ready\"\n" +
				"                }\n" +
				"            ],\n" +
				"            \"controlPlane\": false,\n" +
				"            \"created\": \"2019-04-03T02:04:51Z\",\n" +
				"            \"createdTS\": 1554257091000,\n" +
				"            \"creatorId\": null,\n" +
				"            \"customConfig\": {\n" +
				"                \"address\": \"172.16.100.4\",\n" +
				"                \"type\": \"/v3/schemas/customConfig\"\n" +
				"            },\n" +
				"            \"dockerInfo\": {\n" +
				"                \"debug\": false,\n" +
				"                \"experimentalBuild\": false,\n" +
				"                \"type\": \"/v3/schemas/dockerInfo\"\n" +
				"            },\n" +
				"            \"etcd\": false,\n" +
				"            \"externalIpAddress\": \"172.16.100.4\",\n" +
				"            \"hostname\": \"i-zbtikoxp\",\n" +
				"            \"id\": \"c-4v4mw:m-2bb09c67b3b6\",\n" +
				"            \"imported\": true,\n" +
				"            \"info\": {\n" +
				"                \"cpu\": {\n" +
				"                    \"count\": 2\n" +
				"                },\n" +
				"                \"kubernetes\": {\n" +
				"                    \"kubeProxyVersion\": \"v1.13.5\",\n" +
				"                    \"kubeletVersion\": \"v1.13.5\"\n" +
				"                },\n" +
				"                \"memory\": {\n" +
				"                    \"memTotalKiB\": 3880404\n" +
				"                },\n" +
				"                \"os\": {\n" +
				"                    \"dockerVersion\": \"18.9.4\",\n" +
				"                    \"kernelVersion\": \"3.10.0-957.10.1.el7.x86_64\",\n" +
				"                    \"operatingSystem\": \"CentOS Linux 7 (Core)\"\n" +
				"                }\n" +
				"            },\n" +
				"            \"ipAddress\": \"172.16.100.4\",\n" +
				"            \"labels\": {\n" +
				"                \"beta.kubernetes.io/arch\": \"amd64\",\n" +
				"                \"beta.kubernetes.io/os\": \"linux\",\n" +
				"                \"kubernetes.io/hostname\": \"i-zbtikoxp\",\n" +
				"                \"node-role.kubernetes.io/worker\": \"true\"\n" +
				"            },\n" +
				"            \"limits\": {\n" +
				"                \"cpu\": \"10m\",\n" +
				"                \"memory\": \"190Mi\"\n" +
				"            },\n" +
				"            \"links\": {\n" +
				"                \"remove\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-2bb09c67b3b6\",\n" +
				"                \"self\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-2bb09c67b3b6\",\n" +
				"                \"update\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-2bb09c67b3b6\"\n" +
				"            },\n" +
				"            \"name\": \"\",\n" +
				"            \"namespaceId\": null,\n" +
				"            \"nodeName\": \"i-zbtikoxp\",\n" +
				"            \"nodePoolId\": \"\",\n" +
				"            \"nodeTemplateId\": null,\n" +
				"            \"podCidr\": \"10.42.1.0/24\",\n" +
				"            \"requested\": {\n" +
				"                \"cpu\": \"540m\",\n" +
				"                \"memory\": \"140Mi\",\n" +
				"                \"pods\": \"8\"\n" +
				"            },\n" +
				"            \"requestedHostname\": \"i-zbtikoxp\",\n" +
				"            \"sshUser\": \"root\",\n" +
				"            \"state\": \"active\",\n" +
				"            \"transitioning\": \"no\",\n" +
				"            \"transitioningMessage\": \"\",\n" +
				"            \"type\": \"node\",\n" +
				"            \"unschedulable\": false,\n" +
				"            \"uuid\": \"ddcb0d37-55b4-11e9-bbb1-0242ac110002\",\n" +
				"            \"worker\": true\n" +
				"        },\n" +
				"        {\n" +
				"            \"allocatable\": {\n" +
				"                \"cpu\": \"4\",\n" +
				"                \"ephemeral-storage\": \"18902281390\",\n" +
				"                \"hugepages-2Mi\": \"0\",\n" +
				"                \"memory\": \"7906460Ki\",\n" +
				"                \"pods\": \"110\"\n" +
				"            },\n" +
				"            \"annotations\": {\n" +
				"                \"flannel.alpha.coreos.com/backend-data\": \"{\\\"VtepMAC\\\":\\\"b6:0c:90:b7:25:2f\\\"}\",\n" +
				"                \"flannel.alpha.coreos.com/backend-type\": \"vxlan\",\n" +
				"                \"flannel.alpha.coreos.com/kube-subnet-manager\": \"true\",\n" +
				"                \"flannel.alpha.coreos.com/public-ip\": \"172.16.100.3\",\n" +
				"                \"node.alpha.kubernetes.io/ttl\": \"0\",\n" +
				"                \"rke.cattle.io/external-ip\": \"172.16.100.3\",\n" +
				"                \"rke.cattle.io/internal-ip\": \"172.16.100.3\",\n" +
				"                \"volumes.kubernetes.io/controller-managed-attach-detach\": \"true\"\n" +
				"            },\n" +
				"            \"baseType\": \"node\",\n" +
				"            \"capacity\": {\n" +
				"                \"cpu\": \"4\",\n" +
				"                \"ephemeral-storage\": \"20510288Ki\",\n" +
				"                \"hugepages-2Mi\": \"0\",\n" +
				"                \"memory\": \"8008860Ki\",\n" +
				"                \"pods\": \"110\"\n" +
				"            },\n" +
				"            \"clusterId\": \"c-4v4mw\",\n" +
				"            \"conditions\": [\n" +
				"                {\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Initialized\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"message\": \"registered with kubernetes\",\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Registered\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Provisioned\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-02T11:47:26Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554205646000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-02T11:45:46Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554205546000,\n" +
				"                    \"message\": \"kubelet has sufficient memory available\",\n" +
				"                    \"reason\": \"KubeletHasSufficientMemory\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"MemoryPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-02T11:47:26Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554205646000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-02T11:45:46Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554205546000,\n" +
				"                    \"message\": \"kubelet has no disk pressure\",\n" +
				"                    \"reason\": \"KubeletHasNoDiskPressure\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"DiskPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-02T11:47:26Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554205646000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-02T11:45:46Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554205546000,\n" +
				"                    \"message\": \"kubelet has sufficient PID available\",\n" +
				"                    \"reason\": \"KubeletHasSufficientPID\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"PIDPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-02T11:47:26Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554205646000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-02T11:46:56Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554205616000,\n" +
				"                    \"message\": \"kubelet is posting ready status\",\n" +
				"                    \"reason\": \"KubeletReady\",\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Ready\"\n" +
				"                }\n" +
				"            ],\n" +
				"            \"controlPlane\": true,\n" +
				"            \"created\": \"2019-04-02T11:38:32Z\",\n" +
				"            \"createdTS\": 1554205112000,\n" +
				"            \"creatorId\": null,\n" +
				"            \"customConfig\": {\n" +
				"                \"address\": \"172.16.100.3\",\n" +
				"                \"type\": \"/v3/schemas/customConfig\"\n" +
				"            },\n" +
				"            \"dockerInfo\": {\n" +
				"                \"debug\": false,\n" +
				"                \"experimentalBuild\": false,\n" +
				"                \"type\": \"/v3/schemas/dockerInfo\"\n" +
				"            },\n" +
				"            \"etcd\": true,\n" +
				"            \"externalIpAddress\": \"172.16.100.3\",\n" +
				"            \"hostname\": \"i-ms7v7ifx\",\n" +
				"            \"id\": \"c-4v4mw:m-5b60307b9e51\",\n" +
				"            \"imported\": true,\n" +
				"            \"info\": {\n" +
				"                \"cpu\": {\n" +
				"                    \"count\": 4\n" +
				"                },\n" +
				"                \"kubernetes\": {\n" +
				"                    \"kubeProxyVersion\": \"v1.13.5\",\n" +
				"                    \"kubeletVersion\": \"v1.13.5\"\n" +
				"                },\n" +
				"                \"memory\": {\n" +
				"                    \"memTotalKiB\": 8008860\n" +
				"                },\n" +
				"                \"os\": {\n" +
				"                    \"dockerVersion\": \"18.9.4\",\n" +
				"                    \"kernelVersion\": \"3.10.0-957.10.1.el7.x86_64\",\n" +
				"                    \"operatingSystem\": \"CentOS Linux 7 (Core)\"\n" +
				"                }\n" +
				"            },\n" +
				"            \"ipAddress\": \"172.16.100.3\",\n" +
				"            \"labels\": {\n" +
				"                \"beta.kubernetes.io/arch\": \"amd64\",\n" +
				"                \"beta.kubernetes.io/os\": \"linux\",\n" +
				"                \"kubernetes.io/hostname\": \"i-ms7v7ifx\",\n" +
				"                \"node-role.kubernetes.io/controlplane\": \"true\",\n" +
				"                \"node-role.kubernetes.io/etcd\": \"true\"\n" +
				"            },\n" +
				"            \"links\": {\n" +
				"                \"remove\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-5b60307b9e51\",\n" +
				"                \"self\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-5b60307b9e51\",\n" +
				"                \"update\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-5b60307b9e51\"\n" +
				"            },\n" +
				"            \"name\": \"\",\n" +
				"            \"namespaceId\": null,\n" +
				"            \"nodeName\": \"i-ms7v7ifx\",\n" +
				"            \"nodePoolId\": \"\",\n" +
				"            \"nodeTemplateId\": null,\n" +
				"            \"podCidr\": \"10.42.0.0/24\",\n" +
				"            \"requested\": {\n" +
				"                \"cpu\": \"250m\",\n" +
				"                \"pods\": \"2\"\n" +
				"            },\n" +
				"            \"requestedHostname\": \"i-ms7v7ifx\",\n" +
				"            \"sshUser\": \"root\",\n" +
				"            \"state\": \"active\",\n" +
				"            \"taints\": [\n" +
				"                {\n" +
				"                    \"effect\": \"NoSchedule\",\n" +
				"                    \"key\": \"node-role.kubernetes.io/controlplane\",\n" +
				"                    \"type\": \"/v3/schemas/taint\",\n" +
				"                    \"value\": \"true\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"effect\": \"NoExecute\",\n" +
				"                    \"key\": \"node-role.kubernetes.io/etcd\",\n" +
				"                    \"type\": \"/v3/schemas/taint\",\n" +
				"                    \"value\": \"true\"\n" +
				"                }\n" +
				"            ],\n" +
				"            \"transitioning\": \"no\",\n" +
				"            \"transitioningMessage\": \"\",\n" +
				"            \"type\": \"node\",\n" +
				"            \"unschedulable\": false,\n" +
				"            \"uuid\": \"d7e9fa8f-553b-11e9-bbb1-0242ac110002\",\n" +
				"            \"worker\": false\n" +
				"        },\n" +
				"        {\n" +
				"            \"actions\": {\n" +
				"                \"cordon\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-c766d5f9906b?action=cordon\",\n" +
				"                \"drain\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-c766d5f9906b?action=drain\"\n" +
				"            },\n" +
				"            \"allocatable\": {\n" +
				"                \"cpu\": \"2\",\n" +
				"                \"ephemeral-storage\": \"18902281390\",\n" +
				"                \"hugepages-2Mi\": \"0\",\n" +
				"                \"memory\": \"3778016Ki\",\n" +
				"                \"pods\": \"110\"\n" +
				"            },\n" +
				"            \"annotations\": {\n" +
				"                \"flannel.alpha.coreos.com/backend-data\": \"{\\\"VtepMAC\\\":\\\"22:ce:3b:90:51:b9\\\"}\",\n" +
				"                \"flannel.alpha.coreos.com/backend-type\": \"vxlan\",\n" +
				"                \"flannel.alpha.coreos.com/kube-subnet-manager\": \"true\",\n" +
				"                \"flannel.alpha.coreos.com/public-ip\": \"172.16.100.5\",\n" +
				"                \"node.alpha.kubernetes.io/ttl\": \"0\",\n" +
				"                \"rke.cattle.io/external-ip\": \"172.16.100.5\",\n" +
				"                \"rke.cattle.io/internal-ip\": \"172.16.100.5\",\n" +
				"                \"volumes.kubernetes.io/controller-managed-attach-detach\": \"true\"\n" +
				"            },\n" +
				"            \"baseType\": \"node\",\n" +
				"            \"capacity\": {\n" +
				"                \"cpu\": \"2\",\n" +
				"                \"ephemeral-storage\": \"20510288Ki\",\n" +
				"                \"hugepages-2Mi\": \"0\",\n" +
				"                \"memory\": \"3880416Ki\",\n" +
				"                \"pods\": \"110\"\n" +
				"            },\n" +
				"            \"clusterId\": \"c-4v4mw\",\n" +
				"            \"conditions\": [\n" +
				"                {\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Initialized\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"message\": \"registered with kubernetes\",\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Registered\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Provisioned\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T04:08:15Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554264495000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T04:03:04Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554264184000,\n" +
				"                    \"message\": \"kubelet has sufficient memory available\",\n" +
				"                    \"reason\": \"KubeletHasSufficientMemory\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"MemoryPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T04:08:15Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554264495000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T04:03:04Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554264184000,\n" +
				"                    \"message\": \"kubelet has no disk pressure\",\n" +
				"                    \"reason\": \"KubeletHasNoDiskPressure\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"DiskPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T04:08:15Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554264495000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T04:03:04Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554264184000,\n" +
				"                    \"message\": \"kubelet has sufficient PID available\",\n" +
				"                    \"reason\": \"KubeletHasSufficientPID\",\n" +
				"                    \"status\": \"False\",\n" +
				"                    \"type\": \"PIDPressure\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"lastHeartbeatTime\": \"2019-04-03T04:08:15Z\",\n" +
				"                    \"lastHeartbeatTimeTS\": 1554264495000,\n" +
				"                    \"lastTransitionTime\": \"2019-04-03T04:03:44Z\",\n" +
				"                    \"lastTransitionTimeTS\": 1554264224000,\n" +
				"                    \"message\": \"kubelet is posting ready status\",\n" +
				"                    \"reason\": \"KubeletReady\",\n" +
				"                    \"status\": \"True\",\n" +
				"                    \"type\": \"Ready\"\n" +
				"                }\n" +
				"            ],\n" +
				"            \"controlPlane\": false,\n" +
				"            \"created\": \"2019-04-03T03:57:26Z\",\n" +
				"            \"createdTS\": 1554263846000,\n" +
				"            \"creatorId\": null,\n" +
				"            \"customConfig\": {\n" +
				"                \"address\": \"172.16.100.5\",\n" +
				"                \"type\": \"/v3/schemas/customConfig\"\n" +
				"            },\n" +
				"            \"dockerInfo\": {\n" +
				"                \"debug\": false,\n" +
				"                \"experimentalBuild\": false,\n" +
				"                \"type\": \"/v3/schemas/dockerInfo\"\n" +
				"            },\n" +
				"            \"etcd\": false,\n" +
				"            \"externalIpAddress\": \"172.16.100.5\",\n" +
				"            \"hostname\": \"i-ukk27f5b\",\n" +
				"            \"id\": \"c-4v4mw:m-c766d5f9906b\",\n" +
				"            \"imported\": true,\n" +
				"            \"info\": {\n" +
				"                \"cpu\": {\n" +
				"                    \"count\": 2\n" +
				"                },\n" +
				"                \"kubernetes\": {\n" +
				"                    \"kubeProxyVersion\": \"v1.13.5\",\n" +
				"                    \"kubeletVersion\": \"v1.13.5\"\n" +
				"                },\n" +
				"                \"memory\": {\n" +
				"                    \"memTotalKiB\": 3880416\n" +
				"                },\n" +
				"                \"os\": {\n" +
				"                    \"dockerVersion\": \"18.9.4\",\n" +
				"                    \"kernelVersion\": \"3.10.0-957.10.1.el7.x86_64\",\n" +
				"                    \"operatingSystem\": \"CentOS Linux 7 (Core)\"\n" +
				"                }\n" +
				"            },\n" +
				"            \"ipAddress\": \"172.16.100.5\",\n" +
				"            \"labels\": {\n" +
				"                \"beta.kubernetes.io/arch\": \"amd64\",\n" +
				"                \"beta.kubernetes.io/os\": \"linux\",\n" +
				"                \"kubernetes.io/hostname\": \"i-ukk27f5b\",\n" +
				"                \"node-role.kubernetes.io/worker\": \"true\"\n" +
				"            },\n" +
				"            \"links\": {\n" +
				"                \"remove\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-c766d5f9906b\",\n" +
				"                \"self\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-c766d5f9906b\",\n" +
				"                \"update\": \"https://43.254.44.13/v3/nodes/c-4v4mw:m-c766d5f9906b\"\n" +
				"            },\n" +
				"            \"name\": \"\",\n" +
				"            \"namespaceId\": null,\n" +
				"            \"nodeName\": \"i-ukk27f5b\",\n" +
				"            \"nodePoolId\": \"\",\n" +
				"            \"nodeTemplateId\": null,\n" +
				"            \"podCidr\": \"10.42.2.0/24\",\n" +
				"            \"requested\": {\n" +
				"                \"cpu\": \"250m\",\n" +
				"                \"pods\": \"3\"\n" +
				"            },\n" +
				"            \"requestedHostname\": \"i-ukk27f5b\",\n" +
				"            \"sshUser\": \"root\",\n" +
				"            \"state\": \"active\",\n" +
				"            \"transitioning\": \"no\",\n" +
				"            \"transitioningMessage\": \"\",\n" +
				"            \"type\": \"node\",\n" +
				"            \"unschedulable\": false,\n" +
				"            \"uuid\": \"985a69d6-55c4-11e9-bbb1-0242ac110002\",\n" +
				"            \"worker\": true\n" +
				"        }\n" +
				"    ]\n" +
				"}";
		JSONObject obj = JSON.parseObject(content);
		JSONArray data = (JSONArray) obj.get("data");
		if (data == null || data.size() == 0) {
			System.out.println("=============================");
		}
		data.stream().forEach(it -> {
			if (Boolean.TRUE.equals(((JSONObject) it).get("worker"))) {
				JSONObject item = (JSONObject) it;
				ClusterNodeDto info = JSON.parseObject(JSON.toJSONString(it), ClusterNodeDto.class);
				info.setCpuLimit(null);
				info.setCpuRequest(null);
				info.setCpuTotal(null);
				info.setMemLimit(null);
				info.setMemRequest(null);
				info.setMemTotal(null);
				info.setWanIp(item.getString("externalIpAddress"));
				info.setLanIp(item.getString("ipAddress"));
				info.setStatus(item.getString("state"));
				info.setCreatedAt(item.getString("created"));
				info.setUpdatedAt(null);
				System.out.println(info);

			}
		});
	}
}
