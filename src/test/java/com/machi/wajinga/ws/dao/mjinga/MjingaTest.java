package com.machi.wajinga.ws.dao.mjinga;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.Mjinga.Cheo;

public class MjingaTest {

	@Before
	public void configure() {

	}

	@Test
	public void encryptionTest() {
		Mjinga mjinga = new Mjinga("", "", "", "nywira", "+33652003035", "Kazi", Cheo.KATIBU, DateTime.now());
		Assert.assertEquals(true, mjinga.nywiraSahihi("nywira"));
		Assert.assertEquals(false, mjinga.nywiraSahihi("nwira"));
	}
}
