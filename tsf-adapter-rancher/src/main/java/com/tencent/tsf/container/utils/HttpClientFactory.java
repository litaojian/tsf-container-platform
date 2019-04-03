/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * @title HttpClientFactory
 * @title Ethan
 * @date 2019/3/29 11:46
 * @description TODO
 * @version Version 1.0
 */
public class HttpClientFactory {
	private static final RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(15 * 1000)
			.setConnectTimeout(15 * 1000)
			.setSocketTimeout(15 * 1000)
			.build();
	private static final ConnectionConfig connConfig = ConnectionConfig.custom()
			.setCharset(Consts.UTF_8)
			.build();
	private static final ConnectionKeepAliveStrategy keepAliveStrategy =
			new DefaultConnectionKeepAliveStrategy() {
				@Override
				public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
					long keepAlive = super.getKeepAliveDuration(response, context);
					if (keepAlive == -1) {
						keepAlive = 10 * 1000;
					}
					return keepAlive;
				}
			};
	private static final HttpRequestRetryHandler retryHandler = (ioException, exceptionCount, httpContext) -> {
		if (exceptionCount >= 3) {
			// don't retry if over max retry count
			return false;
		}
		if (ioException instanceof UnknownHostException) {
			//unknown host
			return false;
		}
		if (ioException instanceof InterruptedIOException) {
			return false;
		}
		if (ioException instanceof ConnectTimeoutException) {
			return false;
		}
		if (ioException instanceof SSLException) {
			return false;
		}
		HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
		HttpRequest request = clientContext.getRequest();
		boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
		if (idempotent) {
			// retry if the request is considered idempotent.
			return true;
		}
		return false;
	};


	public static CloseableHttpClient createHttpClient() throws Exception {
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, (certificate, authType) -> true).build();


		return HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setDefaultConnectionConfig(connConfig)
				.setKeepAliveStrategy(keepAliveStrategy)
				.setRetryHandler(retryHandler)
				.setSSLContext(sslContext)
				.build();
	}
}
