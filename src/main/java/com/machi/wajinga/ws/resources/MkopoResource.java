package com.machi.wajinga.ws.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.NotFoundException;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.Mjinga.Cheo;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.mkopo.MkopoDao;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;
import com.machi.wajinga.dao.mkopo.Rejesho;
import com.machi.wajinga.dao.mkopo.Mkopo;

@Path("mikopo")
public class MkopoResource {
	
	private MkopoDao mkopoDao;
	private MjingaDao mjingaDao;
	
	@Inject
	public MkopoResource(WajingaDao wajingaDao) {
		super();
		mkopoDao = wajingaDao.getMkopoDao();
		mjingaDao = wajingaDao.getMjingaDao();
	}

	@GET
	@RolesAllowed({"KATIBU", "MWEKAHAZINA", "MWENYEKITI"})
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mkopo> mikopo(@Context UriInfo info) {
		return mkopoDao.tafutaMikopo(info.getQueryParameters());
	}
	
	@GET
	@Path("mikopo-yangu")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mkopo> mikopoYangu(@Context UriInfo info, @Context SecurityContext context) {
		MultivaluedMap<String, String> filters = info.getQueryParameters();
		filters.putSingle("mjinga", context.getUserPrincipal().getName());
		return mkopoDao.tafutaMikopo(filters);
	}
	
	@GET
	@Path("mikopo-yangu/{mkopo-id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Mkopo mikopoYangu(@PathParam("mkopo-id") Long mkopoId, @Context SecurityContext context) {
		Mkopo mkopo = mkopoDao.tafutaMkopo(mkopoId);
		
		if (mkopoId == null) {
			throw new NotFoundException("Hakuna mkopo");
		}
		
		Mjinga mjinga = (Mjinga)context.getUserPrincipal();
		
		if (!mkopo.getMkopaji().getId().equals(mjinga.getId()) && mjinga.getCheo().equals(Cheo.MJUMBE)) {
			throw new BadRequestException("Mkopo sio la kwako");
		}
		
		return mkopo;
	}
	
	@GET
	@RolesAllowed({"KATIBU", "MWEKAHAZINA", "MWENYEKITI"})
	@Path("maombi")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OmbiLaMkopo> ombiLaMikopo(@Context UriInfo info) {
		return mkopoDao.tafutaOmbiLaMkopo(info.getQueryParameters());
	}
	
	@GET
	@Path("maombi-yangu")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OmbiLaMkopo> ombiLaMikopoYangu(@Context UriInfo info, @Context SecurityContext context) {
		MultivaluedMap<String, String> filters = info.getQueryParameters();
		filters.putSingle("mjinga", context.getUserPrincipal().getName());
		return mkopoDao.tafutaOmbiLaMkopo(filters);
	}
	
	@GET
	@Path("maombi-yangu/{ombi-id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public OmbiLaMkopo ombiLaMikopoYangu(@PathParam("ombi-id") Long ombiId, @Context SecurityContext context) {
		OmbiLaMkopo ombi = mkopoDao.tafutaOmbiLaMkopo(ombiId);
		
		if (ombi == null) {
			throw new NotFoundException("Hakuna ombi");
		}
		
		Mjinga mjinga = (Mjinga)context.getUserPrincipal();
		
		if (!ombi.getMjinga().getId().equals(mjinga.getId()) && mjinga.getCheo().equals(Cheo.MJUMBE)) {
			throw new BadRequestException("Ombi sio la kwako");
		}
		
		return ombi;
	}
	
