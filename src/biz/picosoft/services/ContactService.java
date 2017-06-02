package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;

public interface ContactService {
	void insert(Contacte contacte);

	void update(int id,String nom, String mail, String téléphone, String adresse, Société société);

	void delete(int id);

	public Contacte findById(int id);

	public List<Map<String, Object>> findAll();
}
