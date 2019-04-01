/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Title HttpClientUtil
 * @Author Ethan
 * @Date 2019/3/29 11:41
 * @Description TODO
 * @Version Version 1.0
 */
@Slf4j
public class HttpClientUtil {

	public static String doPost(String url, Map<String, String> headers, Map<String, Object> param) {
		try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
			HttpPost httpPost = new HttpPost(url);
			//设置请求参数
			setRequestParams(httpPost, param);
			//设置请求headers
			setRequestHeaders(httpPost, headers);

			HttpResponse response = httpClient.execute(httpPost);
			//TODO response headers 是否需要返回
			Header[] responseHeaders = response.getAllHeaders();
			log.info("all headers info: ");
			Arrays.stream(responseHeaders)
					.forEach(it -> log.info("---- {}: {}", it.getName(), it.getValue()));

			return responseToStringEntity(response);
		} catch (Exception ex) {
			log.error("", ex);
		}
		return StringUtils.EMPTY;
	}

	private static String responseToStringEntity(HttpResponse response) throws IOException{
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				return EntityUtils.toString(resEntity, Consts.UTF_8.name());
			}
		}
		return StringUtils.EMPTY;
	}

	//请求设置header
	private static void setRequestHeaders(HttpRequestBase httpRequest, Map<String, String> headers) {
		if (!CollectionUtils.isEmpty(headers)) {
			Iterator iteratorSet = headers.entrySet().iterator();
			while (iteratorSet.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iteratorSet.next();
				httpRequest.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	//post请求设置参数
	private static void setRequestParams(HttpPost httpPost, Map<String, Object> param)
			throws UnsupportedEncodingException {
		List<NameValuePair> list = new ArrayList<>();
		Iterator iterator = param.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> elem = (Map.Entry<String, Object>) iterator.next();
			if(elem.getValue() != null)
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue().toString()));
		}
		if (list.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8.name());
			httpPost.setEntity(entity);
		}
	}

	public static String doPost(String url, Map<String, String> headers, String param) {
		try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
			HttpPost httpPost = new HttpPost(url);
			//设置请求headers
			setRequestHeaders(httpPost, headers);
			StringEntity entity = new StringEntity(param, Consts.UTF_8.name());
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			//TODO response headers 是否需要返回
			Header[] responseHeaders = response.getAllHeaders();
			log.info("all headers info: ");
			Arrays.stream(responseHeaders)
					.forEach(it -> log.info("---- {}: {}", it.getName(), it.getValue()));
			return responseToStringEntity(response);
		} catch (Exception ex) {
			log.error("", ex);
		}
		return StringUtils.EMPTY;
	}


	public static String doGet(String url, Map<String, String> headers) {
		String result = null;
		try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
			HttpGet httpGet = new HttpGet(url);
			setRequestHeaders(httpGet, headers);
			CloseableHttpResponse response = httpClient.execute(httpGet);

			//TODO response headers 是否需要返回
			Header[] responseHeaders = response.getAllHeaders();
			log.info("all headers info: >>>>>>");
			Arrays.stream(responseHeaders)
					.forEach(it -> log.info("---- {}: {}", it.getName(), it.getValue()));
			return responseToStringEntity(response);
		} catch (IOException ioEx) {
			log.error("", ioEx);
		} catch (Exception ex) {
			log.error("", ex);
		}
		return result;
	}

	public static String doDelete(String url, Map<String, String> headers) {
		try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
			HttpDelete httpDelete = new HttpDelete(url);
			//设置请求headers
			setRequestHeaders(httpDelete, headers);
			HttpResponse response = httpClient.execute(httpDelete);
			return responseToStringEntity(response);
		} catch (IOException ioEx) {
			log.error("", ioEx);
		} catch (Exception ex) {
			log.error("", ex);
		}
		return StringUtils.EMPTY;
	}
	public static void main (String[] args) {
		String url = "https://43.254.44.13/v3/cluster";
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic dG9rZW4tcGxqd2Y6cms0czRoNG1xMjhkdmNkY2p0NWs3ODh3d2IyNXh3dHZ2endrOHZ3a3R6bDU2N2g5OW41d2Y5");
		headers.put("Content-Type", "application/json");

		String content = "{\"name\":\"test001\",\"rancherKubernetesEngineConfig\":{" +
				"\"addonJobTimeout\":30,\"ignoreDockerVersion\":true,\"sshAgentAuth\":false," +
				"\"type\":\"rancherKubernetesEngineConfig\",\"kubernetesVersion\":\"v1.13.5-rancher1-1\"," +
				"\"authentication\":{\"strategy\":\"x509\",\"type\":\"authnConfig\"}," +
				"\"network\":{\"plugin\":\"canal\",\"type\":\"networkConfig\",\"options\":{\"flannel_backend_type\":\"vxlan\"}}," +
				"\"ingress\":{\"provider\":\"nginx\",\"type\":\"ingressConfig\"}," +
				"\"monitoring\":{\"provider\":\"metrics-server\",\"type\":\"monitoringConfig\"}," +
				"\"services\":{\"type\":\"rkeConfigServices\"," +
				"\"kubeApi\":{\"podSecurityPolicy\":false,\"serviceNodePortRange\":\"30000-32767\",\"type\":\"kubeAPIService\"}," +
				"\"etcd\":{\"creation\":\"12h\",\"extraArgs\":{\"heartbeat-interval\":500,\"election-timeout\":5000}," +
				"\"retention\":\"72h\",\"snapshot\":true,\"type\":\"etcdService\"}}," +
				"\"cloudProvider\":{\"type\":\"cloudProvider\",\"cloudConfig\":{}}}}";

		try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
			HttpPost httpPost = new HttpPost(url);
			//设置请求headers
			setRequestHeaders(httpPost, headers);

			StringEntity entity = new StringEntity(content, Consts.UTF_8.name());
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);

			if (response != null) {
				HttpEntity resEntity = response.getEntity();

				//TODO response headers 是否需要返回
				Header[] responseHeaders = response.getAllHeaders();
				log.info("all headers info: ");
				Arrays.stream(responseHeaders)
						.forEach(it -> log.info("---- {}: {}", it.getName(), it.getValue()));
				log.info("----------------------------------------------------");
				if (resEntity != null) {
					String result = EntityUtils.toString(resEntity, Consts.UTF_8.name());
					System.out.println(result);
				}
			}
		} catch (Exception ex) {
			log.error("", ex);
		}
		log.info("----------------------------------------------------");
	}
}
