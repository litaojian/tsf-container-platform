/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.models;

import lombok.Data;

import java.util.Map;

/**
 * @title ClusterInfo
 * @title Ethan Pau
 * @date 2019/4/2 10:21
 * @description TODO
 * @version Version 1.0
 */
@Data
public class ClusterInfo {

	private Map<String, String>     actions;
	private Allocatable             allocatable;
	private String                  baseType;
	private Capacity                capacity;
	private String                  created;
	private String                  creatorId;
	private String                  id;
	private Limits                  limits;
	private Map<String, String>     links;
	private String                  name;
	private Requested               requested;
	private String                  state;
	private String                  transitioningMessage;
	private String                  type;
	private String                  uuid;

}
