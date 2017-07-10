package com.machi.wajinga.dao.malipo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.MultivaluedMap;

import org.joda.time.DateTime;
import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;
import com.machi.wajinga.dao.mjinga.Mjinga;

@Service
public class MalipoYaMweziDaoImpl extends AbstractDaoImpl implements MalipoYaMweziDao {
	public MalipoYaMweziDaoImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MalipoYaMwezi> tafutaMalipo(MultivaluedMap<String, String> filters) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(MalipoYaMwezi.class);
		try {
			List<String> queryParams = new ArrayList<String>();
			List<String> queryFilters = new ArrayList<String>();
			List<Object> values = new ArrayList<Object>();

			filters.forEach((key, value) -> {
				if ("tarehe".equals(key)) {
					queryParams.add("DateTime TAREHE");
					queryFilters.add("tarehe == TAREHE");
					DateTime tarehe = new DateTime(Long.valueOf(value.get(0))).withMillisOfDay(0);
					values.add(tarehe);
				} else if ("mwezi".equals(key)) {
					DateTime mwezi = DateTime.parse(value.get(0)).withDayOfMonth(1).withMillisOfDay(0);
					values.add(mwezi);
					queryParams.add("DateTime MWEZI");
					queryFilters.add("MWEZI == mweziHusika");
				} else if ("wajinga".equals(key)) {
					List<String> mjingaFilter = new ArrayList<String>();
					value.forEach(v -> {
						mjingaFilter.add("mjinga.jina == \"" + v + "\"");
					});
					queryFilters.add(mjingaFilter.stream().collect(Collectors.joining(" || ")));
				} else if ("maelezo".equals(key)) {
					queryParams.add("String ELEZO");
					queryFilters.add("maelezo.matches(ELEZO)");
					values.add("(.*)" + value.get(0) + "(.*)");
				}
			});

			query.setFilter(queryFilters.parallelStream().collect(Collectors.joining(" && ")));
			query.declareParameters(queryParams.parallelStream().collect(Collectors.joining(",")));
			query.declareImports("import " + DateTime.class.getCanonicalName());
			List<MalipoYaMwezi> malipo = (List<MalipoYaMwezi>) query.executeWithArray(values.toArray());
			
			return (List<MalipoYaMwezi>) persistenceManager.detachCopyAll(malipo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<MalipoYaMwezi>();
		} finally {
			query.closeAll();
			persistenceManager.close();
		}
	}

	@Override
	public MalipoYaMwezi tafutaLipo(Long lipoId) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		try {
			MalipoYaMwezi malipoYaMwezi = persistenceManager.getObjectById(MalipoYaMwezi.class, lipoId);
			return persistenceManager.detachCopy(malipoYaMwezi).wipeMjinga(false);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Boolean futaLipo(Long lipoId, Long mjingaId) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		try {
			MalipoYaMwezi malipoYaMwezi = persistenceManager.getObjectById(MalipoYaMwezi.class, lipoId);
			persistenceManager.getFetchPlan().addGroup("Malipo");

			Mjinga mjinga = persistenceManager.getObjectById(Mjinga.class, mjingaId);

			if (mjinga != null) {
				mjinga.getMalipo().removeIf(lipo -> lipo.getId().equals(lipoId));
			}

			persistenceManager.deletePersistent(malipoYaMwezi);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Boolean tunza(List<MalipoYaMwezi> malipo) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.begin();
			malipo.forEach(lipo -> {
				persistenceManager.getFetchPlan().addGroup("Malipo");
				Mjinga mjinga = persistenceManager.getObjectById(Mjinga.class, lipo.getMjinga().getId());
				lipo.setMjinga(mjinga);
				mjinga.getMalipo().add(lipo);
			});
			persistenceManager.makePersistentAll(malipo);
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
	public Boolean lipa(MalipoYaMwezi lipo) {
		return tunza(Arrays.asList(lipo));
	}
}
