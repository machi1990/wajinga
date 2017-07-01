package com.machi.wajinga.ws.resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.machi.wajinga.dao.mjinga.Mjinga;


/**
 * Root resource (exposed at "home" path)
 */
@Path("ingia")
public class HomeResource {

	@Context SecurityContext context;
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Mjinga login() {
        Mjinga mjinga = (Mjinga) context.getUserPrincipal();
   
        mjinga.setMaafa(null);
        mjinga.setMalipo(null);
        mjinga.setMichambo(null);
        mjinga.setMikopo(null);
        
        return mjinga;
    }
    
    @GET
    @PermitAll
    @Path("badili-nywira")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword() {
    		return Response.noContent().build();
    }
    
}