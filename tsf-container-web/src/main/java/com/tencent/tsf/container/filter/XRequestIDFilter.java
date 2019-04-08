/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ethan Pau
 * @version Version 1.0
 * @title XRequestIDFilter
 * @date 2019/4/4 22:51
 * @description TODO
 */
@Component
public class XRequestIDFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String xRequestId = request.getHeader("X-Request-ID");
		if(StringUtils.isNotBlank(xRequestId)) {
			response.setHeader("X-Request-ID", xRequestId);
		}
		filterChain.doFilter(servletRequest, servletResponse);

	}

	@Override
	public void destroy() {
	}
}
