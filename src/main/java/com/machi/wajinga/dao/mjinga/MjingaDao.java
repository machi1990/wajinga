package com.machi.wajinga.dao.mjinga;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.IDao;
import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.mkopo.Mkopo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;

@Contract
public interface MjingaDao extends IDao {

	/**
	 * Tafuta mjinga kwa jina
	 * @param jina
	 * @return Mjinga
	 */
	Mjinga tafutaMjingaKwaJina(String jina);

	/**
	 * Tafuta mjinga kwa jina
	 * @param jina
	 * @return Mjinga
	 */
	Mjinga tafutaMjingaKwaBaruaPepe(String baruaPepe);
	
	/**
	 * Badili nywira ya mjinga
	 * @param mjinga
	 * @param nywiraMpya
	 */
	void badiliNywira(Mjinga mjinga, String nywiraMpya);

	/**
	 * Weka mjinga kwa database
	 * @param mjinga
	 * @return Boolean
	 */
	Boolean tunzaMjinga(Mjinga mjinga);

	/**
	 * Tafuta taarifa kamili za mjinga
	 * @param id
	 * @return Mjinga
	 */
	Mjinga tafutaTaarifaZaMjinga(Long id);

	/**
	 * Tafuta mikopo yote ya mjinga
	 * @param id
	 * @return List<Mkopo>
	 */
	List<Mkopo> tafutaMikopoYaMjinga(Long id);
	
	/**
	 * Tafuta mikopo ya mjingwa iliyolipwa
	 * @param id
	 * @return List<Mkopo>
	 */
	List<Mkopo> tafutaMikopoIliyoLipwa(Long id);
	
	/**
	 * Tafuta mikopo ya mjingwa isiyolipwa
	 * @param id
	 * @return List<Mkopo>
	 */
	List<Mkopo> tafutaDeni(Long id);

	/**
	 * Rudisha maombi yote ya mikopo
	 * @param id
	 * @return List<OmbiLaMkopo>
	 */
	List<OmbiLaMkopo> tafutaOmbiLaMkopo(Long id);
	
	/**
	 * Rudisha maombi ya mikopo yaliyokataliwa
	 * @param id
	 * @return List<OmbiLaMkopo>
	 */
	List<OmbiLaMkopo> tafutaOmbiLaMkopoYaliyokataliwa(Long id);
	
	/**
	 * Rudisha maombi ya mikopo yaliyokubaliwa
	 * @param id
	 * @return List<OmbiLaMkopo>
	 */
	List<OmbiLaMkopo> tafutaOmbiLaMkopoLililokubaliwa(Long id);

	/**
	 * Rudisha michambo
	 * @param id
	 * @return List<Mchambo>
	 */
	List<Mchambo> tafutaMichambo(Long id);
	
	/**
	 * Rudisha maafa
	 * @param id
	 * @return
	 */
	List<Maafa> tafutaMaafa(Long id);

	/**
	 * Weka tokeni ya uombaji nywira mpya
	 * @param id
	 * @param tokeni
	 * @return Boolean - imewekwa 
	 */
	Boolean wekaNywiraTokeni(Long id, String tokeni);

	Boolean badiliCheo(Mjinga mjinga);

}
