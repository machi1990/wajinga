package com.machi.wajinga.dao;

import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.maafa.MaafaDao;
import com.machi.wajinga.dao.malipo.MalipoYaMweziDao;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.dao.mkopo.MkopoDao;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;

@Contract
public interface WajingaDao {
	MaafaDao getMaafaDao();
	MjingaDao getMjingaDao();
	MalipoYaMweziDao getMalipoDao();
	MkopoDao getMkopoDao();
	WajiboostDao getWajiboostDao();
}
