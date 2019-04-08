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
 * @title ClusterInfoDto
 * @date 2019/4/8 17:21
 * @description TODO
 */
@Data
public class ClusterInfoDto implements Serializable {

	private String cidr;
	private String createdAt;
	private String id;
	private String name;
	private String status;
	private String updatedAt;
	private Integer totalNodeNum;
	private Integer runningNodeNum;
	private Integer runningPodNum;

}
