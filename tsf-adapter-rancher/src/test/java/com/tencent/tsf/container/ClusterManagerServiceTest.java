/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container;

import com.tencent.tsf.container.impl.ClusterManagerServiceImpl;
import com.tencent.tsf.container.service.ClusterManagerService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title ClusterManagerServiceTest
 * @Author Ethan Pau
 * @Date 2019/4/2 10:43
 * @Description TODO
 * @Version Version 1.0
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
	public void clusterUsageTest(){
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", authorization);
		String result = clusterManagerService.clusterUsage(headers, clusterId);
		System.out.println("------------------------------" + result);
	}
}
