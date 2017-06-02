package controllers;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;
import biz.picosoft.services.ContacteServiceImpl;

public class ContactsController {
	ContacteServiceImpl contacteServiceImpl;

	void insert(String nom, String mail, String t�l�phone, String adresse, int idSoci�t�) {
		contacteServiceImpl.insert(nom, mail, t�l�phone, adresse, idSoci�t�);
	}

	void update(int id, String nom, String mail, String t�l�phone, String adresse, Soci�t� soci�t�) {
		contacteServiceImpl.update(id, nom, mail, t�l�phone, adresse, soci�t�);
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
