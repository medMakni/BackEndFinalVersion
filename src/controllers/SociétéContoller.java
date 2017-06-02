package controllers;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.services.Soci�t�Service;
import biz.picosoft.services.Soci�t�ServiceImpl;

public class Soci�t�Contoller {
	Soci�t�Service soci�t�Service ;
	void insert(  String nom, String email, String t�l�phone, String adress) {
		soci�t�Service.insert(nom, email, t�l�phone, adress);
	}

	void update(int id,String nom, String email, String t�l�phone, String adress) {
		soci�t�Service.update(id, nom, email, t�l�phone, adress);
	}

	void delete(int id) {
		soci�t�Service.delete(id);
	}

	public Map<String, Object> findById(int id) {
		return soci�t�Service.findById(id);
	}

	public List<Map<String, Object>> findAll() {
		return soci�t�Service.findAll();
	}

	public List<Contacte> findAllContacts(int idSoci�t�) {
		return soci�t�Service.findAllContacts(idSoci�t�);
	}

	public Soci�t�Contoller() {
		super();
		this.soci�t�Service=new Soci�t�ServiceImpl();
	}
}
