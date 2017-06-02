package biz.picosoft.services;

import java.util.List;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;

public interface SociétéService {

	void insert(Société société);

	void update(Société société);

	void delete(Société société);

	public Société findById(String id);

	public List<Société> findAll();
	public List<Contacte> findAllContacts(Société société);
}
