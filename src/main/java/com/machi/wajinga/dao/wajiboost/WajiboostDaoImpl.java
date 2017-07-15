package com.machi.wajinga.dao.wajiboost;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.wajiboost.Tukio.Oni;

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
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.begin();
			try {
				Usanidi usanidi_ = persistenceManager.getObjectById(Usanidi.class, usanidi.getFunguo());
				if (usanidi_ != null) {
					usanidi.getMabadiliko().putAll(usanidi_.getMabadiliko());
				}
			} catch (Exception e) {
			}

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

	@Override
	public Boolean wekaTukio(Tukio tukio) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.begin();
			tukio.setMuandaaji(persistenceManager.getObjectById(Mjinga.class, tukio.getMuandaaji().getId()));
			persistenceManager.makePersistent(tukio);
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

	@Override
	public Boolean wekaLengo(Lengo lengo) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.begin();
			persistenceManager.makePersistent(lengo);
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

	@Override
	public Lengo tafutaLengo(Long lengoId) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();

		try {
			Lengo lengo = persistenceManager.getObjectById(Lengo.class, lengoId);
			return persistenceManager.detachCopy(lengo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Tukio tafutaTukio(Long tukioId) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();

		try {
			Tukio tukio = persistenceManager.getObjectById(Tukio.class, tukioId);
			tukio = persistenceManager.detachCopy(tukio);
			tukio.getWashiriki().forEach(mjinga -> mjinga.wipe());
			return tukio;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Tukio> matukio() {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(Tukio.class);

		try {
			List<Tukio> matukio = (List<Tukio>) query.execute();
			matukio = (List<Tukio>) persistenceManager.detachCopyAll(matukio);
			matukio.forEach(tukio -> {
				tukio.getMuandaaji().wipe();
				tukio.getWashiriki().forEach(mshiriki -> mshiriki.wipe());
			});
			return matukio;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Tukio>();
		} finally {
			persistenceManager.close();
			query.closeAll();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Lengo> malengo() {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(Lengo.class);
		try {
			List<Lengo> malengo = (List<Lengo>) query.execute();
			malengo = (List<Lengo>) persistenceManager.detachCopyAll(malengo);
			return malengo;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Lengo>();
		} finally {
			persistenceManager.close();
			query.closeAll();
		}
	}

	@Override
	public Boolean futaLengo(Lengo lengo) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();

		try {
			lengo = persistenceManager.getObjectById(Lengo.class, lengo.getId());
			persistenceManager.deletePersistent(lengo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Boolean futaTukio(Tukio tukio) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();

		try {
			tukio = persistenceManager.getObjectById(Tukio.class, tukio.getId());
			persistenceManager.deletePersistent(tukio);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Boolean shirikiTukio(Tukio tukio, Mjinga mjinga) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.commit();
			tukio = persistenceManager.getObjectById(Tukio.class, tukio.getId());
			mjinga = persistenceManager.getObjectById(Mjinga.class, mjinga.getId());
			tukio.getWashiriki().add(mjinga);
			persistenceManager.deletePersistent(tukio);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			persistenceManager.close();
		}
	}

	@Override
	public Boolean tokaKwenyeTukio(Tukio tukio, Mjinga mjinga) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.commit();
			tukio = persistenceManager.getObjectById(Tukio.class, tukio.getId());
			tukio.getWashiriki().removeIf(mshiriki -> mshiriki.getId().equals(mjinga.getId()));
			persistenceManager.makePersistent(tukio);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			persistenceManager.close();
		}
	}

	@Override
	public Boolean toaOniTukio(Tukio tukio, Oni oni) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.commit();
			tukio = persistenceManager.getObjectById(Tukio.class, tukio.getId());
			tukio.getMaoni().add(oni);
			persistenceManager.makePersistent(tukio);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			persistenceManager.close();
		}
	}

	@Override
	public Boolean maelezoYaTukio(Tukio tukio) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.commit();
			final Tukio tukio_ = persistenceManager.getObjectById(Tukio.class, tukio.getId());
			tukio_.setMaelezoBaadaYaTukio(tukio.getMaelezoBaadaYaTukio());
			persistenceManager.makePersistent(tukio_);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			persistenceManager.close();
		}
	}

	@Override
	public Boolean badiliTaarifa(Tukio tukio) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.commit();
			final Tukio tukio_ = persistenceManager.getObjectById(Tukio.class, tukio.getId());
			tukio_.setMaelezoYaTukio(tukio.getMaelezoYaTukio());
			tukio_.setTarehe(tukio.getTarehe());
			tukio_.setMahali(tukio.getMahali());
			persistenceManager.makePersistent(tukio_);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			persistenceManager.close();
		}
	}

}
