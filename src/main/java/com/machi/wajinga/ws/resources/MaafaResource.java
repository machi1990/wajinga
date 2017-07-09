package com.machi.wajinga.ws.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.maafa.Maafa.Zawadi;
import com.machi.wajinga.dao.maafa.MaafaDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;
import com.machi.wajinga.ws.services.mailer.BaruaPepeService;

@Path("maafa")
public class MaafaResource {

	@Context
	private SecurityContext context;

	@Inject
	private BaruaPepeService hudumaYaBaruaPepe;

	private MaafaDao maafaDao;
	private MjingaDao mjingaDao;
	private WajiboostDao wajiboostDao;

	@Inject
	public MaafaResource(WajingaDao wajingaDao) {
		super();
		maafaDao = wajingaDao.getMaafaDao();
		mjingaDao = wajingaDao.getMjingaDao();
		wajiboostDao = wajingaDao.getWajiboostDao();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Maafa> maafa(@Context UriInfo info) {
		return maafaDao.tafutaMaafa(info.getQueryParameters());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{afa-id : \\d+}")
	public Maafa afa(@PathParam("afa-id") Long afaId) {
		return maafaDao.tafutaAfa(afaId);
	}

	@DELETE
	@RolesAllowed({ "KATIBU", "MWEKAHAZINA", "MWENYEKITI" })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{afa-id: \\d+}/{mjinga-id : \\d+}")
	public Response futa(@PathParam("afa-id") Long afaId, @PathParam("mjinga-id") Long mjingaId) {
		return Response.ok(maafaDao.futa(afaId, mjingaId)).build();
	}

	@POST
	@RolesAllowed({ "KATIBU", "MWENYEKITI", "MWEKAHAZINA" })
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("ongeza/{mjinga-id: \\d+}")
	public Response ongezaAfa(@PathParam("mjinga-id") Long mjingaId, Maafa maafa) {
		if (maafa.getKiasi() == null) {
			throw new BadRequestException("Kiasi cha maafa kinatakiwa");
		}

		if (maafa.getFplTimuId() == null) {
			throw new BadRequestException("FPL app timu id inatakiwa");
		}

		if (maafa.getTimu() == null) {
			throw new BadRequestException("Jina la timu linatakiwa");
		}

		Mjinga mjinga = mjingaDao.tafutaTaarifaZaMjinga(mjingaId);

		if (mjinga == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}

		maafa.setZawadi(new ArrayList<Zawadi>());
		String msimu = wajiboostDao.tafutaUsanidi(Usanidi.MSIMU);
		maafa.setMsimu(msimu);
		maafa.setId(mjingaId);
		maafa.setNafasi(null);
		maafa.setMjinga(mjinga);

		if (maafaDao.wekaAfa(maafa)) {
			Mjinga principal = (Mjinga) context.getUserPrincipal();
			hudumaYaBaruaPepe.tuma(Arrays.asList(mjinga.getBaruaPepe()), Arrays.asList(principal.getBaruaPepe()),
					wajiboostDao.tafutaUsanidi(Usanidi.MAAFA),
					String.format(wajiboostDao.tafutaUsanidi(Usanidi.MAAFA_MESEJI), mjinga.getJinaLaUkoo(), msimu,
							DateTimeFormat.forPattern("yyyy-mm-dd").print(DateTime.now()), principal.getJinaLaUkoo()),
					null);
			return Response.noContent().build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("Ombi limegoma").build();
		}
	}
}
