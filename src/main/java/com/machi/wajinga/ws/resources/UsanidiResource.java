package com.machi.wajinga.ws.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("usanidi")
public class UsanidiResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get() {
		return "usainidi";
	}
}
