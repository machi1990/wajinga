package com.machi.wajinga.ws.resources;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.security.PermitAll;
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

import org.glassfish.jersey.internal.util.Base64;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;
import com.machi.wajinga.ws.services.mailer.BaruaPepeService;


/**
 * Root resource (exposed at "home" path)
 */
@Path("ingia")
public class HomeResource {

	@Context private SecurityContext context;
	@Inject private BaruaPepeService hudumaYaBaruaPepe;
	private MjingaDao mjingaDao;
	private WajiboostDao wajiboostDao;
	
	@Inject
	public HomeResource(WajingaDao wajingaDao) {
		super();
		mjingaDao  = wajingaDao.getMjingaDao();
		wajiboostDao = wajingaDao.getWajiboostDao();
	}

	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Mjinga ingia() {
    		return mjingaDao.tafutaMjingaKwaJina(context.getUserPrincipal().getName());
    }
    
    @POST
    @Path("badili-nywira")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response badiliNywira(NywiraKontena kontena) {
    		Mjinga mjinga = mjingaDao.tafutaMjingaKwaJina(context.getUserPrincipal().getName());
    		
    		if (!mjinga.getBaruaPepe().equals(kontena.baruaPepe)) {
    			throw new BadRequestException("Barua pepe imekosewa");
    		}
    		
    		if (!mjinga.nywiraSahihi(kontena.nywiraYaZamani)) {
    			throw new BadRequestException("Nywira ya zamani si sahihi");
    		}
    	
    		if (kontena.nywiraMpya == null || "".equals(kontena.nywiraMpya) || kontena.nywiraMpya.matches("\\s+")) {
    			throw new BadRequestException("Nywira mpya haiwezi kuwa tupu au kuwa na herufi zisizo sahihi");
    		}
    		
    		mjingaDao.badiliNywira(mjinga, kontena.nywiraMpya);
    		
    		return Response.noContent().build();
    }
    
    
    @GET
    @PermitAll
    @Path("tengeneza-nywira-mpya/{barua-pepe}")
    public Response tengenezaNywiraMpya(@PathParam("barua-pepe") String baruaPepe) {
    		Mjinga mjinga = mjingaDao.tafutaMjingaKwaBaruaPepe(baruaPepe);
    		
    		if (mjinga == null) {
    			throw new BadRequestException("Barua pepe haipo");
    		}
    		
    		
    		/**
    		 * TODO
    		 */
    		String tokeni = UUID.randomUUID().toString();
    		
    		mjingaDao.wekaNywiraTokeni(mjinga.getId(), tokeni);
    		
    		List<String> emails = Arrays.asList(baruaPepe);
    		
    		String linki = wajiboostDao.tafutaUsanidi(Usanidi.SEVA) + "/mjinga/tengeneza-nywira-mpya/" + tokeni + "/" +baruaPepe+"/";
    		hudumaYaBaruaPepe.tuma(emails, null, wajiboostDao.tafutaUsanidi(Usanidi.BADILI_NYWIRA), String.format(Usanidi.BADILI_NYWIRA_MESEJI, linki), null);
    		return Response.noContent().build();
    }
    
    @GET
    @PermitAll
    @Path("tengeneza-nywira-mpya/{tokeni}/{barua-pepe}")
    public Response tengenezaNywiraMpya(@PathParam("tokeni") String tokeni, @PathParam("barua-pepe") String baruaPepe) {
    		Mjinga mjinga = mjingaDao.tafutaMjingaKwaBaruaPepe(baruaPepe);
    		
    		if (mjinga == null) {
    			throw new BadRequestException("Barua pepe haipo");
    		}
    		
    		if (!tokeni.equals(mjinga.getNywiraTokeni())) {
    			throw new BadRequestException("Hakuna ombi ka hilo");
    		}
    		
    		String nywiraMpya = Base64.encodeAsString(UUID.randomUUID().toString().substring(0, 10));
    		
    		mjingaDao.badiliNywira(mjinga, nywiraMpya);
    		List<String> emails = Arrays.asList(baruaPepe);
    		String linki = wajiboostDao.tafutaUsanidi(Usanidi.SEVA + "/mjinga/nywira-mpya/" + tokeni);
    		hudumaYaBaruaPepe.tuma(emails, null, wajiboostDao.tafutaUsanidi(Usanidi.BADILI_NYWIRA), String.format(Usanidi.BADILI_NYWIRA_MESEJI, linki), null);
    		
    		return Response.ok().entity("<div> Nywira yako mpya ni: <strong>"+nywiraMpya+" </strong> <br> Kumbuka badili hii nywira.</div>").build();
    }
    
    public static class NywiraKontena {
	    	public String baruaPepe;
	    	public String nywiraYaZamani;
	    	public String nywiraMpya;
    }
}
