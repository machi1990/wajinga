package com.machi.wajinga.ws.resources;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mjinga.Mchambo;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;
import com.machi.wajinga.ws.services.mailer.BaruaPepeService;

@Path("michambo")
public class MchamboResource {

	@Inject
	private BaruaPepeService hudumaYaBaruaPepe;
	private MjingaDao mjingaDao;
	private WajiboostDao wajiboostDao;
	
	@Inject
	public MchamboResource(WajingaDao wajingaDao) {
		mjingaDao = wajingaDao.getMjingaDao();
		wajiboostDao = wajingaDao.getWajiboostDao();
	}

	/**
	 * @param info
	 * @return List<Michambo>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mchambo> michambo(@QueryParam("tarehe") Long tarehe, @QueryParam("elezo") String elezo) {
		return mjingaDao.tafutaMichambo(tarehe, elezo);
	}

	/**
	 * @param info
	 * @return List<Michambo>
	 */
	@GET
	@Path("/{mchambo-id: \\d+}/{mjinga-id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ongezaMchamboUlipo(@Context SecurityContext context, @PathParam("mchambo-id") Long mchamboId, @PathParam("mjinga-id") Long mjingaId) {
		Mjinga mjinga = mjingaDao.tafutaTaarifaZaMjinga(mjingaId);
		
		if (mjinga == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}
		
		Mchambo mchambo = mjingaDao.tafutaMchambo(mchamboId);
		
		if (mchambo == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}
		
		Boolean umeongezwa = mjingaDao.ongezaMchamboKwaMjinga(mjinga, mchambo);
		
		if (umeongezwa) {
			Mjinga mjinga_ = (Mjinga) context.getUserPrincipal();
			hudumaYaBaruaPepe.tuma(Arrays.asList(mjinga.getBaruaPepe()), null , String.format(wajiboostDao.tafutaUsanidi(Usanidi.MCHAMBO_MPYA), mjinga_.getJinaLaUkoo()), String.format("<div>%s</div>",mchambo.getMchambo()), null);
		}
		
		return umeongezwa ? Response.noContent().build() : Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	
	@GET
	@Path("{mchambo-id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Mchambo tafutaMchambo(@PathParam("mchambo-id") Long mchamboId ) {
		return mjingaDao.tafutaMchambo(mchamboId);
	}
	
	@POST
	@Path("{mjinga-id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ongezaMchamboKwa(@Context SecurityContext context, @PathParam("mjinga-id") Long mjingaId, Mchambo mchambo) {
		Mjinga mjinga = mjingaDao.tafutaTaarifaZaMjinga(mjingaId);
		
		if (mjinga == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}
		
		mchambo.setId(null);
		mchambo.setTarehe(DateTime.now());
		Boolean umeongezwa = mjingaDao.ongezaMchamboKwaMjinga(mjinga, mchambo);
		
		if (umeongezwa) {
			Mjinga mjinga_ = (Mjinga) context.getUserPrincipal();
			hudumaYaBaruaPepe.tuma(Arrays.asList(mjinga.getBaruaPepe()), null , String.format(wajiboostDao.tafutaUsanidi(Usanidi.MCHAMBO_MPYA), mjinga_.getJinaLaUkoo()), String.format("<div>%s</div>",mchambo.getMchambo()), null);
		}
		
		return umeongezwa ? Response.noContent().build() : Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	

	@POST
	@Path("mchambo-mpya")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ongezaMchamboMpya(Mchambo mchambo) {
		mchambo.setId(null);
		mchambo.setTarehe(DateTime.now());
		Boolean umeongezwa = mjingaDao.ongezaMchambo(mchambo);		
		return umeongezwa ? Response.noContent().build() : Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
}
