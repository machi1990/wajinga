package com.machi.wajinga.ws.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class ResponseHeaderFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		if (requestContext.getProperty("AuthorizationSid") != null) {
			responseContext.getHeaders().add("AuthorizationSid", requestContext.getProperty("AuthorizationSid"));
		}
		
		responseContext.getHeaders().add("Cache-Control", "no-cache");
	}

}
