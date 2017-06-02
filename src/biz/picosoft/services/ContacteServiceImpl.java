package biz.picosoft.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.dao.ContacteDao;
import biz.picosoft.dao.Soci�t�Dao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.Soci�t�DaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;

public class ContacteServiceImpl implements ContactService {
	ContacteDao contacteDao;
	Soci�t�Dao soci�t�Dao;

	public ContacteServiceImpl() {
		super();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.contacteDao = (ContacteDaoImpl) context.getBean("contactDaoImpl");
		this.soci�t�Dao = (Soci�t�DaoImpl) context.getBean("soci�t�DaoImpl");
	}

	@Override
	public void insert(String nom, String mail, String t�l�phone, String adresse, int idSoci�t�) {
		Soci�t� soci�t�=soci�t�Dao.findById(Soci�t�.class, idSoci�t�);
		Contacte contacte = new Contacte(nom, mail, t�l�phone, adresse, soci�t�);
		contacteDao.insert(contacte);

	}

	@Override
	public void update(int id, String nom, String mail, String t�l�phone, String adresse, Soci�t� soci�t�) {
		Contacte contacte = new Contacte(id, nom, mail, t�l�phone, adresse, soci�t�);
		contacte.setIdContact(id);
		contacteDao.update(contacte);
	}

	@Override
	public void delete(int id) {
		Contacte contacte = contacteDao.findById(Contacte.class, id);
		contacteDao.delete(contacte);
	}

	@Override
	public Contacte findById(int id) {
		// TODO Auto-generated method stub
		return contacteDao.findById(Contacte.class, id);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listOfMapOfAllContacts = new ArrayList<Map<String, Object>>();
		Map<String, Object> allContactsMap;
		String soci�t�Name;
		for (int i = 0; i < contacteDao.findAll().size(); i++) {
			allContactsMap = new HashMap<String, Object>();
			soci�t�Name = soci�t�Dao.findById(Soci�t�.class, contacteDao.findAll().get(i).getSoci�t�().getIdSoci�t�())
					.getNom();
			allContactsMap.put("idContact", contacteDao.findAll().get(i).getIdContact());
			allContactsMap.put("nom", contacteDao.findAll().get(i).getNom());
			allContactsMap.put("mail", contacteDao.findAll().get(i).getMail());
			allContactsMap.put("t�l�phone", contacteDao.findAll().get(i).getT�l�phone());
			allContactsMap.put("adresse", contacteDao.findAll().get(i).getAdresse());
			allContactsMap.put("Soci�t�", soci�t�Name);
			listOfMapOfAllContacts.add(allContactsMap);
			allContactsMap = null;
		}
		return listOfMapOfAllContacts;
	}

}
