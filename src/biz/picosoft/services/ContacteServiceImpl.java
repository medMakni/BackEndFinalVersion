package biz.picosoft.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.dao.ContacteDao;
import biz.picosoft.dao.SociétéDao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.SociétéDaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;

public class ContacteServiceImpl implements ContactService {
	ContacteDao contacteDao;
	SociétéDao sociétéDao;

	public ContacteServiceImpl() {
		super();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.contacteDao = (ContacteDaoImpl) context.getBean("contactDaoImpl");
		this.sociétéDao = (SociétéDaoImpl) context.getBean("sociétéDaoImpl");
	}

	@Override
	public void insert(String nom, String mail, String téléphone, String adresse, int idSociété) {
		Société société=sociétéDao.findById(Société.class, idSociété);
		Contacte contacte = new Contacte(nom, mail, téléphone, adresse, société);
		contacteDao.insert(contacte);

	}

	@Override
	public void update(int id, String nom, String mail, String téléphone, String adresse, Société société) {
		Contacte contacte = new Contacte(id, nom, mail, téléphone, adresse, société);
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
		String sociétéName;
		for (int i = 0; i < contacteDao.findAll().size(); i++) {
			allContactsMap = new HashMap<String, Object>();
			sociétéName = sociétéDao.findById(Société.class, contacteDao.findAll().get(i).getSociété().getIdSociété())
					.getNom();
			allContactsMap.put("idContact", contacteDao.findAll().get(i).getIdContact());
			allContactsMap.put("nom", contacteDao.findAll().get(i).getNom());
			allContactsMap.put("mail", contacteDao.findAll().get(i).getMail());
			allContactsMap.put("téléphone", contacteDao.findAll().get(i).getTéléphone());
			allContactsMap.put("adresse", contacteDao.findAll().get(i).getAdresse());
			allContactsMap.put("Société", sociétéName);
			listOfMapOfAllContacts.add(allContactsMap);
			allContactsMap = null;
		}
		return listOfMapOfAllContacts;
	}

}
