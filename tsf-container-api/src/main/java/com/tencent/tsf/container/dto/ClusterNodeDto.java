/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Ethan Pau
 * @version Version 1.0
 * @title ClusterNodeDto
 * @date 2019/4/3 15:50
 * @description TODO
 */
@Data
public class ClusterNodeDto {

	private String id;
	private String wanIp;
	private String lanIp;
	private String status;
	private List<Map<String, String>> conditions;
	private Float cpuTotal;
	private Float cpuLimit;
	private Float cpuRequest;
	private Float memTotal;
	private Float memLimit;
	private Float memRequest;
	private String createdAt;
	private String updatedAt;
	private Map<String, String> labels;

}
