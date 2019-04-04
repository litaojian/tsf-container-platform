/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.utils;

import lombok.Data;

/**
 * @author Ethan Pau
 * @version Version 1.0
 * @title ExecResult
 * @date 2019/4/4 10:21
 * @description TODO
 */
@Data
public class ExecResult {
	private String stdout;
	private String stderr;
	private Integer exitCode;

	public ExecResult() {
	}

	public ExecResult(String stdout, String stderr, Integer exitCode) {
		this.stdout = stdout;
		this.stderr = stderr;
		this.exitCode = exitCode;
	}

}
