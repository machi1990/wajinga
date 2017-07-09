package com.machi.wajinga.dao.mkopo;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.IDao;

@Contract
public interface MkopoDao extends IDao {

	/**
	 * Tafuta mikopo
	 * @param queryParameters
	 * @return List<Mikopo>
	 */
	List<Mkopo> tafutaMikopo(MultivaluedMap<String, String> queryParameters);

	/**
	 * Tafuta ombi la mkopo
	 * @param filters
	 * @return
	 */
	List<OmbiLaMkopo> tafutaOmbiLaMkopo(MultivaluedMap<String, String> filters);
}
