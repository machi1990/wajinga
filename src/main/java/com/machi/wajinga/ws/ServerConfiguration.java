package com.machi.wajinga.ws;

import java.util.Properties;

import org.jvnet.hk2.annotations.Service;


@Service
public class ServerConfiguration {
	private String scheme = "https";
	public static ServerConfiguration config;
	
	private ServerConfiguration(Properties props) {
		this.scheme = props.getProperty("scheme", scheme);
	}
	
	public String getScheme() {
		return scheme;
	}
	
	public Boolean isSecure() {
		return !scheme.equals("http");
	}
	
	public static void register(Properties props) {
		config = new ServerConfiguration(props);
	}
}
