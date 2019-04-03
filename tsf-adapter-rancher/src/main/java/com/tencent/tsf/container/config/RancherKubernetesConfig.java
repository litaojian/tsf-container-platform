/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.config;/**
 * 123456789
 **/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @title RancherKubernetesConfig
 * @title Ethan Pau
 * @date 2019/4/1 16:36
 * @description TODO
 * @version Version 1.0
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "rancher.kubernetes-engine")
public class RancherKubernetesConfig {

	private Integer         addonJobTimeout         = 30;
	private Boolean         ignoreDockerVersion     = true;
	private Boolean         sshAgentAuth            = false;
	private String          type                    = "rancherKubernetesEngineConfig";
	private String          kubernetesVersion       = "v1.13.5-rancher1-1";
	private Authentication	authentication          = new Authentication();
	private Network         network                 = new Network();
	private Ingress         ingress                 = new Ingress();
	private Monitoring      monitoring              = new Monitoring();
	private Services        services                = new Services();
	private CloudProvider   cloudProvider           = new CloudProvider();

	@Data
	public class CloudProvider {
		private String type = "cloudProvider";
		private Map<String, Object> cloudConfig = Collections.EMPTY_MAP;
	}

	@Data
	public class Etcd {
		private String creation = "12h";
		private Map<String, String> extraArgs;
		private String retention = "72h";
		private Boolean snapshot = true;
		private String type = "etcdService";

		{
			extraArgs = new HashMap<>();
			extraArgs.put("heartbeat-interval", "500");
			extraArgs.put("election-timeout", "5000");
		}
	}

//	@Data
//	public class ExtraArgs {
//		private Integer heartbeatInterval = 500;
//		private Integer electionTimeout = 5000;
//	}

	@Data
	public class KubeApi{
		private Boolean podSecurityPolicy = false;
		private String serviceNodePortRange = "30000-32767";
		private String type = "kubeAPIService";
	}
	@Data
	public class Services{
		private String type ="monitoringConfig";
		private KubeApi kubeApi = new KubeApi();
		private Etcd etcd = new Etcd();

	}

	@Data
	public class Monitoring {
		private String provider = "metrics-server";
		private String type = "monitoringConfig";
	}

	@Data
	public class Ingress{
		private String provider = "nginx";
		private String type = "ingressConfig";
	}

	@Data
	public class Authentication {
		private String strategy = "x509";
		private String type = "authnConfig";
	}

	@Data
	public class Options {
		private String flannel_backend_type = "vxlan";
	}

	@Data
	public class Network {
		private String plugin = "canal";
		private String type = "networkConfig";
		private Options options  = new Options();
	}
}
