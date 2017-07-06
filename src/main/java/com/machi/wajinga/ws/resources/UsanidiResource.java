package com.machi.wajinga.ws.resources;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;

@Path("usanidi")
@PermitAll
public class UsanidiResource {

	private WajiboostDao wajiboostDao;
	
	@Inject
	public UsanidiResource(WajingaDao wajingaDao) {
		super();
		wajiboostDao = wajingaDao.getWajiboostDao();
	}

	@GET
	@PermitAll
	@Path("{funguo}")
	@Produces(MediaType.APPLICATION_JSON)
	public String chukua(@PathParam("funguo") String funguo) {
		return wajiboostDao.tafutaUsanidi(funguo);
	}
	
	@POST
	@RolesAllowed({"KATIBU", "MWENYEKITI"})
	@Path("{funguo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response weka(Usanidi usanidi) {
		return Response.ok(wajiboostDao.tunzaUsanidi(usanidi)).build();
	}
}
