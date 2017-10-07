package com.machi.wajinga.ws.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.util.Base64;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.malipo.MalipoYaMwezi;
import com.machi.wajinga.dao.mjinga.Mchambo;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.Mjinga.Cheo;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.mkopo.Mkopo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;
import com.machi.wajinga.ws.services.mailer.BaruaPepeService;
import com.machi.wajinga.ws.services.mailer.EmailAttachment;

@Path("mjinga")
public class MjingaResource {
	private final static DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-mm-dd hh:mm");
	
	@Context
	private SecurityContext context;
	
	private MjingaDao dao;
	private WajiboostDao wajiboostDao;
	
	@Inject
	private BaruaPepeService hudumaYaBaruaPepe;
	
	@Inject
	public MjingaResource(WajingaDao wajingaDao) {
		dao = wajingaDao.getMjingaDao();
		wajiboostDao = wajingaDao.getWajiboostDao();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Mjinga taarifaZaMjinga() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaTaarifaZaMjinga(mjinga.getId());
	}

	@GET
	@Path("mikopo-yote")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mkopo> taarifaZaMikopoYoteYaMjinga() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaMikopoYaMjinga(mjinga.getId());
	}

	@GET
	@Path("mikopo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mkopo> taarifaZaMikopoYaMjinga() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaDeni(mjinga.getId());
	}

	@GET
	@Path("mikopo-iliyolipwa")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mkopo> taarifaZaMalipoYaMikopoYaMjinga() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaDeni(mjinga.getId());
	}

	@GET
	@Path("maombi-ya-mkopo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OmbiLaMkopo> taarifaZaMaombiYaMikopo() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaOmbiLaMkopo(mjinga.getId());
	}

	@GET
	@Path("maombi-ya-mkopo-yaliyokubaliwa")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OmbiLaMkopo> taarifaZaMaombiYaMikopoYaliyokubaliwa() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaOmbiLaMkopoLililokubaliwa(mjinga.getId());
	}

	@GET
	@Path("maombi-ya-mkopo-yaliyokataliwa")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OmbiLaMkopo> taarifaZaMaombiYaMikopoYaliyokataliwa() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaOmbiLaMkopoYaliyokataliwa(mjinga.getId());
	}

	@GET
	@Path("michambo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mchambo> michambo() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaMichambo(mjinga.getId());
	}

	@GET
	@Path("maafa")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Maafa> maafa() {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return dao.tafutaMaafa(mjinga.getId()).parallelStream().sorted().collect(Collectors.toList());
	}

	@POST
	@RolesAllowed({ "KATIBU", "MWENYEKITI" })
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("ongeza-mjinga")
	public Response ongezaMjinga(Mjinga mjinga) {
		if (mjinga == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}

		mjinga.setId(null);

		/**
		 * TODO use bean validation API
		 */

		if (StringUtils.isEmpty(mjinga.getJina()) || StringUtils.isEmpty(mjinga.getBaruaPepe())) {
			throw new BadRequestException("Jina au barua pepe ya mjinga yatakiwa");
		}

		Mjinga tafutaMjingaKwaJina = dao.tafutaMjingaKwaJina(mjinga.getJina());
		Mjinga mjinga_ = tafutaMjingaKwaJina != null ? tafutaMjingaKwaJina
				: dao.tafutaMjingaKwaBaruaPepe(mjinga.getBaruaPepe());

		if (mjinga_ != null) {
			throw new BadRequestException("Jina au barua pepe ya mjinga imetumika");
		}

		String nywiraMpya = Base64.encodeAsString(UUID.randomUUID().toString().substring(0, 10));
		mjinga.wekaNywira(nywiraMpya);
		mjinga.setTareheYaKuanzaUjinga(DateTime.now());
		mjinga.setMaafa(new ArrayList<Maafa>());
		mjinga.setOmbiMkopo(new ArrayList<OmbiLaMkopo>());
		mjinga.setMikopo(new ArrayList<Mkopo>());
		mjinga.setMichambo(new ArrayList<Mchambo>());
		mjinga.setMalipo(new ArrayList<MalipoYaMwezi>());
		mjinga.setCheo(Cheo.MJUMBE);
		dao.tunzaMjinga(mjinga);
		
		tumaBaruaKwaMjingaMpya(mjinga, nywiraMpya);

		return Response.ok().entity("Mjinga kaongezwa").build();
	}

	private void tumaBaruaKwaMjingaMpya(Mjinga mjinga, String nywiraMpya) {
		hudumaYaBaruaPepe.tuma(Arrays.asList(mjinga.getBaruaPepe()), null,
				wajiboostDao.tafutaUsanidi(Usanidi.AKAUNTI_KUFUNGULIWA),
				String.format(wajiboostDao.tafutaUsanidi(Usanidi.AKAUNTI_KUFUNGULIWA_MESEJI), mjinga.getJina(),
						format.print(DateTime.now()), nywiraMpya),
				Arrays.asList(new EmailAttachment(Usanidi.KATIKA_PDF, wajiboostDao.tafutaUsanidi(Usanidi.KATIKA_PDF).getBytes())));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "KATIBU", "MWENYEKITI" })
	@Path("badili-cheo-mjinga")
	public Response badiliCheoChaMjinga(Mjinga mjinga) {
		if (mjinga == null || mjinga.getId() == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}

		if (mjinga.getCheo() == null) {
			throw new BadRequestException("Cheo kipya kinatakiwa");
		}

		dao.badiliCheo(mjinga);

		return Response.ok().entity("Cheo cha mjinga kimebadilishwa").build();
	}

	@GET
	@RolesAllowed({ "KATIBU", "MWENYEKITI" })
	@Path("ban-mjinga/{jina}")
	public Response banChaMjinga(@PathParam("jina") String jinaLaMjinga) {
		Mjinga mjinga = dao.tafutaMjingaKwaJina(jinaLaMjinga);

		if (mjinga == null || mjinga.getId() == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}

		mjinga.setCheo(Cheo.PASTOR);
		dao.badiliCheo(mjinga);

		String subject = wajiboostDao.tafutaUsanidi(Usanidi.AKAUNTI_KUFUNGWA);
		String meseji = wajiboostDao.tafutaUsanidi(Usanidi.AKAUNTI_KUFUNGWA_MESEJI);
		hudumaYaBaruaPepe.tuma(Arrays.asList(mjinga.getBaruaPepe()), null, subject,
				String.format(meseji, mjinga.getJina(), format.print(DateTime.now())), null);

		return Response.ok().entity("Agu Aguu Aguu Aguu chaliiii").build();
	}

	@GET
	@RolesAllowed({ "KATIBU", "MWENYEKITI" })
	@Path("ondoa-ban-ya-mjinga/{jina}")
	public Response ondoBanYaMjinga(@PathParam("jina") String jinaLaMjinga) {
		Mjinga mjinga = dao.tafutaMjingaKwaJina(jinaLaMjinga);

		if (mjinga == null || mjinga.getId() == null) {
			throw new BadRequestException("Mjinga anatakiwa");
		}

		mjinga.setCheo(Cheo.MJUMBE);
		dao.badiliCheo(mjinga);

		String nywiraMpya = Base64.encodeAsString(UUID.randomUUID().toString().substring(0, 10));
		mjinga.wekaNywira(nywiraMpya);
		dao.badiliNywira(mjinga, nywiraMpya);

		hudumaYaBaruaPepe.tuma(Arrays.asList(mjinga.getBaruaPepe()), null,
				wajiboostDao.tafutaUsanidi(Usanidi.AKAUNTI_KUFUNGULIWA),
				String.format(wajiboostDao.tafutaUsanidi(Usanidi.AKAUNTI_KUFUNGULIWA_MESEJI), mjinga.getJina(),
						format.print(DateTime.now()), nywiraMpya),
				null);

		return Response.ok().entity("Ombi limekubaliwa").build();
	}
}
