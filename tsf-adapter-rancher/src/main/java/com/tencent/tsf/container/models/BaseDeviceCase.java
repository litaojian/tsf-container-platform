/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.models;

import lombok.Data;

/**
 * @title BaseDeviceCase
 * @title Ethan Pau
 * @date 2019/4/2 10:28
 * @description TODO
 * @version Version 1.0
 */

@Data
public class BaseDeviceCase {

	private String cpu;
	private String memory;
	private String pods;

}
