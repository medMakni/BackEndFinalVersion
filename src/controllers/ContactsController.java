package controllers;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;
import biz.picosoft.services.ContacteServiceImpl;

public class ContactsController {
	ContacteServiceImpl contacteServiceImpl;

	void insert(String nom, String mail, String téléphone, String adresse, int idSociété) {
		contacteServiceImpl.insert(nom, mail, téléphone, adresse, idSociété);
	}

	void update(int id, String nom, String mail, String téléphone, String adresse, Société société) {
		contacteServiceImpl.update(id, nom, mail, téléphone, adresse, société);
	}

	void delete(int id) {
		contacteServiceImpl.delete(id);
	}

	public Contacte findById(int id) {
		return contacteServiceImpl.findById(id);
	}

	public List<Map<String, Object>> findAll() {
		return contacteServiceImpl.findAll();
	}

	public ContactsController() {
		super();
		this.contacteServiceImpl = new ContacteServiceImpl();
	}

}
