package com.machi.wajinga.ws.filters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

public class GzipWriteInterceptor implements WriterInterceptor {
 
    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
                    throws IOException, WebApplicationException {
    	
    		if (context.getProperty("acceptedEncoding") != null && context.getProperty("acceptedEncoding").toString().contains("gzip")) {
    	        final OutputStream outputStream = context.getOutputStream();
    	        context.setOutputStream(new GZIPOutputStream(outputStream));
    	        context.getHeaders().add("Content-Encoding", "gzip");
    		}
    		
        context.proceed();
    }
}
