/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ethan Pau
 * @version Version 1.0
 * @title ClusterVMDto
 * @date 2019/4/3 12:12
 * @description TODO
 */

@Data
public class ClusterVMDto implements Serializable {

	private String clusterId;
	private String username;
	private String ip;
	private String password;
	private Integer port = 22;
	private Boolean isMaster;

}
