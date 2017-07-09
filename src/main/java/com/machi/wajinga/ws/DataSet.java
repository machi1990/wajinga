package com.machi.wajinga.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.maafa.Maafa.Zawadi;
import com.machi.wajinga.dao.malipo.MalipoYaMwezi;
import com.machi.wajinga.dao.mjinga.Mchambo;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.Mjinga.Cheo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;
import com.machi.wajinga.dao.wajiboost.Katiba;
import com.machi.wajinga.dao.wajiboost.KipengeleChaKatiba;
import com.machi.wajinga.dao.wajiboost.Lengo;
import com.machi.wajinga.dao.wajiboost.Lengo.Hali;
import com.machi.wajinga.dao.wajiboost.Lengo.Historia;
import com.machi.wajinga.dao.wajiboost.Tukio;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.Tukio.Aina;
import com.machi.wajinga.dao.wajiboost.Tukio.Oni;

public class DataSet {

	public static void generate(String source) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(source);
		PersistenceManager pm = pmf.getPersistenceManager();

		List<Mjinga> wajinga = new ArrayList<Mjinga>();
		wajinga.add(new Mjinga("kibo", "George Kibogoyo", "george.kibogoyo@gmail.com", "password", "+33652003035",
				"Daktari", Cheo.KATIBU, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("machi", "Manyanda Chitimbo", "manyanda.chitimbo@gmail.com", "password", "+33652003035",
				"Software Engineer", Cheo.MJUMBE, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("songe", "Songelaeli Nsunza", "songelaeli.nsunza@gmail.com", "password", "+33652003035",
				"Software Engineer", Cheo.MWENYEKITI, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("icmar", "Ismail Juma", "ismail.juma@gmail.com", "password", "+33652003035",
				"Software Engineer", Cheo.MWEKAHAZINA, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("ally.busha", "Ally Masoud", "ally.masoud@gmail.com", "password", "+33652003035",
				"IT Engineer", Cheo.MWEKAHAZINA, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("chuwa.ticha.paja", "Thomas Chuwa", "thomas.chuwa@gmail.com", "password", "+33652003035",
				"Mhadhini wa Chuo", Cheo.MJUMBE, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("ruvilo.mwananchi", "Denis Ruvilo", "ruvilo.denis@gmail.com", "password", "+33652003035",
				"Banker", Cheo.MJUMBE, DateTime.now().minusYears(1)));

		wajinga.add(new Mjinga("pastor.mjeda", "Kleberson Mhere", "kleberson@gmail.com", "password", "+33652003035",
				"Mjeda", Cheo.PASTOR, DateTime.now().minusYears(1)));

		wajinga.add(new Mjinga("lipuli", "Sir Mboka", "lipuli@gmail.com", "password", "+33652003035", "Banker",
				Cheo.PASTOR, DateTime.now().minusYears(1)));

		Maafa afa = new Maafa("Buffalo", 1l, 3, "2016/2017", 100000l, wajinga.get(3));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa tatu"));
		afa.getZawadi().add(new Zawadi(3, 50000l, Maafa.Aina.JUMLA, "Mshindi wa jumla nafasi tatu"));
		wajinga.get(3).getMaafa().add(afa);
		pm.makePersistent(afa);

		afa = new Maafa("Kibo", 2l, 8, "2016/2017", 100000l, wajinga.get(0));
		wajinga.get(0).getMaafa().add(afa);
		pm.makePersistent(afa);

		afa = new Maafa("Tchwaa FC", 3l, 4, "2016/2017", 100000l, wajinga.get(5));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa kumi"));
		wajinga.get(5).getMaafa().add(afa);
		pm.makePersistent(afa);

		afa = new Maafa("El Nino Maravilla", 1l, 1, "2016/2017", 100000l, wajinga.get(1));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa tisa"));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa tano"));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa desemba"));
		afa.getZawadi().add(new Zawadi(1, 250000l, Maafa.Aina.JUMLA, "Mshindi wa jumla nafasi ya kwanza"));

		wajinga.get(1).getMaafa().add(afa);
		pm.makePersistent(afa);

		afa = new Maafa("Pochettino", 2l, 1, "2016/2017", 100000l, wajinga.get(2));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa kwanza"));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa pili"));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi wa nne"));
		afa.getZawadi().add(new Zawadi(1, 100000l, Maafa.Aina.JUMLA, "Mshindi wa jumla nafasi ya pili"));
		wajinga.get(2).getMaafa().add(afa);
		pm.makePersistent(afa);

		afa = new Maafa("Born Town", 7l, 7, "2016/2017", 100000l, wajinga.get(4));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi novemba"));

		afa.setMjinga(wajinga.get(4));
		wajinga.get(4).getMaafa().add(afa);

		pm.makePersistent(afa);
		pm.makePersistentAll(wajinga);

		afa = new Maafa("Kleberson FC", 5l, 5, "2016/2017", 100000l, wajinga.get(7));
		afa.getZawadi().add(new Zawadi(1, 40000l, Maafa.Aina.MWEZI, "Mshindi wa mwezi nane"));
		wajinga.get(7).getMaafa().add(afa);
		pm.makePersistent(afa);

		Mchambo mchambo = new Mchambo("Busha bashite");
		pm.makePersistent(mchambo);
		wajinga.get(3).getMichambo().add(mchambo);

		mchambo = new Mchambo("Oiler mchambo");
		pm.makePersistent(mchambo);
		wajinga.get(0).getMichambo().add(mchambo);

		mchambo = new Mchambo("Mkubwa busha");
		pm.makePersistent(mchambo);
		wajinga.get(4).getMichambo().add(mchambo);

		mchambo = new Mchambo("Luuuuukaaaakuuuuuu");
		pm.makePersistent(mchambo);
		wajinga.get(5).getMichambo().add(mchambo);

		mchambo = new Mchambo("Kadiporiii");
		wajinga.get(1).getMichambo().add(mchambo);
		wajinga.get(2).getMichambo().add(mchambo);
		pm.makePersistentAll(wajinga);

		List<MalipoYaMwezi> malipo = new ArrayList<MalipoYaMwezi>();
		wajinga.forEach(mjinga -> {
			IntStream.range(1, 7).forEach(i -> {
				MalipoYaMwezi lipo = new MalipoYaMwezi(mjinga, 50000l, DateTime.now(), DateTime.now().minusMonths(i),
						"Maelezo " + mjinga.getJina() + " " + i);
				mjinga.getMalipo().add(lipo);
				malipo.add(lipo);
			});
		});

		pm.makePersistentAll(wajinga);
		pm.makePersistentAll(malipo);

		OmbiLaMkopo ombi = new OmbiLaMkopo(wajinga.get(1), DateTime.now(), "Naombeni mkopo mke wangu kajifungua", null,
				"", null);
		wajinga.get(1).getOmbiMkopo().add(ombi);
		pm.makePersistent(wajinga.get(1));
		OmbiLaMkopo ombi2 = new OmbiLaMkopo(wajinga.get(1), DateTime.now(), "Naombeni mkopo mke wangu kajifungua 2",
				null, "", null);
		wajinga.get(1).getOmbiMkopo().add(ombi2);
		pm.makePersistent(wajinga.get(1));

		List<Usanidi> visanidi = new ArrayList<Usanidi>();
		visanidi.add(new Usanidi(Usanidi.MSIMU, "2016/2017"));
		visanidi.add(new Usanidi(Usanidi.SMTP_HOST, "smtp.gmail.com"));
		visanidi.add(new Usanidi(Usanidi.SMTP_PORT, "587"));
		visanidi.add(new Usanidi(Usanidi.SMTP_JINA, "wajiboost.dev@gmail.com"));
		visanidi.add(new Usanidi(Usanidi.SMTP_NYWIRA, "Wajiboost-dev@2017"));
		visanidi.add(new Usanidi(Usanidi.SEVA, "http://localhost:8080"));
		visanidi.add(new Usanidi(Usanidi.BADILI_NYWIRA, "RE: OMBI LA KUBADILI NYWIRA"));
		visanidi.add(new Usanidi(Usanidi.BADILI_NYWIRA_MESEJI,
				"<div>Ombi lako la kubadili nywira lipekolewa. <br> Tembelea <a href=\"%s\"> Ukurasa wetu </a> uweze badili nywira yako. <br> <strong>Angalizo. Fanya haraka, meseji hii itakwisha muda wake ndani ya siku 2. <strong></div>"));
		visanidi.add(new Usanidi(Usanidi.JIBU_BARUA_PEPE_KWA, "wajiboost@gmail.com"));
		visanidi.add(new Usanidi(Usanidi.AKAUNTI_KUFUNGWA, "AKAUNTI YAKO IMEFUNGWA"));
		visanidi.add(new Usanidi(Usanidi.AKAUNTI_KUFUNGWA_MESEJI,
				"<div><strong>%s</strong> Akaunti yako ya Wajinga imefungwa kuanzia leo tarehe <strong>%s</strong>. Wasiliana na Uongozi wa Wajinga kwa taarifa zaidi</div>"));
		visanidi.add(new Usanidi(Usanidi.AKAUNTI_KUFUNGULIWA, "AKAUNTI YAKO IMEFUNGULIWA"));
		visanidi.add(new Usanidi(Usanidi.AKAUNTI_KUFUNGULIWA_MESEJI,
				"<div><strong>%s</strong> Akaunti yako ya Wajinga ipo hewani kuanzia leo tarehe <strong>%s</strong>. Tumia nywra hii <strong>%</strong> kuibadilisha. Wasiliana na Uongozi wa Wajinga kwa taarifa zaidi</div>"));

		visanidi.add(new Usanidi(Usanidi.MAAFA, "MAAFA YAKO YAMEKUBALIWA"));
		visanidi.add(new Usanidi(Usanidi.MAAFA_MESEJI,
				"<div> <strong>%s</strong> maafa yako ya msimu <strong>%s</strong> yamepokelewa leo tarehe <strong>%s</strong> na <strong>%s</strong>. Tunashukuru kwa kuchangia maafa. Anza kuchamba</div>"));

		pm.makePersistentAll(visanidi);

		Tukio tukio = new Tukio(Aina.TAFRIJA, new ArrayList<Oni>(), "Mbuzi John",
				"Mbuzi John Day kuaga na kukaribisha mwaka", "Ilipendeza tufanye tena", "Dar Es Salaam, Mariat Hotel",
				DateTime.now().minusMonths(6).minusDays(15), wajinga.get(0), DateTime.now().minusMonths(8));
		tukio.getWashiriki().addAll(wajinga);
		pm.makePersistent(tukio);

		List<Lengo> malengo = new ArrayList<Lengo>();
		Historia pendokezo = new Historia(Hali.PENDEKEZO, "Napendekeza tuongeze siku za mbuzi john", DateTime.now(),
				"machi");
		List<Historia> historia = new ArrayList<Historia>();
		historia.add(pendokezo);
		malengo.add(new Lengo("Halijatimia", pendokezo, historia));

		List<Mjinga> signatori = new ArrayList<Mjinga>();
		signatori.add(wajinga.get(0));
		signatori.add(wajinga.get(4));
		signatori.add(wajinga.get(2));

		Katiba katiba = new Katiba(new ArrayList<>(), signatori, DateTime.now().minusMonths(7), 50000l, 50000l, true);
		List<KipengeleChaKatiba> vipengeleVyaKatiba = new ArrayList<KipengeleChaKatiba>();

		IntStream.range(0, 4)
				.forEach(i -> vipengeleVyaKatiba.add(new KipengeleChaKatiba("Kichwa - " + i, "Kichwa cha katiba")));

		katiba.getVipengele().addAll(vipengeleVyaKatiba);
		pm.makePersistent(katiba);

		afa = new Maafa("Tekkers+++", 1l, 1, "2017/2018", 200000l, wajinga.get(1));
		wajinga.get(1).getMaafa().add(afa);
		pm.makePersistentAll(wajinga);
	}
}
