package com.machi.wajinga.dao;

import javax.jdo.PersistenceManagerFactory;

public abstract class AbstractDaoImpl implements IDao {
	private PersistenceManagerFactory pmf;

	public AbstractDaoImpl() {
		super();
	}

	protected PersistenceManagerFactory getPmf() {
		return pmf;
	}

	@Override
	public void setPmf(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
}
