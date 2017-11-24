package com.machi.wajinga.dao.mkopo;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.joda.time.DateTime;
import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.IDao;
import com.machi.wajinga.dao.mjinga.Mjinga;

@Contract
public interface MkopoDao extends IDao {

    /**
     * Tafuta mikopo
     *
     * @param queryParameters
     * @return List<Mikopo>
     */
    List<Mkopo> tafutaMikopo(MultivaluedMap<String, String> queryParameters);

    /**
     * Tafuta ombi la mkopo
     *
     * @param filters
     * @return
     */
    List<OmbiLaMkopo> tafutaOmbiLaMkopo(MultivaluedMap<String, String> filters);

    /**
     * @param ombi
     * @return Boolean - tunzwa au hapana
     */
    Boolean omba(OmbiLaMkopo ombi);

    /**
     * Cheki ka mjinga ana deni
     *
     * @param mjinga
     * @return
     */
    Boolean anaDeni(Mjinga mjinga);

    /**
     * Tafuta ombi la mkopo kwa id yake
     *
     * @param ombiId
     * @return
     */
    OmbiLaMkopo tafutaOmbiLaMkopo(Long ombiId);

    /**
     * Tafuta mkopo
     *
     * @param mkopoId
     * @return Mkopo
     */
    Mkopo tafutaMkopo(Long mkopoId);

    /**
     * @param ombi
     * @return Boolean
     */
    Boolean kataaOmbi(OmbiLaMkopo ombi);

    /**
     * @param ombi
     * @param dateTime - mwisho wa rejesho
     * @return Boolean
     */
    Boolean kubaliOmbi(OmbiLaMkopo ombi, DateTime dateTime);

    /**
     * @param mkopo
     * @param rejesho
     * @return Boolean
     */
    Boolean rejesha(Mkopo mkopo, Rejesho rejesho);
}
