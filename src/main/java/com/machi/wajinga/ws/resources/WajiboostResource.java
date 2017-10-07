package com.machi.wajinga.ws.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.wajiboost.Lengo;
import com.machi.wajinga.dao.wajiboost.Lengo.Hali;
import com.machi.wajinga.dao.wajiboost.Lengo.Historia;
import com.machi.wajinga.dao.wajiboost.Tukio;
import com.machi.wajinga.dao.wajiboost.Tukio.Oni;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;


@Path("wajiboost")
public class WajiboostResource {
	
	private WajiboostDao wajiboostDao;
	
	@Inject
	public WajiboostResource(WajingaDao wajingaDao) {
		super();
		wajiboostDao = wajingaDao.getWajiboostDao();
	}

	@GET
	@PermitAll
	@Path("katiba.pdf")
	public Response katibaPdf() {
		return output(Usanidi.KATIKA_PDF);
	}
	
	@GET
	@Path("katiba.docx")
	public Response katibaWord() {
		return output(Usanidi.KATIBA_WORD);
	}
	
	private Response output(String katibaFormat) {
		final byte[] content = wajiboostDao.tafutaUsanidi(katibaFormat).getBytes();
		final StreamingOutput output = new StreamingOutput() {	
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {	
				output.write(content);
			}
		};
		
		return Response.ok(output).header("Content-Disposition", "attachment; filename=" + katibaFormat).build();
	}

	@GET
	@Path("matukio")
	public List<Tukio> matukio() {
		return wajiboostDao.matukio();
	}
	
	@POST
    @Path("tukio")
	public Response tukio(Tukio tukio, @Context SecurityContext context) {
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		
		if (tukio.getAina() == null) {
			throw new BadRequestException("Aina ya tukio yatakiwa");
		}
		
		if (StringUtils.isEmpty(tukio.getKichwa())) {
			throw new BadRequestException("Kichwa cha tukio kinatakiwa");
		}
		
		if (StringUtils.isEmpty(tukio.getMaelezoYaTukio())) {
			throw new BadRequestException("Maelezo ya tukio yanatakiwa");
		}
		
		if (StringUtils.isEmpty(tukio.getMahali())) {
			throw new BadRequestException("Mahali pa tukio panatakiwa");
		}
		
		if (tukio.getTarehe() == null) {
			throw new BadRequestException("Tarehe ya tukio yatakiwa");
		}
		
		tukio.setId(null);
		tukio.setMuandaaji(mjinga);
		tukio.setTareheYaKutengenezwa(DateTime.now());
		tukio.setMaoni(new ArrayList<Oni>());
		tukio.setWashiriki(new ArrayList<Mjinga>());
		tukio.setMaelezoBaadaYaTukio("");
		
		return Response.ok(wajiboostDao.wekaTukio(tukio)).build();
	}
	
	@GET
	@Path("malengo")
	public List<Lengo> malengo () {
		return wajiboostDao.malengo();
	}
	
	@POST
    @Path("lengo")
	public Response lengo(Lengo lengo, @Context SecurityContext context) {
		if (lengo.getHaliYaSasa() == null) {
			throw new BadRequestException("Maelezo yanatakiwa");
		}
		
		Set<Historia> historias = new HashSet<Historia>();
		Historia historia = new Historia(Hali.PENDEKEZO,lengo.getHaliYaSasa().maelezo, DateTime.now(), context.getUserPrincipal().getName());
		historias.add(historia);
		lengo.setHistoria(historias);
		return Response.ok(wajiboostDao.wekaLengo(lengo)).build();
	}
	
	@POST
    @Path("lengo/{lengo-id: \\d+}")
	public Response badiliHistoriaYaLengo(Historia historia, @Context SecurityContext context, @PathParam("lengo-id") Long lengoId) {
		if (historia.hali == null) {
			throw new BadRequestException("Hali inatakiwa");
		}
		
		if (historia.maelezo == null) {
			throw new BadRequestException("Maalezo ya hali yanatakiwa");
		}
		
		Lengo lengo = wajiboostDao.tafutaLengo(lengoId);

		if (lengo == null) {
			throw new BadRequestException("Hakuna lengo kama hilo");
		}
		
		historia.mwekaHistoria = context.getUserPrincipal().getName();
		lengo.getHistoria().add(historia);
		return Response.ok(wajiboostDao.wekaLengo(lengo)).build();
	}
	
	@GET
    @Path("lengo/{lengo-id: \\d+}")
	public Lengo lengo(@PathParam("lengo-id") Long lengoId) {
		Lengo lengo = wajiboostDao.tafutaLengo(lengoId);
		
		if (lengo == null) {
			throw new NotFoundException("Hakuna lengo kama hilo");
		}
		
		return lengo;
	}
	
	@DELETE
	@RolesAllowed({"MWENYEKITI", "KATIBU"})
    @Path("lengo/{lengo-id: \\d+}")
	public Response futaLengo(@PathParam("lengo-id") Long lengoId) {
		Lengo lengo = wajiboostDao.tafutaLengo(lengoId);
		
		if (lengo == null) {
			throw new NotFoundException("Hakuna lengo kama hilo");
		}
		
		return Response.ok(wajiboostDao.futaLengo(lengo)).build();
	}
	