	@POST
	@Path("omba")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ombiMikopo(OmbiLaMkopo ombiLaMkopo, @Context SecurityContext context) {
		
		if (ombiLaMkopo.getKiasi() == null || ombiLaMkopo.getKiasi() <= 0) {
			throw new BadRequestException("Kiasi kinatakiwa");
		}
		

		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		
		Boolean anaDeni = mkopoDao.anaDeni(mjinga);
		Boolean anaOmbi = mjingaDao.tafutaOmbiLaMkopo(mjinga.getId()).parallelStream().filter(element -> element.getJibu() == null).count() > 0;
		
		if (anaOmbi || anaDeni) {
			throw new BadRequestException("Una ombi ambalo halijajibiwa");
		}
		
		ombiLaMkopo.setJibu(null);
		ombiLaMkopo.setId(null);
		ombiLaMkopo.setTarehe(DateTime.now());
		ombiLaMkopo.setTareheYaMajibu(null);
		ombiLaMkopo.setMjibuji(null);
		ombiLaMkopo.setMjinga(mjinga);
		
		if (StringUtils.isEmpty(ombiLaMkopo.getMaelezo())) {
			ombiLaMkopo.setMaelezo("");
		}
		
		return Response.ok(mkopoDao.omba(ombiLaMkopo)).build();
	}
	
	
	@POST
	@Path("kubali-ombi/{ombi-id : \\d+}/{mwisho-rejesho: \\d+}")
	@RolesAllowed({"MWEKAHAZINA"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response kubaliOmbiMikopo(OmbiLaMkopo ombiLaMkopo, @Context SecurityContext context, @PathParam("ombi-id") Long ombiId, @PathParam("mwisho-rejesho") Long mwishoRejesho) {
		DateTime dateTime = new DateTime(mwishoRejesho);
		
		if (dateTime.isBefore(DateTime.now().plusMonths(2))) {
			throw new BadRequestException("Mwisho wa kurejesha mkopo watakiwa uwe mbele kwa miezi miwili kutoka sasa");
		}
		
		if (StringUtils.isEmpty(ombiLaMkopo.getMaelezoYaJibu())) {
			throw new BadRequestException("Maelezo ya majibu yanatakiwa");
		}
		
		OmbiLaMkopo ombi = mkopoDao.tafutaOmbiLaMkopo(ombiId);

		if (ombi == null ) {
			throw new NotFoundException("Hakuna ombi kama hilo");
		}
		
		if (ombi.getJibu() != null) {
			throw new BadRequestException("Ombi lina jibu tayari");
		}
		
		if (ombiLaMkopo.getKiasiKilichokubaliwa() == null ) {
			throw new BadRequestException("Kiasi kilichokubaliwa kinatakiwa");
		}
		
		if (ombiLaMkopo.getKiasiKilichokubaliwa() > ombi.getKiasi()) {
			throw new BadRequestException("Kiasi hakiwezi zidi ombi");
		}
		
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
	
		ombi.setMjibuji(mjinga);
		ombi.setMaelezoYaJibu(ombiLaMkopo.getMaelezoYaJibu());
		ombi.setKiasiKilichokubaliwa(ombiLaMkopo.getKiasiKilichokubaliwa());
	
		return Response.ok(mkopoDao.kubaliOmbi(ombi, dateTime)).build();
	}
	
	@POST
	@Path("kataa-ombi/{ombi-id : \\d+}")
	@RolesAllowed({"MWENYEKITI", "KATIBU"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response kataaOmbiMikopo(OmbiLaMkopo ombiLaMkopo, @Context SecurityContext context, @PathParam("ombi-id") Long ombiId) {
		if (StringUtils.isEmpty(ombiLaMkopo.getMaelezoYaJibu())) {
			throw new BadRequestException("Maelezo ya majibu yanatakiwa");
		}
		
		OmbiLaMkopo ombi = mkopoDao.tafutaOmbiLaMkopo(ombiId);

		if (ombi == null ) {
			throw new BadRequestException("Hakuna ombi kama hilo");
		}
		
		if (ombi.getJibu() != null) {
			throw new BadRequestException("Ombi lina jibu tayari");
		}
		
		
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		ombi.setMjibuji(mjinga);
		ombi.setMaelezoYaJibu(ombiLaMkopo.getMaelezoYaJibu());
	
		return Response.ok(mkopoDao.kataaOmbi(ombi)).build();
	}
	
	

	@POST
	@Path("rejesha/{mkopo-id : \\d+}")
	@RolesAllowed({"MWEKAHAZINA"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response rejeshaMikopo(Rejesho rejesho, @Context SecurityContext context, @PathParam("mkopo-id") Long mkopoid) {
		Mkopo mkopo = mkopoDao.tafutaMkopo(mkopoid);
		
		if (mkopo == null) {
			throw new NotFoundException("Hakuna mkopo kama huo");
		}
		
		if (rejesho.getKiasi() == null || rejesho.getKiasi() <= 0 ) {
			throw new BadRequestException("Kiasi cha rejesho kinatakiwa");
		}
		
		return Response.ok(mkopoDao.rejesha(mkopo, rejesho)).build();
	}
}
