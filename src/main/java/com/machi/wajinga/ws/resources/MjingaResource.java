package com.machi.wajinga.ws.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.mjinga.Mchambo;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.mkopo.Mkopo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;

@Path("mjinga")
public class MjingaResource {
	
	private MjingaDao dao;
	@Context SecurityContext context;
	
	@Inject 
	public  MjingaResource(WajingaDao wajingaDao) {
		dao = wajingaDao.getMjingaDao();
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
}
