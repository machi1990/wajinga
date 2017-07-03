package com.machi.wajinga.dao.mjinga;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;

@Service
@SuppressWarnings("unchecked")
public class MjingaDaoImpl extends AbstractDaoImpl implements MjingaDao {

	public MjingaDaoImpl() {
		super();
	}

	@Override
	public Mjinga tafutaMjingaKwaJina(String username) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		Query query = pm.newQuery(Mjinga.class);
		query.declareParameters("String username");
		query.setFilter("jina == username");
		List<Mjinga> detached = (List<Mjinga>) query.execute(username);
		
		if (detached.isEmpty()) {
			return null;
		}
		
		return pm.detachCopy(detached.get(0)).wipe();
	}
}