	@GET
    @Path("tukio/{tukio-id: \\d+}")
	public Tukio tukio(@PathParam("tukio-id") Long lengoId) {
		Tukio tukio = wajiboostDao.tafutaTukio(lengoId);
		
		if (tukio == null) {
			throw new NotFoundException("Hakuna tukio kama hulo");
		}
		
		return tukio;
	}
	
	@PATCH
    @Path("shiriki-tukio/{tukio-id: \\d+}")
	public Response shirikiTukio(@PathParam("tukio-id") Long lengoId, @Context SecurityContext context) {
		Tukio tukio = wajiboostDao.tafutaTukio(lengoId);
		
		if (tukio == null) {
			throw new NotFoundException("Hakuna tukio kama hulo");
		}
		
		if (tukio.isLimefanyika()) {
			throw new BadRequestException("Tukio limeishafantika");
		}
		
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return Response.ok(wajiboostDao.shirikiTukio(tukio, mjinga)).build();
	}
	
	
	@POST
    @Path("tukio-badili-elezo/{tukio-id: \\d+}")
	public Response maelezoYaTukio(@PathParam("tukio-id") Long lengoId, @Context SecurityContext context, String tukioElezo) {
		if (StringUtils.isEmpty(tukioElezo)) {
			throw new BadRequestException("Maelezo yanatakiwa");
		}
		
		Tukio tukio = wajiboostDao.tafutaTukio(lengoId);
		
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		
		if (tukio.getMuandaaji().getId().equals(mjinga.getId())) {
			throw new BadRequestException("Wewe sio muaandaji wa tukio");
		}
		
		if (!tukio.isLimefanyika()) {
			throw new BadRequestException("Tukio halijaisha");
		}
		
		tukio.setMaelezoBaadaYaTukio(tukioElezo);
		
		return Response.ok(wajiboostDao.maelezoYaTukio(tukio)).build();
	}
	
	@POST
    @Path("tukio-badili-taarifa/{tukio-id: \\d+}")
	public Response badiliTaarifaYaTukio(@PathParam("tukio-id") Long lengoId, @Context SecurityContext context, Tukio tukioJipya) {
		if (StringUtils.isEmpty(tukioJipya.getMahali())) {
			throw new BadRequestException("Mahali panatakiwa");
		}
		
		if (StringUtils.isEmpty(tukioJipya.getMaelezoYaTukio())) {
			throw new BadRequestException("Mahali panatakiwa");
		}
		
		
		if (tukioJipya.getTarehe() == null) {
			throw new BadRequestException("Tarehe inatakiwa");
		}
		
		Tukio tukio = wajiboostDao.tafutaTukio(lengoId);
		
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		
		if (tukio.getMuandaaji().getId().equals(mjinga.getId())) {
			throw new BadRequestException("Wewe sio muaandaji wa tukio");
		}
		
		tukio.setMahali(tukioJipya.getMahali());
		tukio.setTarehe(tukioJipya.getTarehe());
		tukio.setMaelezoYaTukio(tukioJipya.getMaelezoYaTukio());
		return Response.ok(wajiboostDao.badiliTaarifa(tukio)).build();
	}
	
	@POST
    @Path("tukio-toa-oni/{tukio-id: \\d+}")
	public Response toaOniTukio(@PathParam("tukio-id") Long lengoId, @Context SecurityContext context, Oni oni) {
		if (StringUtils.isEmpty(oni.maelezo)) {
			throw new BadRequestException("Maoni yanatakiwa");
		}
		
		Tukio tukio = wajiboostDao.tafutaTukio(lengoId);
		
		if (tukio == null) {
			throw new NotFoundException("Hakuna tukio kama hulo");
		}
		
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		
		if (!tukio.isMshiriki(mjinga)) {
			throw new ForbiddenException("Huwezi toa oni ka si mshiriki wa tukio");		
		}
	
		oni.mtoaOni = mjinga.getJina();
		oni.tarehe = DateTime.now();
		return Response.ok(wajiboostDao.toaOniTukio(tukio, oni)).build();
	}
	
	@DELETE
    @Path("toka-tukio/{tukio-id: \\d+}")
	public Response tokaKwenyeTukio(@PathParam("tukio-id") Long lengoId, @Context SecurityContext context) {
		Tukio tukio = wajiboostDao.tafutaTukio(lengoId);
		
		if (tukio == null) {
			throw new NotFoundException("Hakuna tukio kama hulo");
		}
		
		if (tukio.isLimefanyika()) {
			throw new BadRequestException("Tukio limeishafantika");
		}
		
		Mjinga mjinga = (Mjinga) context.getUserPrincipal();
		return Response.ok(wajiboostDao.tokaKwenyeTukio(tukio, mjinga)).build();
	}

	@DELETE
	@RolesAllowed({"MWENYEKITI", "KATIBA"})
    @Path("tukio/{tukio-id: \\d+}")
	public Response futaTukio(@PathParam("tukio-id") Long lengoId) {
		Tukio tukio = wajiboostDao.tafutaTukio(lengoId);
		
		if (tukio == null) {
			throw new NotFoundException("Hakuna tukio kama hulo");
		}
		
		if (tukio.isLimefanyika()) {
			throw new BadRequestException("Tukio limeishafantika");
		}
		
		return Response.ok(wajiboostDao.futaTukio(tukio)).build();
	}
}
