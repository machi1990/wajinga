package com.machi.wajinga.ws.resources;

import java.io.IOException;
import java.security.Principal;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson1.Jackson1Feature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.maafa.MaafaDao;
import com.machi.wajinga.dao.maafa.MaafaDaoImpl;
import com.machi.wajinga.dao.malipo.MalipoYaMweziDao;
import com.machi.wajinga.dao.malipo.MalipoYaMweziDaoImpl;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.mjinga.MjingaDaoImpl;
import com.machi.wajinga.dao.mkopo.MkopoDao;
import com.machi.wajinga.dao.mkopo.MkopoDaoImpl;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;
import com.machi.wajinga.dao.wajiboost.WajiboostDaoImpl;
import com.machi.wajinga.ws.ServerConfiguration;
import com.machi.wajinga.ws.dataset.DataSet;

public abstract class AbstractResourceTest extends JerseyTest {

	public static class SecurityContainerFilter implements ContainerRequestFilter {

		@Inject WajingaDao dao;
		
		@Override
		public void filter(ContainerRequestContext requestContext) throws IOException {
			requestContext.setSecurityContext(new SecurityContext() {
				
				@Override
				public boolean isUserInRole(String role) {
					return true;
				}
				
				@Override
				public boolean isSecure() {
					return false;
				}
				
				@Override
				public Principal getUserPrincipal() {
					return dao.getMjingaDao().tafutaMjingaKwaJina("machi");
				}
				
				@Override
				public String getAuthenticationScheme() {
					return "http";
				}
			});
		}
		
	}
	
	@Override
	protected Application configure() {
		DataJanitor.clean();
		DataSet.generate("Wajinga-Test");
		enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
       	return new ResourceConfig().packages(true,"com.machi.wajinga.ws.resources")
       		.register(Jackson1Feature.class).register(SecurityContainerFilter.class)
       		.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(MaafaDaoImpl.class).to(MaafaDao.class);
				bind(MjingaDaoImpl.class).to(MjingaDao.class);
				bind(MalipoYaMweziDaoImpl.class).to(MalipoYaMweziDao.class);
				bind(MkopoDaoImpl.class).to(MkopoDao.class);
				bind(WajiboostDaoImpl.class).to(WajiboostDao.class);
				bind(WajingaDao.class).to(WajingaDao.class);
				bind(ServerConfiguration.class).to(ServerConfiguration.class);
				Properties props = new Properties();
				props.setProperty("persistence", "Wajinga-Test");
				bind(props).to(Properties.class);
			}
		});
	}
	
	public WebTarget getTarget() {
		return super.target().register(Jackson1Feature.class);
	}

	public WebTarget getTarget(String path) {
		return super.target(path).register(Jackson1Feature.class);
	}
}
