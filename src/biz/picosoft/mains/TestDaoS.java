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
 
		Société société=new Société("pico2", "pico@gmail.com", "74255546", "el ghazella");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SociétéDaoImpl sociétéDao=(SociétéDaoImpl) context.getBean("sociétéDaoImpl");
		  ContacteDaoImpl contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");
		sociétéDao.insert(société);
		 Contacte contacte=new Contacte("souad", "imed@pico.biz", "7424554", "ghazella", société);
		 //Contacte contacte2=new Contacte("med", "med@pico.biz", "7424554", "ghazella", société);
		 contacteDaoImpl.insert(contacte);
		// sociétéDao.delete(société);
		//  Contacte c=contacteDaoImpl.findById(Contacte.class,4);
		 //contacteDaoImpl.delete(c);
		//  ContacteServiceImpl contacteServiceImpl=new ContacteServiceImpl();
		  SociétéServiceImpl sociétéServiceImpl=new SociétéServiceImpl();
		  Société société2=sociétéDao.findById(Société.class, 2);
		 // sociétéServiceImpl.delete(2);
	//	System.out.println( sociétéServiceImpl.findAll());
		
		// contacteServiceImpl.insert("imed", "imed@pico.biz", "7424554", "ghazella", 26);
		// contacteServiceImpl.delete(23); 
		//contacteServiceImpl.insert(contacte2);
		
	//	System.out.println("list contacts"+sociétéDao.findAll().get(0).getContacts().get(0).getAdresse());
				}

}