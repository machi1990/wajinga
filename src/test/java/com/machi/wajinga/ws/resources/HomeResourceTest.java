package com.machi.wajinga.ws.resources;

import org.junit.Test;

import com.machi.wajinga.dao.mjinga.Mjinga;

import static org.junit.Assert.assertEquals;

public class HomeResourceTest extends AbstractResourceTest {

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testGetIt() {
		Mjinga mjinga = getTarget("ingia").request().get(Mjinga.class);
		assertEquals("machi", mjinga.getJina());
	}

}
