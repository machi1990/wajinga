package com.machi.wajinga.ws.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mkopo.MkopoDao;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;
import com.machi.wajinga.dao.mkopo.Mkopo;

@Path("mkopo")
public class MkopoResource {
	
	private MkopoDao mkopoDao;
	
	@Inject
	public MkopoResource(WajingaDao wajingaDao) {
		super();
		mkopoDao = wajingaDao.getMkopoDao();
	}

	@GET
	@RolesAllowed({"KATIBU", "MWEKAHAZINA", "MWENYEKITI"})
	@Produces(MediaType.TEXT_PLAIN)
	public List<Mkopo> mikopo(@Context UriInfo info) {
		return mkopoDao.tafutaMikopo(info.getQueryParameters());
	}
	
	@GET
	@Path("mikopo-yangu")
	@Produces(MediaType.TEXT_PLAIN)
	public List<Mkopo> mikopoYangu(@Context UriInfo info, @Context SecurityContext context) {
		MultivaluedMap<String, String> filters = info.getQueryParameters();
		filters.putSingle("mjinga", context.getUserPrincipal().getName());
		return mkopoDao.tafutaMikopo(filters);
	}
	
	
	@GET
	@RolesAllowed({"KATIBU", "MWEKAHAZINA", "MWENYEKITI"})
	@Path("maombi")
	@Produces(MediaType.TEXT_PLAIN)
	public List<OmbiLaMkopo> ombiLaMikopo(@Context UriInfo info) {
		return mkopoDao.tafutaOmbiLaMkopo(info.getQueryParameters());
	}
	
	@GET
	@Path("maombi-yangu")
	@Produces(MediaType.TEXT_PLAIN)
	public List<OmbiLaMkopo> ombiLaMikopoYangu(@Context UriInfo info, @Context SecurityContext context) {
		MultivaluedMap<String, String> filters = info.getQueryParameters();
		filters.putSingle("mjinga", context.getUserPrincipal().getName());
		return mkopoDao.tafutaOmbiLaMkopo(filters);
	}
}
