package biz.picosoft.mains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.CourrierDaoImpl;
import biz.picosoft.daoImpl.Soci�t�DaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Courrier;
import biz.picosoft.entity.Soci�t�;

public class TestHibernate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		 Soci�t� soci�t�=new Soci�t�("pico","test","2121","dsf");
		 
		Contacte contacte = new Contacte("fatma", "test222222", "test", "test" ,soci�t�);
	 
		Courrier courrier = new Courrier(17, 8,contacte);
		CourrierDaoImpl courrierDaoImpl =  (CourrierDaoImpl) context.getBean("courrierDaoImpl");
		courrierDaoImpl.insert(courrier);
 
		
 
		// contacteDaoImpl.insert(contacte);

	/*	List<Soci�t�> lst = soci�t�DaoImpl.findAll();
		for (Soci�t� soci�t�2 : lst) {
			 
			 soci�t�2.getContacteListe().add(contacte);
			// contacteDaoImpl.delete(contacte2);
			soci�t�DaoImpl.update(soci�t�2);

		}

		
	*/	

	}

}
