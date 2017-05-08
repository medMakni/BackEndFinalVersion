package biz.picosoft.mains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.CourrierDaoImpl;
import biz.picosoft.daoImpl.SociétéDaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Courrier;
import biz.picosoft.entity.Société;

public class TestHibernate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		 Société société=new Société("pico","test","2121","dsf");
		 SociétéDaoImpl sociétéDaoImpl = (SociétéDaoImpl) context.getBean("sociétéDaoImpl");
			sociétéDaoImpl.insert(société);
		Contacte contacte = new Contacte("fatma", "test222222", "test", "test" ,société);
		Contacte contacte2 = new Contacte("fatma", "test222222", "test", "test" ,société);
		ContacteDaoImpl contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");
		contacteDaoImpl.insert(contacte2);
		contacteDaoImpl.insert(contacte);
		Courrier courrier = new Courrier(17, 8,contacte);
		CourrierDaoImpl courrierDaoImpl =  (CourrierDaoImpl) context.getBean("courrierDaoImpl");
		courrierDaoImpl.insert(courrier);
 
		
 
		// contacteDaoImpl.insert(contacte);

	/*	List<Société> lst = sociétéDaoImpl.findAll();
		for (Société société2 : lst) {
			 
			 société2.getContacteListe().add(contacte);
			// contacteDaoImpl.delete(contacte2);
			sociétéDaoImpl.update(société2);

		}

		
	*/	

	}

}
