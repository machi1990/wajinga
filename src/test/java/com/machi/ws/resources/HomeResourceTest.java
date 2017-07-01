package com.machi.ws.resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class HomeResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig().packages(true,"com.machi.ws.resources");
	}

 
    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
    		String responseMsg = target("home").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }
}
