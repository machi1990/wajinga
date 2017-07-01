package com.machi.wajinga.dao;

import javax.jdo.PersistenceManagerFactory;

public interface IDao {

	PersistenceManagerFactory getPmf();
	void setPmf(String source);
}
