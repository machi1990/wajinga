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
		
		try {
			List<Mjinga> detached = (List<Mjinga>) query.execute(username);
			
			if (detached.isEmpty()) {
				return null;
			}
			
			return persistenceManager.detachCopy(detached.get(0)).wipe();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
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
			
			if (mjinga == null) {
				return null;
			}
			
			return pm.detachCopy(mjinga).kokotoaIdadi();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			pm.close();
		}
	}

	@Override
	public List<Mkopo> tafutaMikopoYaMjinga(Long id) {
		PersistenceManager pm = getPmf().getPersistenceManager();
		try {
			pm.getFetchPlan().addGroup("Mikopo");
			
			Mjinga mjinga = pm.getObjectById(Mjinga.class, id);
			
			if (mjinga == null) {
				return new ArrayList<Mkopo>();
			}
			
			List<Mkopo> mikopo  = pm.detachCopy(mjinga).getMikopo();
			
			mikopo.stream().forEach(mkopo -> {
				mkopo.setMkopaji(null);
				mkopo.setSignatori(null);
				mkopo.setOmbi(null);
				mkopo.setMarejesho(null);
			});
			
			return mikopo;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Mkopo>();
		}finally {
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
			
			if (mjinga == null) {
				return new ArrayList<OmbiLaMkopo>();
			}
			
			List<OmbiLaMkopo> maombi  = pm.detachCopy(mjinga).getOmbiMkopo();
			
			maombi.stream().forEach(ombi -> {
				ombi.setMjibuji(null);
				ombi.setMjinga(null);
			});
			
			return maombi;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<OmbiLaMkopo>();
		}finally {
			pm.close();
		}
	}

	@Override
	public List<OmbiLaMkopo> tafutaOmbiLaMkopoYaliyokataliwa(Long id) {
		return tafutaOmbiLaMkopo(id).stream().filter(ombi -> Jibu.LIMEKATALIWA.equals(ombi.getJibu())).collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public List<OmbiLaMkopo> tafutaOmbiLaMkopoLililokubaliwa(Long id) {
		return tafutaOmbiLaMkopo(id).stream().filter(ombi -> Jibu.LIMEKUBALIWA.equals(ombi.getJibu())).collect(Collectors.toCollection(ArrayList::new));
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
		}finally {
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
			maafa.stream().forEach(afa -> afa.setMjinga(null));
			
			return maafa;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Maafa>();
		}finally {
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
		if (StringUtils.isEmpty(tokeni) || id == null ) {
			return false;
		}
		
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		try {
			
			Mjinga  mjinga = persistenceManager.getObjectById(Mjinga.class, id);
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

}
