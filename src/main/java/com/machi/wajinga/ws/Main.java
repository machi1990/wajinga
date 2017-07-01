package com.machi.wajinga.ws;

import org.eclipse.jetty.alpn.ALPN;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NegotiatingServerConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.jackson1.Jackson1Feature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.ws.filters.BasicAuthenticationFilter;
import com.machi.wajinga.ws.filters.CustomObjectMapper;
import com.machi.wajinga.ws.filters.EntranceFilter;
import com.machi.wajinga.ws.filters.GzipWriteInterceptor;
import com.machi.wajinga.ws.filters.ResponseHeaderFilter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Main class.
 *
 */
public class Main {	
	// private static Logger logger = Logger.getLogger(Main.class);
    private static final String BASE_URL = "127.0.0.1";

    /**
     * Starts HTTP server exposing JAX-RS resources defined in this application.
     * @return HTTP server.
     */
    public static void configureAndStartServer(Properties props) {
	   Server server = createServer(props);
		
		ResourceConfig resources = new ResourceConfig();
		resources.packages(true, "com.machi.wajinga.ws.resources");
		resources.register(Jackson1Feature.class);
		resources.register(CustomObjectMapper.class);
		resources.register(BasicAuthenticationFilter.class);
		resources.register(LoggingFeature.class);
		resources.register(EntranceFilter.class);
		resources.register(GzipWriteInterceptor.class);
		resources.register(ResponseHeaderFilter.class);
		
		ServletHolder servletHolder = new ServletHolder(new ServletContainer(resources));
		ServletContextHandler webServices = new ServletContextHandler(ServletContextHandler.SESSIONS);
		webServices.setContextPath("/ws");
		webServices.addServlet(servletHolder, "/*");
		
		ResourceHandler staticHandler = new ResourceHandler();
		staticHandler.setResourceBase(props.getProperty("static", "static"));
		staticHandler.setWelcomeFiles(new String[] { "index.html" });
		staticHandler.setDirectoriesListed(false);
		staticHandler.setCacheControl("max-age=86400, must-revalidate");
		staticHandler.setEtags(true);
		
		MimeTypes mimeTypes = new MimeTypes();
		mimeTypes.addMimeMapping("html", "text/html; charset=UTF-8");
		mimeTypes.addMimeMapping("css", "text/css; charset=UTF-8");
		mimeTypes.addMimeMapping("js", "application/javascript; charset=UTF-8");
		staticHandler.setMimeTypes(mimeTypes);
		ContextHandler staticHandlerCtx = new ContextHandler();
		staticHandlerCtx.setContextPath("/");
		staticHandlerCtx.setHandler(staticHandler);
		
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers( new Handler[] { webServices, staticHandlerCtx});

		server.setHandler(contexts);
		
		ServerConfiguration.register(props);
		try {
			server.start();
		} catch (Exception e) {
			
		}
    }

	
	private static Server createServer(Properties props) {
		Server server = new Server();
		Integer port = Integer.valueOf(props.getProperty("port","8080"));
		
		if (!"https".equalsIgnoreCase(props.getProperty("scheme"))) {
			ServerConnector connector = new ServerConnector(server);
			connector.setHost(BASE_URL);
			connector.setPort(port);
			connector.setIdleTimeout(30000);
			server.addConnector(connector);
			return server;
		}
		
		ALPN.debug=true;
		HttpConfiguration http2Config = new HttpConfiguration();
        http2Config.setSecureScheme("https");
        http2Config.setSecurePort(port);

        // SSL Context Factory for HTTPS and HTTP/2
        SslContextFactory sslContextFactory = new SslContextFactory(props.getProperty("certificate", "cert.pkcs12"));
        sslContextFactory.setKeyStorePassword(props.getProperty("paraphrase", "wajiboost"));   
        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);

        // HTTPS Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration(http2Config);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // HTTP/2 Connection Factory
        HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);
        NegotiatingServerConnectionFactory.checkProtocolNegotiationAvailable();
        
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory("h2","http/1.1");
        alpn.setDefaultProtocol("h2");
        
        // SSL Connection Factory
        SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory,alpn.getProtocol());

   
        // HTTP/2 Connector
        ServerConnector http2Connector = new ServerConnector(server,ssl,alpn,h2,new HttpConnectionFactory(httpsConfig));
        http2Connector.setPort(port);
        http2Connector.setIdleTimeout(30000);
        http2Connector.setHost(BASE_URL);
        server.addConnector(http2Connector);

        return server;
	}

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    		InputStream in; 
    		if (args.length > 0) {
    			in = new FileInputStream(args[0]);
    		} else {
    			in = Main.class.getClassLoader().getResourceAsStream("configuration.properties");
    		}
    		
    		Properties props = new Properties();
    		props.load(in);
    		in.close();
    		
    		try {
    			WajingaDao.Dao().setPmf(props.getProperty("persistence", "Wajinga-Dev"));
    	        configureAndStartServer(props);
    		} catch (Exception e) {
    			throw e;
    		} finally {
			
		}
        
    }
}

