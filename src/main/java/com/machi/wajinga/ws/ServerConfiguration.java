package com.machi.wajinga.ws;

import java.util.Properties;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

@Service
public class ServerConfiguration {
	private Properties props;

	@Inject
	public ServerConfiguration(Properties props) {
		this.props = props;
	}

	public String getScheme() {
		return props.getProperty("scheme", "http");
	}

	public Boolean isSecure() {
		return !getScheme().equals("http");
	}

	public String getPersistenceUnit() {
		return props.getProperty("persistence", "Wajinga-Dev");
	}
}
