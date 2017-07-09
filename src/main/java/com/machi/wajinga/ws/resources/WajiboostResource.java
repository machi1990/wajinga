package com.machi.wajinga.ws.resources;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.machi.wajinga.dao.WajingaDao;
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
	@PermitAll
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
}
