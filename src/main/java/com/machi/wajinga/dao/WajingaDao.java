package com.machi.wajinga.dao;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.maafa.MaafaDao;
import com.machi.wajinga.dao.malipo.MalipoYaMweziDao;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.mkopo.MkopoDao;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;
import com.machi.wajinga.ws.ServerConfiguration;

@Service
public class WajingaDao {
	private MjingaDao mjingaDao;
	private MaafaDao maafaDao;
	private MalipoYaMweziDao malipoDao;
	private MkopoDao mkopoDao;
	private WajiboostDao wajiboostDao;

	@Inject
	public WajingaDao(MjingaDao mjingaDao, MaafaDao maafaDao, MalipoYaMweziDao malipoDao, MkopoDao mkopoDao,
			WajiboostDao wajiboostDao, ServerConfiguration config) throws Exception {
		super();
		this.mjingaDao = mjingaDao;
		this.maafaDao = maafaDao;
		this.malipoDao = malipoDao;
		this.mkopoDao = mkopoDao;
		this.wajiboostDao = wajiboostDao;
		setPmf(config.getPesistenceUnit());
	}

	public MaafaDao getMaafaDao() {
		return maafaDao;
	}

	public MjingaDao getMjingaDao() {
		return mjingaDao;
	}

	public MalipoYaMweziDao getMalipoDao() {
		return malipoDao;
	}

	public MkopoDao getMkopoDao() {
		return mkopoDao;
	}

	public WajiboostDao getWajiboostDao() {
		return wajiboostDao;
	}

	public void setPmf(String source) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(source);
		maafaDao.setPmf(pmf);
		malipoDao.setPmf(pmf);
		mkopoDao.setPmf(pmf);
		wajiboostDao.setPmf(pmf);
		mjingaDao.setPmf(pmf);
	}
}
