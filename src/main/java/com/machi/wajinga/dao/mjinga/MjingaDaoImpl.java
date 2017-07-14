package com.machi.wajinga.dao.mjinga;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;
import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.mkopo.Mkopo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo.Jibu;

@Service
@SuppressWarnings("unchecked")
public class MjingaDaoImpl extends AbstractDaoImpl implements MjingaDao {

	public MjingaDaoImpl() {
		super();
	}

	@Override
	public Mjinga tafutaMjingaKwaJina(String username) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(Mjinga.class);
		query.declareParameters("String username");
		query.setFilter("jina == username");
		query.setUnique(true);
		
		try {
			Mjinga mjinga = (Mjinga) query.execute(username);
			
			if (mjinga == null) {
				return null;
			}
			
			return persistenceManager.detachCopy(mjinga).wipe();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.closeAll();
			persistenceManager.close();
		}
	}

	@Override
	public void badiliNywira(Mjinga mjinga, String nywiraMpya) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();
		try {
			transaction.begin();
			mjinga = persistenceManager.getObjectById(Mjinga.class, mjinga.getId());
			mjinga.setNywiraTokeni(null);
			mjinga.setTrhOmbiLaKubadiliNywira(null);
			mjinga.wekaNywira(nywiraMpya);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}

			persistenceManager.close();
		}
	}

	@Override
	public Boolean tunzaMjinga(Mjinga mjinga) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();
		try {
			transaction.begin();
			persistenceManager.makePersistent(mjinga);
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
	public Mjinga tafutaTaarifaZaMjinga(Long id) {
		PersistenceManager pm = getPmf().getPersistenceManager();

		try {
			pm.getFetchPlan().addGroup("Mikopo");
			pm.getFetchPlan().addGroup("Michambo");
			pm.getFetchPlan().addGroup("Malipo");
			pm.getFetchPlan().addGroup("Maafa");

			Mjinga mjinga = pm.getObjectById(Mjinga.class, id);

			return pm.detachCopy(mjinga).kokotoaIdadi();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.close();
		}
	}

	@Override
	public List<Mkopo> tafutaMikopoYaMjinga(Long id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.getFetchPlan().addGroup("Mikopo");
			
			Mjinga mjinga = pm.getObjectById(Mjinga.class, id);

			List<Mkopo> mikopo = pm.detachCopy(mjinga).getMikopo();

			mikopo.forEach(mkopo -> {
				mkopo.setMkopaji(null);
				mkopo.setSignatori(null);
				mkopo.setOmbi(null);
				mkopo.setMarejesho(null);
			});

			return mikopo;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Mkopo>();
		} finally {
			pm.close();
		}
	}

	@Override
	public List<Mkopo> tafutaMikopoIliyoLipwa(Long id) {
		return tafutaMikopoYaMjinga(id).stream().filter(Mkopo::isUmeLipwa).collect(Collectors.toList());
	}

	@Override
	public List<Mkopo> tafutaDeni(Long id) {
		return tafutaMikopoYaMjinga(id).stream().filter(mkopo -> !mkopo.isUmeLipwa()).collect(Collectors.toList());
	}

	@Override
	public List<OmbiLaMkopo> tafutaOmbiLaMkopo(Long id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.getFetchPlan().addGroup("Mikopo");

			Mjinga mjinga = pm.getObjectById(Mjinga.class, id);
			
			List<OmbiLaMkopo> maombi = pm.detachCopy(mjinga).getOmbiMkopo();

			maombi.forEach(ombi -> {
				ombi.setMjibuji(null);
				ombi.setMjinga(null);
			});

			return maombi;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<OmbiLaMkopo>();
		} finally {
			pm.close();
		}
	}

	@Override
	public List<OmbiLaMkopo> tafutaOmbiLaMkopoYaliyokataliwa(Long id) {
		return tafutaOmbiLaMkopo(id).stream().filter(ombi -> Jibu.LIMEKATALIWA.equals(ombi.getJibu()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public List<OmbiLaMkopo> tafutaOmbiLaMkopoLililokubaliwa(Long id) {
		return tafutaOmbiLaMkopo(id).stream().filter(ombi -> Jibu.LIMEKUBALIWA.equals(ombi.getJibu()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public List<Mchambo> tafutaMichambo(Long id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.getFetchPlan().addGroup("Michambo");

			Mjinga mjinga = pm.getObjectById(Mjinga.class, id);

			if (mjinga == null) {
				return new ArrayList<Mchambo>();
			}

			return pm.detachCopy(mjinga).getMichambo();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Mchambo>();
		} finally {
			pm.close();
		}
	}

	@Override
	public List<Maafa> tafutaMaafa(Long id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.getFetchPlan().addGroup("Maafa");

			Mjinga mjinga = pm.getObjectById(Mjinga.class, id);

			if (mjinga == null) {
				return new ArrayList<Maafa>();
			}

			List<Maafa> maafa = pm.detachCopy(mjinga).getMaafa();
			maafa.forEach(afa -> afa.setMjinga(null));

			return maafa;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Maafa>();
		} finally {
			pm.close();
		}
	}

	@Override
	public Mjinga tafutaMjingaKwaBaruaPepe(String baruaPepe) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(Mjinga.class);
		query.declareParameters("String email");
		query.setFilter("baruaPepe == email");

		try {
			List<Mjinga> detached = (List<Mjinga>) query.execute(baruaPepe);

			if (detached.isEmpty()) {
				return null;
			}

			return persistenceManager.detachCopy(detached.get(0)).wipe();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.closeAll();
			persistenceManager.close();
		}
	}

	@Override
	public Boolean wekaNywiraTokeni(Long id, String tokeni) {
		if (StringUtils.isEmpty(tokeni) || id == null) {
			return false;
		}

		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		try {

			Mjinga mjinga = persistenceManager.getObjectById(Mjinga.class, id);
			mjinga.setNywiraTokeni(tokeni);
			mjinga.setTrhOmbiLaKubadiliNywira(DateTime.now());

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			persistenceManager.close();
		}

		return true;
	}

	@Override
	public Boolean badiliCheo(Mjinga mjinga) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();
		try {
			transaction.begin();
			Mjinga mjinga_ = persistenceManager.getObjectById(Mjinga.class, mjinga.getId());
			mjinga_.setCheo(mjinga.getCheo());
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
	public void wekaMuda(Mjinga mjinga) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();
		try {
			transaction.begin();
			mjinga = persistenceManager.getObjectById(Mjinga.class, mjinga.getId());
			mjinga.setMudaWaMwishoKutumia(DateTime.now());
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

	}

	@Override
	public List<Mchambo> tafutaMichambo(Long tarehe, String elezo) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(Mchambo.class);
		try {
			List<String> filters = new ArrayList<String>();
			if (elezo != null) {
				filters.add("mchambo.matches(\"(.*)" + elezo +"(.*)\")");
			}
			
			List<DateTime> tarehes = new ArrayList<DateTime>();
			if (tarehe != null) {
				DateTime muda = new DateTime(tarehe).withMillisOfDay(0);
				tarehes.add(muda);
				tarehes.add(muda.plusDays(1));
				List<String> params = new ArrayList<String>();
				params.add("DateTime TAREHE");
				params.add("DateTime KESHO");
				filters.add("tarehe >= TAREHE && tarehe <= KESHO");
				query.declareImports("import " + DateTime.class.getCanonicalName());
			}
			
			query.setFilter(filters.stream().collect(Collectors.joining(" && ")));
			List<Mchambo> michambo = (List<Mchambo>) query.executeWithArray(tarehes.toArray());
			return (List<Mchambo>) persistenceManager.detachCopyAll(michambo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Mchambo>();
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Boolean ongezaMchamboKwaMjinga(Mjinga mjinga, Mchambo mchambo) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();
		
		try {
			transaction.begin();
			persistenceManager.getFetchPlan().addGroup("Michambo");
			mjinga = persistenceManager.getObjectById(Mjinga.class, mjinga.getId());
			mjinga.getMichambo().add(mchambo);
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
	public Mchambo tafutaMchambo(Long mchamboId) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		try {
			return persistenceManager.detachCopy(persistenceManager.getObjectById(Mchambo.class, mchamboId));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Boolean ongezaMchambo(Mchambo mchambo) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();
		
		try {
			transaction.begin();
			persistenceManager.makePersistent(mchambo);
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