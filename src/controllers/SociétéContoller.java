package controllers;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.services.SociétéService;
import biz.picosoft.services.SociétéServiceImpl;

public class SociétéContoller {
	SociétéService sociétéService ;
	void insert(  String nom, String email, String télèphone, String adress) {
		sociétéService.insert(nom, email, télèphone, adress);
	}

	void update(int id,String nom, String email, String télèphone, String adress) {
		sociétéService.update(id, nom, email, télèphone, adress);
	}

	void delete(int id) {
		sociétéService.delete(id);
	}

	public Map<String, Object> findById(int id) {
		return sociétéService.findById(id);
	}

	public List<Map<String, Object>> findAll() {
		return sociétéService.findAll();
	}

	public List<Contacte> findAllContacts(int idSociété) {
		return sociétéService.findAllContacts(idSociété);
	}

	public SociétéContoller() {
		super();
		this.sociétéService=new SociétéServiceImpl();
	}
}
