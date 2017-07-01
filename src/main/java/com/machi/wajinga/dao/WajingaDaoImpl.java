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

@Service
public class WajingaDaoImpl implements WajingaDao {

	@Inject protected MjingaDao mjingaDao;
	@Inject protected MaafaDao maafaDao;
	@Inject protected MalipoYaMweziDao malipoDao;
	@Inject protected MkopoDao mkopoDao;
	@Inject protected WajiboostDao wajiboostDao;
	private PersistenceManagerFactory  pmf;
	
	public WajingaDaoImpl() {
		super();
		setPmf("Wajinga-Dev");
	}

	@Override
	public MaafaDao getMaafaDao() {
		return maafaDao;
	}

	@Override
	public MjingaDao getMjingaDao() {
		return mjingaDao;
	}

	@Override
	public MalipoYaMweziDao getMalipoDao() {
		return malipoDao;
	}

	@Override
	public MkopoDao getMkopoDao() {
		return mkopoDao;
	}

	@Override
	public WajiboostDao getWajiboostDao() {
		return wajiboostDao;
	}

	public PersistenceManagerFactory getPmf() {
		return pmf;
	}

	public void setPmf(String source) {
		this.pmf = JDOHelper.getPersistenceManagerFactory(source);
	}
}
