package biz.picosoft.mains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.dao.ContacteDao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.Soci�t�DaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;
import biz.picosoft.services.ContacteServiceImpl;
import biz.picosoft.services.Soci�t�ServiceImpl;

public class TestDaoS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
 
		Soci�t� soci�t�=new Soci�t�("pico2", "pico@gmail.com", "74255546", "el ghazella");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Soci�t�DaoImpl soci�t�Dao=(Soci�t�DaoImpl) context.getBean("soci�t�DaoImpl");
		  ContacteDaoImpl contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");
		soci�t�Dao.insert(soci�t�);
		 Contacte contacte=new Contacte("souad", "imed@pico.biz", "7424554", "ghazella", soci�t�);
		 //Contacte contacte2=new Contacte("med", "med@pico.biz", "7424554", "ghazella", soci�t�);
		 contacteDaoImpl.insert(contacte);
		// soci�t�Dao.delete(soci�t�);
		//  Contacte c=contacteDaoImpl.findById(Contacte.class,4);
		 //contacteDaoImpl.delete(c);
		//  ContacteServiceImpl contacteServiceImpl=new ContacteServiceImpl();
		  Soci�t�ServiceImpl soci�t�ServiceImpl=new Soci�t�ServiceImpl();
		  Soci�t� soci�t�2=soci�t�Dao.findById(Soci�t�.class, 2);
		 // soci�t�ServiceImpl.delete(2);
	//	System.out.println( soci�t�ServiceImpl.findAll());
		
		// contacteServiceImpl.insert("imed", "imed@pico.biz", "7424554", "ghazella", 26);
		// contacteServiceImpl.delete(23); 
		//contacteServiceImpl.insert(contacte2);
		
	//	System.out.println("list contacts"+soci�t�Dao.findAll().get(0).getContacts().get(0).getAdresse());
				}

}