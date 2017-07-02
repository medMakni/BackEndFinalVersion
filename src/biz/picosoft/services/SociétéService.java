package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;

public interface SociétéService {

	void insert(String nom, String email, String télèphone, String adress);

	void update(int id,String nom, String email, String télèphone, String adress);

	void delete(int id);

	public Map<String, Object> findById(int id);

	public List<Map<String, Object>> findAll();

	public List<String> findAllContacts(int  idSociété);
	
	Société getSociétéFromNom(String nomSociété);
}
