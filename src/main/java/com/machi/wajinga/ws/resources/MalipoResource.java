package com.machi.wajinga.ws.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.NotFoundException;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.malipo.MalipoYaMwezi;
import com.machi.wajinga.dao.malipo.MalipoYaMweziDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Path("malipo")
public class MalipoResource {
	private static final DateTimeFormatter mweziFormat = DateTimeFormat.forPattern("MM/yyyy");
	private static final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

	private MalipoYaMweziDao malipoDao;
	private MjingaDao mjingaDao;

	@Inject
	public MalipoResource(WajingaDao wajingaDao) {
		super();
		malipoDao = wajingaDao.getMalipoDao();
		mjingaDao = wajingaDao.getMjingaDao();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MalipoYaMwezi> malipo(@Context UriInfo info) {
		List<MalipoYaMwezi> malipo = malipoDao.tafutaMalipo(info.getQueryParameters());
		return malipo.parallelStream().sorted()
				.collect(Collectors.mapping(MalipoYaMwezi::wipeMjinga, Collectors.toList()));
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("csv")
	public Response malipoKatikaCSV(@Context UriInfo info) {
		final List<MalipoKontena> malipoKontena = malipoDao.tafutaMalipo(info.getQueryParameters()).parallelStream()
				.sorted().collect(Collectors.mapping(MalipoKontena::getMalipo, Collectors.toList()));
		final Long total = malipoKontena.stream().mapToLong(MalipoKontena::kiasi).sum();
		final MalipoKontena jumla = new MalipoKontena(null, "JUMLA =" + total, null, null);
		malipoKontena.add(jumla);

		StreamingOutput output = new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				Writer writer = new PrintWriter(output);
				StatefulBeanToCsv<MalipoKontena> malipoToCsv = new StatefulBeanToCsvBuilder<MalipoKontena>(writer)
						.build();
				try {
					malipoToCsv.write(malipoKontena);
				} catch (CsvDataTypeMismatchException e) {
					e.printStackTrace();
				} catch (CsvRequiredFieldEmptyException e) {
					e.printStackTrace();
				}
				writer.close();
				output.close();
			}
		};

		CacheControl cache = new CacheControl();
		cache.setMaxAge(600000);
		cache.setMustRevalidate(false);
		return Response.ok(output).header("Content-Disposition", "attachment; filename=malipo.csv").cacheControl(cache)
				.build();
	}

