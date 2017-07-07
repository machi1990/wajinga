package com.machi.wajinga.dao.malipo;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.IDao;

@Contract
public interface MalipoYaMweziDao extends IDao {
	/**
	 * Tafuta malipo ya mwezi ya majinga kutokana na filters alizochagua mtumiaji
	 * 
	 * @param queryParameters
	 * @return List<MalipoYaMwezi>
	 */
	List<MalipoYaMwezi> tafutaMalipo(MultivaluedMap<String, String> queryParameters);

	/**
	 * Tafuta taarifa za lipo la mwezi kwa kutumia id number yake
	 * 
	 * @param lipoId
	 * @return
	 */
	MalipoYaMwezi tafutaLipo(Long lipoId);

	Boolean futaLipo(Long lipoId);

	Boolean tunza(List<MalipoYaMwezi> malipo);
}
