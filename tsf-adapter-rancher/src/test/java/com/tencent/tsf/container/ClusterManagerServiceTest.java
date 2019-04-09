/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container;

import com.alibaba.fastjson.JSON;
import com.tencent.tsf.container.dto.KubeAPIServerDto;
import com.tencent.tsf.container.service.ClusterManagerService;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @version Version 1.0
 * @title ClusterManagerServiceTest
 * @title Ethan Pau
 * @date 2019/4/2 10:43
 * @description TODO
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RancherApplication.class)
@ContextConfiguration
public class ClusterManagerServiceTest {

	@Autowired
	private ClusterManagerService clusterManagerService;

	//自己本地环境(100.2)
//	private String authorization = "Basic dG9rZW4tcGxqd2Y6cms0czRoNG1xMjhkdmNkY2p0NWs3ODh3d2IyNXh3dHZ2endrOHZ3a3R6bDU2N2g5OW41d2Y5";
	//测试
	private String authorization = "Basic dG9rZW4tczgyNzQ6cGtrNHBmaHZta3FoenJsanJ4Njl6d2M4bjdncTJqamJuYjdsbTRyMnJqNXB6cjhudnoycW5r";
//	private String clusterId = "c-vnlq6";
	private String clusterId = "local";

	@Test
	@Ignore
	public void clusterUsageTest() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", authorization);
		String result = clusterManagerService.clusterUsage(headers, clusterId);
		System.out.println("------------------------------" + result);
	}

	@Test @Ignore
	public void data() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", authorization);
		KubeAPIServerDto result = clusterManagerService.getKubernetesAPIServer(headers, clusterId);
		System.out.println("------------------------------" + JSON.toJSONString(result));
	}


	@Test
	public void podsInfo(){

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", authorization);
		KubeAPIServerDto apiServer = clusterManagerService.getKubernetesAPIServer(headers, clusterId);
		String token = apiServer.getAuthorization().getCredentials();
		String address = apiServer.getAddress();
		address = address.replace("172.16.100.3", "43.254.44.13");
		address = address.replace("10.43.0.1", "ha.rancher.com");

		KubernetesClient kubernetesClient = null;
		Config config = null;
		switch (apiServer.getAuthorization().getType()) {
			case "Bearer":
				config = new ConfigBuilder().withMasterUrl(address).withOauthToken(token)
						.withTrustCerts(true).build();
				break;
			case "Basic":

				System.out.println(token);

				byte[] byteArray = Base64.getDecoder().decode(token.getBytes());
				String decodedToken = new String(byteArray);
				System.out.println(decodedToken);
				String[] parts = decodedToken.split(":");

				config = new ConfigBuilder().withMasterUrl(address).withUsername(parts[0])
						.withPassword(parts[1]).withOauthToken(token).withTrustCerts(true).build();
				break;
			default:
				log.error("Unrecognized authorization type {}", apiServer.getAuthorization().getType());
				throw new RuntimeException("调用kube api 失败");
		}

		kubernetesClient = new DefaultKubernetesClient(config);
		PodList podList = kubernetesClient.pods().list();
		System.out.println(JSON.toJSONString(podList));
	}

}