	@SuppressWarnings("unchecked")
	@POST
	@RolesAllowed({ "MWENYEKITI", "KATIBU", "MWEKAHAZINA" })
	@Path("csv")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response malipoKatikaCSV(String csv) {
		Reader reader = new StringReader(csv);

		List<MalipoKontenaInput> malipoKontena = new CsvToBeanBuilder<MalipoKontenaInput>(reader)
				.withType(MalipoKontenaInput.class).build().parse(), majibu = new ArrayList<MalipoKontenaInput>();

		List<MalipoYaMwezi> malipo = new ArrayList<MalipoYaMwezi>();
		malipoKontena.forEach(kontena -> {
			Mjinga mjinga = null;

			if (kontena.getMJINGA_MLIPAJI() != null) {
				mjinga = mjingaDao.tafutaMjingaKwaJina(kontena.getMJINGA_MLIPAJI().trim());
			}

			if (mjinga == null && kontena.getBARUA_PEPE() != null) {
				mjinga = mjingaDao.tafutaMjingaKwaBaruaPepe(kontena.getBARUA_PEPE().trim());
			}

			if (mjinga == null) {
				kontena.setSABABU("Weka taarifa sahihi za mlipaji");
				majibu.add(kontena);
				return;
			}

			MalipoYaMwezi lipo = new MalipoYaMwezi(mjinga, kontena.getKIASI_KILICHOLIPWA(),
					DateTime.parse(kontena.getTAREHE_YA_MALIPO().trim()),
					DateTime.parse(kontena.getMWEZI_HUSIKA().trim()), kontena.getMAELEZO());

			malipo.add(lipo);

		});

		malipoDao.tunza(malipo);

		StreamingOutput output = new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				Writer writer = new PrintWriter(output);
				StatefulBeanToCsv<MalipoKontenaInput> malipoToCsv = new StatefulBeanToCsvBuilder<MalipoKontenaInput>(
						writer).build();
				try {
					malipoToCsv.write(majibu);
				} catch (CsvDataTypeMismatchException e) {
					e.printStackTrace();
				} catch (CsvRequiredFieldEmptyException e) {
					e.printStackTrace();
				}
				writer.close();
				output.close();
			}
		};

		return Response.status(Status.PRECONDITION_FAILED).entity(output)
				.header("Content-Disposition", "attachment; filename=hajakubaliwa.csv").build();
	}

	@DELETE
	@RolesAllowed({ "MWEKAHAZINA" })
	@Path("{lipo-id: \\d+} / {mjinga-id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response futaMalipo(@PathParam("lipo-id") Long lipoId, @PathParam("mjinga-id") Long mjingaId) {
		Boolean limefutwa = malipoDao.futaLipo(lipoId, mjingaId);
		return limefutwa ? Response.ok("Lipo limefutwa").build()
				: Response.status(Status.BAD_REQUEST)
						.entity("Tatizo limetokea, ombi lako la kufuta lipo halijatekelezwa").build();
	}

	@GET
	@Path("{lipo-id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public MalipoYaMwezi tafutaTaarifaZaLipo(@PathParam("lipo-id") Long lipoId) {
		MalipoYaMwezi lipo = malipoDao.tafutaLipo(lipoId);

		if (lipo == null) {
			throw new NotFoundException("Hakuna malipo ya namna hiyo");
		}

		return lipo.wipeMjinga(false);
	}

	@POST
	@RolesAllowed({ "KATIBU", "MWEKAHAZINA", "MWENYEKITI" })
	@Path("mjinga/{mjinga-id: \\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response lipa(@PathParam("mjinga-id") Long mlipaji, MalipoYaMwezi lipo) {
		if (lipo.getMweziHusika() == null) {
			throw new BadRequestException(" Mwezi wa malipo unatakiwa");
		}

		if (lipo.getTarehe() == null) {
			lipo.setTarehe(DateTime.now());
		}

		if (lipo.getMaelezo() == null) {
			lipo.setMaelezo("Malipo ya mwezi " + mweziFormat.print(lipo.getMweziHusika()));
		}

		lipo.setId(null);
		Mjinga mjinga = new Mjinga();
		mjinga.setId(mlipaji);
		lipo.setMjinga(mjinga);

		return malipoDao.lipa(lipo) ? Response.ok("Malipo yamefanyika").build()
				: Response.status(Status.BAD_REQUEST).entity("Malipo hayajafanyika").build();

	}

	public static class MalipoKontena {
		@CsvBindByName(column = "MJINGA MLIPAJI")
		public final String MJINGA_MLIPAJI;

		@CsvBindByName(column = "KIASI KILICHOLIPWA")
		public final Object KIASI_KILICHOLIPWA;

		@CsvBindByName(column = "TAREHE YA MALIPO")
		public final String TAREHE_YA_MALIPO; // ISO format yyyy-mm-dd

		@CsvBindByName(column = "MWEZI HUSIKA")
		public final String MWEZI_HUSIKA; // ISO format yyyy-mm-dd

		private MalipoKontena(String mjinga, Object kiasi, DateTime tarehe, DateTime mwezi) {
			super();
			this.MJINGA_MLIPAJI = mjinga;
			this.KIASI_KILICHOLIPWA = kiasi;
			this.TAREHE_YA_MALIPO = tarehe != null ? format.print(tarehe) : null;
			this.MWEZI_HUSIKA = mwezi != null ? mweziFormat.print(mwezi) : null;
		}

		public static MalipoKontena getMalipo(MalipoYaMwezi lipo) {
			return new MalipoKontena(lipo.getMjinga().getJinaLaUkoo(), lipo.getKiasi(), lipo.getTarehe(),
					lipo.getMweziHusika());
		}

		public String getMJINGA_MLIPAJI() {
			return MJINGA_MLIPAJI;
		}

		private Long kiasi() {
			return (Long) KIASI_KILICHOLIPWA;
		}

		public Object getKIASI_KILICHOLIPWA() {
			return KIASI_KILICHOLIPWA;
		}

		public String getTAREHE_YA_MALIPO() {
			return TAREHE_YA_MALIPO;
		}

		public String getMWEZI_HUSIKA() {
			return MWEZI_HUSIKA;
		}

	}

	public static class MalipoKontenaInput {
		@CsvBindByName(column = "MJINGA MLIPAJI")
		public String MJINGA_MLIPAJI;

		@CsvBindByName(column = "BARUA PEPE")
		public String BARUA_PEPE;

		@CsvBindByName(column = "KIASI KILICHOLIPWA")
		public Long KIASI_KILICHOLIPWA;

		@CsvBindByName(column = "TAREHE YA MALIPO")
		public String TAREHE_YA_MALIPO; // ISO format yyyy-mm-dd

		@CsvBindByName(column = "MWEZI HUSIKA")
		public String MWEZI_HUSIKA; // ISO format yyyy-mm-dd

		@CsvBindByName(column = "SABABU")
		public String SABABU;

		@CsvBindByName(column = "MAELEZO")
		public String MAELEZO = "";

		public MalipoKontenaInput(String mJINGA_MLIPAJI, String bARUA_PEPE, Long kIASI_KILICHOLIPWA,
				String tAREHE_YA_MALIPO, String mWEZI_HUSIKA, String sABABU, String mAELEZO) {
			super();
			MJINGA_MLIPAJI = mJINGA_MLIPAJI;
			BARUA_PEPE = bARUA_PEPE;
			KIASI_KILICHOLIPWA = kIASI_KILICHOLIPWA;
			TAREHE_YA_MALIPO = tAREHE_YA_MALIPO;
			MWEZI_HUSIKA = mWEZI_HUSIKA;
			SABABU = sABABU;
			MAELEZO = mAELEZO;
		}

		public String getMAELEZO() {
			return MAELEZO;
		}

		public void setMAELEZO(String mAELEZO) {
			if (mAELEZO == null) {
				mAELEZO = "";
			}

			MAELEZO = mAELEZO;
		}

		public MalipoKontenaInput() {
			super();
		}

		public void setMJINGA_MLIPAJI(String mJINGA_MLIPAJI) {
			MJINGA_MLIPAJI = mJINGA_MLIPAJI;
		}

		public void setBARUA_PEPE(String bARUA_PEPE) {
			BARUA_PEPE = bARUA_PEPE;
		}

		public void setKIASI_KILICHOLIPWA(Long kIASI_KILICHOLIPWA) {
			KIASI_KILICHOLIPWA = kIASI_KILICHOLIPWA;
		}

		public void setTAREHE_YA_MALIPO(String tAREHE_YA_MALIPO) {
			TAREHE_YA_MALIPO = tAREHE_YA_MALIPO;
		}

		public void setMWEZI_HUSIKA(String mWEZI_HUSIKA) {
			MWEZI_HUSIKA = mWEZI_HUSIKA;
		}

		public String getMJINGA_MLIPAJI() {
			return MJINGA_MLIPAJI;
		}

		public String getBARUA_PEPE() {
			return BARUA_PEPE;
		}

		public Long getKIASI_KILICHOLIPWA() {
			return KIASI_KILICHOLIPWA;
		}

		public String getTAREHE_YA_MALIPO() {
			return TAREHE_YA_MALIPO;
		}

		public String getMWEZI_HUSIKA() {
			return MWEZI_HUSIKA;
		}

		public String getSABABU() {
			return SABABU;
		}

		public void setSABABU(String sABABU) {
			SABABU = sABABU;
		}

		@Override
		public String toString() {
			return "MalipoKontenaInput [MJINGA_MLIPAJI=" + MJINGA_MLIPAJI + ", BARUA_PEPE=" + BARUA_PEPE
					+ ", KIASI_KILICHOLIPWA=" + KIASI_KILICHOLIPWA + ", TAREHE_YA_MALIPO=" + TAREHE_YA_MALIPO
					+ ", MWEZI_HUSIKA=" + MWEZI_HUSIKA + ", SABABU=" + SABABU + ", MAELEZO=" + MAELEZO + "]";
		}

	}

}
