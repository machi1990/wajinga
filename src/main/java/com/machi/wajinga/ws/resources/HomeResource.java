package com.machi.wajinga.ws.resources;

import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.MjingaDao;


/**
 * Root resource (exposed at "home" path)
 */
@Path("ingia")
public class HomeResource {

	@Context private SecurityContext context;
	private MjingaDao mjingaDao;
	
	@Inject
	public HomeResource(WajingaDao wajingaDao) {
		super();
		mjingaDao  = wajingaDao.getMjingaDao();
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
    		if (baruaPepe == null) {
    			throw new BadRequestException("Barua pepe ni muhimu");
    		}
    		
    		Mjinga mjinga = mjingaDao.tafutaMjingaKwaBaruaPepe(baruaPepe);
    		
    		if (mjinga == null) {
    			throw new NotFoundException("Barua pepe haipo");
    		}
    		
    		
    		/**
    		 * TODO
    		 */
    		String tokeni = UUID.randomUUID().toString();
    		
    		mjinga.setNywiraTokeni(tokeni);
    		mjinga.setTrhOmbiLaKubadiliNywira(DateTime.now());
    		
    		/**
    		 * Tuma barua pepe kwa mtumiaji.
    		 */
    		return Response.noContent().build();
    }
    
    public class NywiraKontena {
	    	public String baruaPepe;
	    	public String nywiraYaZamani;
	    	public String nywiraMpya;
    }
}
