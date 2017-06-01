package biz.picosoft.services;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.dao.SociétéDao;
import biz.picosoft.daoImpl.SociétéDaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;

public class SociétéServiceImpl implements SociétéService {
SociétéDao sociétéDao;

public SociétéServiceImpl() {
	super();
	
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	this.sociétéDao=(SociétéDaoImpl) context.getBean("sociétéDaoImpl");
}

@Override
public void insert(Société société) {
	// TODO Auto-generated method stub
	sociétéDao.insert(société);
}

@Override
public void update(Société société) {
	// TODO Auto-generated method stub
	sociétéDao.update(société);
}

@Override
public void delete(Société société) {
	// TODO Auto-generated method stub
	sociétéDao.delete(société);
}

@Override
public Société findById(String id) {
	// TODO Auto-generated method stub
	return sociétéDao.findById(Société.class, id);
}

@Override
public List<Société> findAll() {
	// TODO Auto-generated method stub
	return sociétéDao.findAll();
}

@Override
public List<Contacte> findAllContacts(Société société) {
	// TODO Auto-generated method stub
	return sociétéDao.findAllContacts(société);
}

}
