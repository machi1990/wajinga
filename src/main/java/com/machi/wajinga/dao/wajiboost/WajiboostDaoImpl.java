package com.machi.wajinga.dao.wajiboost;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;

@Service
public class WajiboostDaoImpl extends AbstractDaoImpl implements WajiboostDao {

	@Override
	public String tafutaUsanidi(String funguo) {
		if (StringUtils.isEmpty(funguo)) {
			return "";
		}

		PersistenceManager persistenceManager = getPmf().getPersistenceManager();

		try {
			Usanidi usanidi = persistenceManager.detachCopy(persistenceManager.getObjectById(Usanidi.class, funguo));

			if (usanidi == null) {
				return "";
			}

			return usanidi.getKilichomo();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			persistenceManager.close();
		}

	}

	@Override
	public Boolean tunzaUsanidi(Usanidi usanidi) {
		if (usanidi == null) {
			return false;
		}

		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.begin();
			persistenceManager.makePersistent(usanidi);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}

			persistenceManager.close();
		}

		return true;
	}

}
