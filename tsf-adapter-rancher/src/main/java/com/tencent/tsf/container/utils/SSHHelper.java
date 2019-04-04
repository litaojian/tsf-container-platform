/*
 * Copyright (c) 2018-2019. All rights reserved.
 * 注意：本内容仅限于项目内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.tencent.tsf.container.utils;


import com.jcraft.jsch.*;
import com.tencent.tsf.container.dto.ClusterVMDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Ethan Pau
 * @version Version 1.0
 * @title SSHHelper
 * @date 2019/4/3 11:10
 * @description TODO
 */
public class SSHHelper {

	private static final Logger LOG = LoggerFactory.getLogger(SSHHelper.class);
	private static final Logger JSCH_LOG = LoggerFactory.getLogger(com.jcraft.jsch.JSch.class);

	public static class JschLogger implements com.jcraft.jsch.Logger {
		public boolean isEnabled(int level) {
			return true;
		}

		public void log(int level, String message) {
			switch (level) {
				case DEBUG:
					JSCH_LOG.debug(message);
					break;
				case INFO:
					// Jsch 大量打了大量 INFO 级的 debug 信息
					JSCH_LOG.debug(message);
					break;
				case WARN:
					JSCH_LOG.warn(message);
					break;
				case ERROR:
					JSCH_LOG.error(message);
					break;
				case FATAL:
					JSCH_LOG.error(message);
					break;
			}
		}
	}

	static {
		// WARNING: 如果有别的地方用 JSch，也会受到影响
		JSch.setLogger(new JschLogger());
	}

	/**
	 * 如果提供的用户名有误，对端的 SSH server 会有一个 FAIL_DELAY，通常会是 2-3s；这导致这个函数会有 1s 延时才返回，定义在下面的 setTimeout 函数。
	 * <p>
	 * <b>注意：调用方必须负责将 session.disconnect()。</b>
	 */
	public static Session getConnectedSession(ClusterVMDto sshHostInfo) {
		JSch jsch = new JSch();

		Session session;
		try {
			session = jsch.getSession(sshHostInfo.getUsername(), sshHostInfo.getIp(), sshHostInfo.getPort());
		} catch (JSchException e) {
			LOG.error("jsch.getSession failed", e);
			throw new RuntimeException("登录host 或 username 无效");
		}

		session.setPassword(sshHostInfo.getPassword());
		// 不要检查 host key，因为我们不会提前把要连接主机的 host key 加进来
		session.setConfig("StrictHostKeyChecking", "no");
		// 不要重试了，失败就失败
		session.setConfig("MaxAuthTries", "1");
		// 仅使用密码去尝试登陆；默认会有四种方式（参考 JSch 类的源码），验证起来很慢
		session.setConfig("PreferredAuthentications", "password");

		// Ciphers, kex algorithm, signatures 参数选择，参考了 Mozilla 的 OpenSSH guideline：
		// https://infosec.mozilla.org/guidelines/openssh.html
		// 选取的是 Modern OpenSSH 6.7+，CentOS 7 带的 OpenSSH 都符合这个要求。

		// 原本想要加快速度，但是发现没什么卵用
		// session.setConfig("CheckCiphers", "aes256-ctr");
		// session.setConfig("CheckKexes", "ecdh-sha2-nistp521");
		// session.setConfig("CheckSignatures", "ecdsa-sha2-nistp521");

		try {
			// Timeout in ms
			session.setTimeout(1000);
		} catch (JSchException e) {
			LOG.error("jsch session.setTimeout failed", e);
			throw new RuntimeException("登录失败");
		}

		try {
			session.connect();
		} catch (JSchException e) {
			LOG.error("jsch connect failed", e);
			if ("Auth fail".equals(e.getMessage())) {
				LOG.debug("SSH Auth failed, {}", sshHostInfo);
				throw new RuntimeException("SSH 认证失败，请确认用户名密码是否正确！");
			} else {
				throw new RuntimeException("SSH 连接到目标机器失败！");
			}
		}

		return session;
	}

	public static ExecResult execCommand(String command, ClusterVMDto sshHostInfo) throws RuntimeException {
		Session session = null;
		Channel channel = null;
		try {
			session = SSHHelper.getConnectedSession(sshHostInfo);

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			ByteArrayOutputStream stderr = new ByteArrayOutputStream();
			channel.setInputStream(null);
			channel.setOutputStream(stdout);
			((ChannelExec) channel).setErrStream(stderr);

			// in milli-seconds (1E-3 second)
			channel.connect(1000);
			while (!channel.isClosed()) {
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					// safely ignored
				}
			}

			String stdoutString, stderrString;
			try {
				stdoutString = stdout.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				stdoutString = stdout.toString();
			}
			try {
				stderrString = stderr.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				stderrString = stderr.toString();
			}
			LOG.debug("stdoutString: {}", stdoutString);
			LOG.debug("stderrString: {}", stderrString);

			return new ExecResult(stdoutString, stderrString, channel.getExitStatus());
		} catch (JSchException e) {
			LOG.error("SSH operation failed", e);
			throw new RuntimeException("SSH 操作失败！");
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
	}

}
