/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.controller;

import com.tencent.tsf.container.dto.BaseResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @title BaseController
 * @title Ethan
 * @date 2019/3/29 10:35
 * @description TODO
 * @version Version 1.0
 */

@Slf4j
@RestController
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found"),
		@ApiResponse(code = 500, message = "Failure")})
public class BaseController {

	private final String HEADER_AUTHORIZATION = "Authorization";

	protected String getAuthorization(HttpServletRequest request) {
		if(request == null) return "";
		String authorization = request.getHeader(this.HEADER_AUTHORIZATION);
		return authorization;
	}

	protected Map<String, String> getCustomHeaders(HttpServletRequest request) {
		Map<String, String> headers = new HashMap<>();
		String authorization = getAuthorization(request);
		headers.put(this.HEADER_AUTHORIZATION, authorization);

		return headers;
	}

	protected BaseResponse createSuccessResult(Object data){
		BaseResponse result = new BaseResponse();
		result.setCode(0);
		result.setMessage("");
		result.setData(data);
		return result;
	}

	protected BaseResponse createSuccessResult(){
		BaseResponse result = new BaseResponse();
		result.setCode(0);
		result.setMessage("");
		return result;
	}

	protected Map<String, Object> getRequestParams(HttpServletRequest request) {
		if(request == null) return Collections.EMPTY_MAP;
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> param = new HashMap<>();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			Object value = request.getParameter(name);
			if(value == null) continue;
			param.put(name, value);
		}
		return param;
	}
}
