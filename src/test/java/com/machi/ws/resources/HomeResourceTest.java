package com.machi.ws.resources;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson1.Jackson1Feature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Test;

import com.machi.wajinga.dao.mjinga.Mjinga;

import static org.junit.Assert.assertEquals;

public class HomeResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
      	return new ResourceConfig().packages(true,"com.machi.ws.resources").register( new AbstractBinder() {
			@Override
			protected void configure() {
				bind("Wajinga-Test").to(String.class).named("Persistence_Unit");
			}
		});
	}

	@After
	public void tearDown() {
		
	}
	
    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
    		WebTarget target = target("home").register(Jackson1Feature.class);
    		Mjinga mjinga = target.request().get(Mjinga.class);
        assertEquals(null, mjinga);
    }
    
    
}
