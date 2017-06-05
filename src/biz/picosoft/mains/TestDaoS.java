package biz.picosoft.mains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.dao.ContacteDao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.SociétéDaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;
import biz.picosoft.services.ContacteServiceImpl;
import biz.picosoft.services.SociétéServiceImpl;

public class TestDaoS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
 
		//Société société=new Société("pico", "pico@gmail.com", "74255546", "el ghazella");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SociétéDaoImpl sociétéDao=(SociétéDaoImpl) context.getBean("sociétéDaoImpl");
		  ContacteDaoImpl contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");
		//sociétéDao.insert(société);
		 		// contacteDaoImpl.insert(contacte);
		 ContacteServiceImpl contacteServiceImpl=new ContacteServiceImpl();
		 SociétéServiceImpl sociétéServiceImpl=new SociétéServiceImpl();
		 contacteServiceImpl.delete(4);
		 //sociétéServiceImpl.delete(1);
		//contacteServiceImpl.insert(contacte2);
		
	//	System.out.println("list contacts"+sociétéDao.findAll().get(0).getContacts().get(0).getAdresse());
				}

}
