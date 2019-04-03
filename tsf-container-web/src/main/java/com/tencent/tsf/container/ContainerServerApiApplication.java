/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @title ContainerServerApiApplication
 * @title Ethan Pau
 * @date 2019/4/1 18:04
 * @description TODO
 * @version Version 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties
public class ContainerServerApiApplication {

	public static void main (String[] args) {
		SpringApplication.run(ContainerServerApiApplication.class, args);
	}
}
