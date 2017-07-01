package com.machi.wajinga.dao;


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

public class WajingaDao {
	private static WajingaDao dao;
	protected MjingaDao mjingaDao = new MjingaDaoImpl();
	protected MaafaDao maafaDao = new MaafaDaoImpl();
	protected MalipoYaMweziDao malipoDao = new MalipoYaMweziDaoImpl();
	protected MkopoDao mkopoDao = new MkopoDaoImpl();
	protected WajiboostDao wajiboostDao = new WajiboostDaoImpl();
	
	public WajingaDao() {
		super();
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
		maafaDao.setPmf(source);
		malipoDao.setPmf(source);
		mkopoDao.setPmf(source);
		wajiboostDao.setPmf(source);
		mjingaDao.setPmf(source);
	}

	public static WajingaDao Dao() {
		if (dao == null) {
			dao = new WajingaDao();
		}
		
		return dao;
	}
}
