package biz.picosoft.services;

import java.util.List;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;

public interface Soci�t�Service {

	void insert(Soci�t� soci�t�);

	void update(Soci�t� soci�t�);

	void delete(Soci�t� soci�t�);

	public Soci�t� findById(String id);

	public List<Soci�t�> findAll();
	public List<Contacte> findAllContacts(Soci�t� soci�t�);
}
