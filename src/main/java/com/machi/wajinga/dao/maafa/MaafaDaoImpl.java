package com.machi.wajinga.dao.maafa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.wajiboost.Usanidi;

@SuppressWarnings("unchecked")
@Service
public class MaafaDaoImpl extends AbstractDaoImpl implements MaafaDao {
	public MaafaDaoImpl() {
		super();
	}

	@Override
	public List<Maafa> tafutaMaafa(MultivaluedMap<String, String> filters) {

		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(Maafa.class);
		try {
			List<String> queryParams = new ArrayList<String>();
			List<String> queryFilters = new ArrayList<String>();
			List<Object> values = new ArrayList<Object>();

			tengenezaVigezo(filters, queryParams, queryFilters, values);

			if (!filters.containsKey("msimu")) {
				try {
					String msimu = "";
					Usanidi u = persistenceManager.getObjectById(Usanidi.class, Usanidi.MSIMU);
					if (u != null && StringUtils.isNoneEmpty(u.getKilichomo())) {
						msimu = persistenceManager.detachCopy(u).getKilichomo();
					}
					queryFilters.add("msimu == \"" + msimu + "\"");
				} catch (Exception e) {
				}
			}

			query.setFilter(queryFilters.parallelStream().collect(Collectors.joining(" && ")));
			query.declareParameters(queryParams.parallelStream().collect(Collectors.joining(",")));
			query.declareImports("import " + DateTime.class.getCanonicalName());

			List<Maafa> maafa = (List<Maafa>) query.executeWithArray(values.toArray());
			maafa = (List<Maafa>) persistenceManager.detachCopyAll(maafa);

			maafa.forEach(afa -> {
				afa.setMjinga(null);
			});

			return maafa;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Maafa>();
		} finally {
			query.closeAll();
			persistenceManager.close();
		}
	}

	private void tengenezaVigezo(MultivaluedMap<String, String> filters, List<String> queryParams,
			List<String> queryFilters, List<Object> values) {
		filters.forEach((key, value) -> {
			if ("msimu".equals(key)) {
				List<String> msimuFilter = new ArrayList<String>();
				value.forEach(v -> {
					msimuFilter.add("msimu == \"" + v + "\"");
				});
				queryFilters.add(msimuFilter.stream().collect(Collectors.joining(" || ")));
			} else if ("tarehe".equals(key)) {
				DateTime yesterday = new DateTime(Long.valueOf(value.get(0))).withMillis(0);
				DateTime tomorrow = yesterday.plusDays(1);
				queryParams.add("DateTime YESTERDAY");
				queryParams.add("DateTime TOMORROW");
				values.add(yesterday);
				values.add(tomorrow);
				queryFilters.add("tarehe >= YESTERDAY && tarehe <= TOMORROW");
			} else if ("wajinga".equals(key)) {
				List<String> mjingaFilter = new ArrayList<String>();
				value.forEach(v -> {
					mjingaFilter.add("mjinga.jina == \"" + v + "\"");
				});
				queryFilters.add(mjingaFilter.stream().collect(Collectors.joining(" || ")));
			} else if ("timu".equals(key)) {
				queryFilters.add("timu == \"" + value.get(0) + "\"");
			}
		});
	}

	@Override
	public Maafa tafutaAfa(Long afaId) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		try {
			persistenceManager.getFetchPlan().addGroup("Mjinga");
			Maafa maafa = persistenceManager.getObjectById(Maafa.class, afaId);
			maafa = persistenceManager.detachCopy(maafa);

			if (maafa.getMjinga() != null) {
				maafa.setMjinga(maafa.getMjinga().wipe());
			}

			return maafa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public Boolean wekaAfa(Maafa maafa) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Transaction transaction = persistenceManager.currentTransaction();

		try {
			transaction.begin();
			persistenceManager.getFetchPlan().addGroup("Maafa");
			Mjinga mjinga = persistenceManager.getObjectById(Mjinga.class, maafa.getMjinga().getId());
			maafa.setMjinga(mjinga);
			mjinga.getMaafa().add(maafa);
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
	public Boolean futa(Long afaId, Long mjingaId) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		try {
			Maafa afa = persistenceManager.getObjectById(Maafa.class, afaId);
			persistenceManager.getFetchPlan().addGroup("Maafa");

			Mjinga mjinga = persistenceManager.getObjectById(Mjinga.class, mjingaId);

			if (mjinga != null) {
				mjinga.getMaafa().removeIf(afa_ -> afa_.getId().equals(afaId));
			}

			persistenceManager.deletePersistent(afa);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			persistenceManager.close();
		}
	}
}
