/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.dto;

import lombok.Data;

/**
 * @Title BaseResponse
 * @Author Ethan
 * @Date 2019/3/29 08:22
 * @Description TODO
 * @Version Version 1.0
 */
@Data
public class BaseResponse {

	private Integer code;
	private String message;
	private Object data;

	public BaseResponse(){
		this.code = 0;
		this.message = "";
	}
}
