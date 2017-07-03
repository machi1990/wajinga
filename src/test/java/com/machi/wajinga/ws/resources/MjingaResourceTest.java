package com.machi.wajinga.ws.resources;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mkopo.Mkopo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;

@SuppressWarnings("unchecked")
public class MjingaResourceTest extends AbstractResourceTest {

	@Test
	public void getFullMjinga() {
		Mjinga mjinga = getTarget("mjinga").request().get(Mjinga.class);
		Assert.assertNotNull(mjinga);
	}
	
	@Test
	public void getMikopoYaMjinga() {
		List<Mkopo> mikopo = getTarget("mjinga").path("mikopo").request().get(List.class);
		Assert.assertNotNull("Haiwezi kuwa tupu", mikopo);
		Assert.assertNotSame(1, mikopo.size());
	}
	
	@Test
	public void getOmbiYaMjinga() {
		List<OmbiLaMkopo> mikopo = getTarget("mjinga").path("maombi-ya-mkopo").request().get(List.class);
		Assert.assertNotNull("Haiwezi kuwa tupu", mikopo);
		Assert.assertNotSame(0, mikopo.size());
	}
}
