package com.machi.wajinga.dao.maafa;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.IDao;

@Contract
public interface MaafaDao extends IDao {

	/**
	 * Tafuta maafa
	 * @param filters
	 * @return List<Maafa> - maafa yaliyopatikana
	 */
	List<Maafa> tafutaMaafa(MultivaluedMap<String, String> filters);

	/**
	 * Tafuta afa kwa id ya afa
	 * @param afaId
	 * @return Maafa
	 */
	Maafa tafutaAfa(Long afaId);

	/**
	 * Tunza afa
	 * @param maafa
	 * @return TODO
	 */
	Boolean wekaAfa(Maafa maafa);
	
	/**
	 * Futa afa la mjinga
	 * @param afaId
	 * @param mjingaId
	 * @return Boolean - ombi limekubaliwa au hapana
	 */
	Boolean futa(Long afaId, Long mjingaId);
}
