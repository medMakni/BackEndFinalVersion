package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;

public interface ContactService {
	void insert(Contacte contacte);

	void update(int id,String nom, String mail, String t�l�phone, String adresse, Soci�t� soci�t�);

	void delete(int id);

	public Contacte findById(int id);

	public List<Map<String, Object>> findAll();
}
