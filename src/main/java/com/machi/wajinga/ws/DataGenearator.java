package com.machi.wajinga.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.Mjinga.Cheo;

public class DataGenearator {

	public static void generate() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Wajinga-Dev");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		List<Mjinga> wajinga = new ArrayList<Mjinga>();
		
		wajinga.add(new Mjinga("kibo", "George Kibogoyo", "george.kibogoyo@gmail.com", "password","+33652003035", "Daktari", Cheo.KATIBU, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("machi", "Manyanda Chitimbo", "manyanda.chitimbo@gmail.com", "password","+33652003035", "Software Engineer", Cheo.MJUMBE, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("songe", "Songelaeli Nsunza", "songelaeli.nsunza@gmail.com", "password","+33652003035", "Software Engineer", Cheo.MWENYEKITI, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("icmar", "Ismail Juma", "ismail.juma@gmail.com", "password","+33652003035", "Software Engineer", Cheo.MWEKAHAZINA, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("ally.busha", "Ally Masoud", "ally.masoud@gmail.com", "password","+33652003035", "IT Engineer", Cheo.MWEKAHAZINA, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("chuwa.ticha.paja", "Thomas Chuwa", "thomas.chuwa@gmail.com", "password","+33652003035", "Mhadhini wa Chuo", Cheo.MJUMBE, DateTime.now().minusYears(1)));
		wajinga.add(new Mjinga("ruvilo.mwananchi", "Denis Ruvilo", "ruvilo.denis@gmail.com", "password","+33652003035", "Banker", Cheo.MJUMBE, DateTime.now().minusYears(1)));
	
		pm.makePersistentAll(wajinga);
	
	}
}
