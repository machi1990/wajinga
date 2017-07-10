package com.machi.wajinga.dao.mkopo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.ws.rs.core.MultivaluedMap;

import org.joda.time.DateTime;
import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.AbstractDaoImpl;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo.Jibu;

@Service
@SuppressWarnings("unchecked")
public class MkopoDaoImpl extends AbstractDaoImpl implements MkopoDao {

	public MkopoDaoImpl() {
		super();
	}

	@Override
	public List<Mkopo> tafutaMikopo(MultivaluedMap<String, String> filters) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(Mkopo.class);

		try {
			persistenceManager.getFetchPlan().addGroup("MaelezoYaMjinga");
			List<Object> values = new ArrayList<Object>();
			List<String> queryFilters = new ArrayList<String>();
			List<String> queryParams = new ArrayList<String>();

			filters.forEach((key, value) -> {
				if ("kiasi_kdg".equals(key)) {
					queryParams.add("String kiasi_kdg");
					queryFilters.add("kiasi >= kiasi_kdg");
					values.add(Long.valueOf(value.get(0)));
				} else if ("kiasi_kbw".equals(key)) {
					queryParams.add("String kiasi_kbw");
					queryFilters.add("kiasi <= kiasi_kbw");
					values.add(Long.valueOf(value.get(0)));
				} else if ("tarehe_mwz".equals(key)) {
					queryParams.add("DateTime tarehe_mwz");
					queryFilters.add("tarehe >= tarehe_mwz");
					values.add(new DateTime(Long.valueOf(value.get(0))));
				} else if ("tarehe_msh".equals(key)) {
					queryParams.add("DateTime tarehe_msh");
					queryFilters.add("tarehe <= tarehe_msh");
					values.add(new DateTime(Long.valueOf(value.get(0))));
				} else if ("mjinga".equals(key)) {
					List<String> mjingaFilter = new ArrayList<String>();
					value.forEach(v -> {
						mjingaFilter.add("mjinga.jina == \"" + v + "\"");
					});
					queryFilters.add(mjingaFilter.stream().collect(Collectors.joining(" || ")));
				} else if ("signatori".equals(key)) {
					List<String> mjingaFilter = new ArrayList<String>();
					value.forEach(v -> {
						mjingaFilter.add("signatori.jina == \"" + v + "\"");
					});
					queryFilters.add(mjingaFilter.stream().collect(Collectors.joining(" || ")));
				}
			});

			Boolean umepitilizwa = filters.containsKey("umepitilizwa");

			if (umepitilizwa) {
				persistenceManager.getFetchPlan().addGroup("MaelezoMkopo");
			}

			query.declareImports("import " + DateTime.class.getCanonicalName());
			query.setFilter(queryFilters.stream().collect(Collectors.joining(" && ")));
			query.declareParameters(queryParams.stream().collect(Collectors.joining(" , ")));
			List<Mkopo> mikopo = (List<Mkopo>) query.executeWithArray(values.toArray());
			mikopo = (List<Mkopo>) persistenceManager.detachCopyAll(mikopo);

			Stream<Mkopo> filter = umepitilizwa ? mikopo.stream().filter(Mkopo::isUmepitilizwa) : mikopo.stream();

			filter.forEach(mkopo -> {
				mkopo.setMarejesho(null);
				mkopo.setOmbi(null);
				mkopo.setMkopaji(mkopo.getMkopaji().wipe());
				mkopo.setSignatori(null);
			});

			return mikopo;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Mkopo>();
		} finally {
			query.closeAll();
			persistenceManager.close();
		}
	}

	@Override
	public List<OmbiLaMkopo> tafutaOmbiLaMkopo(MultivaluedMap<String, String> filters) {
		PersistenceManager persistenceManager = getPmf().getPersistenceManager();
		Query query = persistenceManager.newQuery(OmbiLaMkopo.class);

		try {
			List<Object> values = new ArrayList<Object>();
			List<String> queryFilters = new ArrayList<String>();
			List<String> queryParams = new ArrayList<String>();

			filters.forEach((key, value) -> {
				if ("jibu".equals(key)) {
					queryParams.add("Jibu JIBU");
					queryFilters.add("jibu == JIBU");
					if (Boolean.valueOf(value.get(0))) {
						values.add(Jibu.LIMEKUBALIWA);
					} else {
						values.add(Jibu.LIMEKATALIWA);
					}
				} else if ("mjinga".equals(key)) {
					List<String> mjingaFilter = new ArrayList<String>();
					value.forEach(v -> {
						mjingaFilter.add("mjinga.jina == \"" + v + "\"");
					});
					queryFilters.add(mjingaFilter.stream().collect(Collectors.joining(" || ")));
				} else if ("mjibuji".equals(key)) {
					List<String> mjingaFilter = new ArrayList<String>();
					value.forEach(v -> {
						mjingaFilter.add("mjibuji.jina == \"" + v + "\"");
					});
					queryFilters.add(mjingaFilter.stream().collect(Collectors.joining(" || ")));
				} else if ("kiasi".equals(key)) {
				   queryParams.add("Long KIASI");
				   queryFilters.add("kiasi <= KIASI");
				   values.add(Long.valueOf(value.get(0)));
				}  else if ("kiasi_jibu".equals(key)) {
				   queryParams.add("Long KIASI_JIBU");
				   queryFilters.add("kiasiKilichokubaliwa <= KIASI_JIBU");
				   values.add(Long.valueOf(value.get(0)));
				} else if ("tarehe".equals(key)) {
					DateTime tarehe = new DateTime(Long.valueOf(value.get(0)));
					queryParams.add("DateTime TAREHE");
					queryFilters.add("tarehe <= TAREHE");
					values.add(tarehe);
				} else if ("trh_majibu".equals(key)) {
					DateTime tarehe = new DateTime(Long.valueOf(value.get(0)));
					queryParams.add("DateTime TRH_MAJIBU");
					queryFilters.add("tareheYaMajibu <= TRH_MAJIBU");
					values.add(tarehe);
				} else if ("maelezo".equals(key)) {
					queryParams.add("String MAELEZO");
					values.add("(.*)" + value.get(0) + "(.*)");
					queryFilters.add("maelezo.matches(MAELEZO)");
				} else if ("maelezo_majibu".equals(key)) {
					queryParams.add("String MAELEZO_MAJIBU");
					queryFilters.add("maelezoYaJibu.matches(MAELEZO_MAJIBU)");
					values.add("(.*)" + value.get(0) + "(.*)");
				}
			});

			query.declareImports("import " + OmbiLaMkopo.Jibu.class.getSimpleName() + " ; import "
					+ DateTime.class.getCanonicalName());
			query.setFilter(queryFilters.stream().collect(Collectors.joining(" && ")));
			query.declareParameters(queryParams.stream().collect(Collectors.joining(" , ")));
			
			List<OmbiLaMkopo> maombi = (List<OmbiLaMkopo>) query.executeWithArray(values.toArray());
			maombi = (List<OmbiLaMkopo>) persistenceManager.detachCopyAll(maombi);
			
			maombi.forEach(ombi -> {
				ombi.setMjinga(ombi.getMjinga().wipe());
				if (ombi.getMjibuji() != null ) {
					ombi.setMjibuji( ombi.getMjibuji().wipe());
				}
			});
			
			return maombi;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<OmbiLaMkopo>();
		} finally {
			query.closeAll();
			persistenceManager.close();
		}
	}

}
