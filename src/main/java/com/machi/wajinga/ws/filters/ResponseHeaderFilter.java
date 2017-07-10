package com.machi.wajinga.ws.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

		final Long muda = requestContext.getProperty("timestamp") != null ? System.currentTimeMillis() - (Long) requestContext.getProperty("timestamp") : 0;
		final String method = requestContext.getRequest().getMethod();
		final String uri = requestContext.getUriInfo().getRequestUri().toString();
		final String mjinga = requestContext.getProperty("mjinga") != null
				? requestContext.getProperty("mjinga").toString()
				: " - ";
		final StringBuilder builder = new StringBuilder();

		builder.append(mjinga).append(" ").append(method).append(" ").append(uri).append(" - ").append(muda)
				.append("ms");

		Logger.getGlobal().log(Level.INFO, builder.toString());
	}

}
