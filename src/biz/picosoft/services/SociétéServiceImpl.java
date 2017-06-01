package biz.picosoft.services;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.dao.Soci�t�Dao;
import biz.picosoft.daoImpl.Soci�t�DaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;

public class Soci�t�ServiceImpl implements Soci�t�Service {
Soci�t�Dao soci�t�Dao;

public Soci�t�ServiceImpl() {
	super();
	
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	this.soci�t�Dao=(Soci�t�DaoImpl) context.getBean("soci�t�DaoImpl");
}

@Override
public void insert(Soci�t� soci�t�) {
	// TODO Auto-generated method stub
	soci�t�Dao.insert(soci�t�);
}

@Override
public void update(Soci�t� soci�t�) {
	// TODO Auto-generated method stub
	soci�t�Dao.update(soci�t�);
}

@Override
public void delete(Soci�t� soci�t�) {
	// TODO Auto-generated method stub
	soci�t�Dao.delete(soci�t�);
}

@Override
public Soci�t� findById(String id) {
	// TODO Auto-generated method stub
	return soci�t�Dao.findById(Soci�t�.class, id);
}

@Override
public List<Soci�t�> findAll() {
	// TODO Auto-generated method stub
	return soci�t�Dao.findAll();
}

@Override
public List<Contacte> findAllContacts(Soci�t� soci�t�) {
	// TODO Auto-generated method stub
	return soci�t�Dao.findAllContacts(soci�t�);
}

}
