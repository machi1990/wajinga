package com.machi.wajinga.ws.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mjinga.Mchambo;
import com.machi.wajinga.ws.services.mailer.BaruaPepeService;

@Path("michambo")
public class MchamboResource {

	@Inject
	private BaruaPepeService hudumaYaBaruaPepe;
	@Inject
	private WajingaDao dao;

	public MchamboResource() {
		super();
	}

	/**
	 * TODO
	 * 
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mchambo> michambo(@Context UriInfo info) {
		return new ArrayList<Mchambo>();
	}

}
