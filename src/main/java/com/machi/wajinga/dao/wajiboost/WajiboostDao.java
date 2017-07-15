package com.machi.wajinga.dao.wajiboost;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.IDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.wajiboost.Tukio.Oni;

@Contract
public interface WajiboostDao extends IDao {

	/**
	 * 
	 * @param string
	 * @return String
	 */
	String tafutaUsanidi(String string);

	/**
	 * 
	 * @param usanidi
	 * @return Boolean
	 */
	Boolean tunzaUsanidi(Usanidi usanidi);

	/**
	 * 
	 * @param tukio
	 * @return Boolean
	 */
	Boolean wekaTukio(Tukio tukio);

	/**
	 * 
	 * @param lengo
	 * @return Boolean
	 */
	Boolean wekaLengo(Lengo lengo);

	/**
	 * 
	 * @param lengoId
	 * @return
	 */
	Lengo tafutaLengo(Long lengoId);

	/**
	 * 
	 * @param tukioId
	 * @return Tukio
	 */
	Tukio tafutaTukio(Long tukioId);

	/**
	 * TODO - add filters Matukio yote
	 * 
	 * @return List<Tukio>
	 */
	List<Tukio> matukio();

	/**
	 * TODO - add filters
	 * 
	 * Tafuta melengo yote
	 * 
	 * @return List<Lengo>
	 */
	List<Lengo> malengo();

	/**
	 * 
	 * @param lengo
	 * @return Boolean
	 */
	Boolean futaLengo(Lengo lengo);

	/**
	 * 
	 * @param tukio
	 * @return Boolean
	 */
	Boolean futaTukio(Tukio tukio);

	/**
	 * Shiriki tukio
	 * 
	 * @param tukio
	 * @param mjinga
	 * @return Boolean
	 */
	Boolean shirikiTukio(Tukio tukio, Mjinga mjinga);

	/**
	 * Toka kwenye tukio
	 * 
	 * @param tukio
	 * @param mjinga
	 * @return Boolean
	 */
	Boolean tokaKwenyeTukio(Tukio tukio, Mjinga mjinga);

	/**
	 * Toa oni
	 * 
	 * @param tukio
	 * @param oni
	 * @return Boolean
	 */
	Boolean toaOniTukio(Tukio tukio, Oni oni);

	/**
	 * 
	 * @param tukio
	 * @return Boolean
	 */
	Boolean maelezoYaTukio(Tukio tukio);

	/**
	 * Badili taarifa za tukio
	 * 
	 * @param tukio
	 * @return
	 */
	Boolean badiliTaarifa(Tukio tukio);
}
