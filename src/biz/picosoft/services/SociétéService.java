package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;

public interface Soci�t�Service {

	void insert(Soci�t� soci�t�);

	void update(int id,String nom, String email, String t�l�phone, String adress);

	void delete(int id);

	public Soci�t� findById(int id);

	public List<Map<String, Object>> findAll();

	public List<Contacte> findAllContacts(Soci�t� soci�t�);
}
