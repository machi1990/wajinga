package com.machi.wajinga.dao.mjinga;

import org.jvnet.hk2.annotations.Contract;

import com.machi.wajinga.dao.IDao;

@Contract
public interface MjingaDao extends IDao {

	Mjinga tafutaMjungaKwaJina(String username);

}
