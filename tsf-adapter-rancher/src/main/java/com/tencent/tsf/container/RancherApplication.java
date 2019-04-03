/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @title RancherApplication
 * @title Ethan Pau
 * @date 2019/4/2 10:57
 * @description TODO
 * @@version Version 1.0
 */

@SpringBootApplication
@EnableConfigurationProperties
public class RancherApplication {
	public static void main (String[] args) {
		SpringApplication.run(RancherApplication.class, args);
	}
}
