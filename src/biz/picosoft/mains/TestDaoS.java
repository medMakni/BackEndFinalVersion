package biz.picosoft.mains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.dao.ContacteDao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.Soci�t�DaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;
import biz.picosoft.services.ContacteServiceImpl;

public class TestDaoS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
 
		Soci�t� soci�t�=new Soci�t�("pico", "pico@gmail.com", "74255546", "el ghazella");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Soci�t�DaoImpl soci�t�Dao=(Soci�t�DaoImpl) context.getBean("soci�t�DaoImpl");
		  ContacteDaoImpl contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");
		soci�t�Dao.insert(soci�t�);
		 Contacte contacte=new Contacte("imed", "imed@pico.biz", "7424554", "ghazella", soci�t�);
		 Contacte contacte2=new Contacte("med", "med@pico.biz", "7424554", "ghazella", soci�t�);
		// contacteDaoImpl.insert(contacte);
		 ContacteServiceImpl contacteServiceImpl=new ContacteServiceImpl();
		 
		 contacteServiceImpl.insert(contacte);
		//contacteServiceImpl.insert(contacte2);
		
	//	System.out.println("list contacts"+soci�t�Dao.findAll().get(0).getContacts().get(0).getAdresse());
				}

}
