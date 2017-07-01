package com.machi.wajinga.dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public abstract class AbstractDaoImpl implements IDao {
	private PersistenceManagerFactory pmf;
	
	public AbstractDaoImpl() {
		super();
	}

	@Override
	public PersistenceManagerFactory getPmf() {
		return pmf;
	}

	@Override
	public void setPmf(String source) {
		pmf = JDOHelper.getPersistenceManagerFactory(source);
	}
}
