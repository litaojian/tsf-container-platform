/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.models;

import lombok.Data;

/**
 * @Title BaseDeviceCase
 * @Author Ethan Pau
 * @Date 2019/4/2 10:28
 * @Description TODO
 * @Version Version 1.0
 */

@Data
public class BaseDeviceCase {

	private String cpu;
	private String memory;
	private String pods;

}
