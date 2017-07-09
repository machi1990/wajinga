package com.machi.wajinga.dao.mkopo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;

@Service
public class MkopoDaoImpl extends AbstractDaoImpl implements MkopoDao {

	public MkopoDaoImpl() {
		super();
	}

	@Override
	public List<Mkopo> tafutaMikopo(MultivaluedMap<String, String> queryParameters) {
		return new ArrayList<Mkopo>();
	}

	@Override
	public List<OmbiLaMkopo> tafutaOmbiLaMkopo(MultivaluedMap<String, String> filters) {
		return new ArrayList<OmbiLaMkopo>();
	}

}
