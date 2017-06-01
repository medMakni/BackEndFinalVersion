package biz.picosoft.services;

import java.util.List;

import biz.picosoft.entity.Contacte;

public interface ContactService {
	void insert(Contacte contacte);

	void update(Contacte contacte);

	void delete(Contacte contacte);

	public Contacte findById(String id);

	public List<Contacte> findAll();
}
