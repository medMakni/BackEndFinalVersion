package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;

public interface Soci�t�Service {

	void insert(String nom, String email, String t�l�phone, String adress);

	void update(int id,String nom, String email, String t�l�phone, String adress);

	void delete(int id);

	public Map<String, Object> findById(int id);

	public List<Map<String, Object>> findAll();

	public List<String> findAllContacts(int  idSoci�t�);
	
	Soci�t� getSoci�t�FromNom(String nomSoci�t�);
}
