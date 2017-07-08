package com.machi.wajinga.ws.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

public class EntranceFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		requestContext.setProperty("acceptedEncoding",
				requestContext.getHeaders().containsKey("Accept-Encoding")
						? requestContext.getHeaderString("Accept-Encoding")
						: "");
		requestContext.setProperty("timestamp", System.currentTimeMillis());
	}

}
