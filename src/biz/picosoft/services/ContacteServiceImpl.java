package biz.picosoft.services;

import java.util.List;

import biz.picosoft.dao.ContacteDao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.entity.Contacte;

public class ContacteServiceImpl implements ContactService {
	ContacteDao contacteDao;

	public ContacteServiceImpl() {
		super();
		this.contacteDao = new ContacteDaoImpl();
	}

	@Override
	public void insert(Contacte contacte) {

		contacteDao.insert(contacte);

	}

	@Override
	public void update(Contacte contacte) {
		// TODO Auto-generated method stub
		contacteDao.update(contacte);
	}

	@Override
	public void delete(Contacte contacte) {
		// TODO Auto-generated method stub
		contacteDao.delete(contacte);
	}

	@Override
	public Contacte findById(String id) {
		// TODO Auto-generated method stub
		return contacteDao.findById(Contacte.class, id);
	}

	@Override
	public List<Contacte> findAll() {
		// TODO Auto-generated method stub
		return contacteDao.findAll();
	}

}
