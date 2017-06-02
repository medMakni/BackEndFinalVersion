package biz.picosoft.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		this.soci�t�Dao = (Soci�t�DaoImpl) context.getBean("soci�t�DaoImpl");
	}

	@Override
	public void insert(Soci�t� soci�t�) {
		// TODO Auto-generated method stub
		soci�t�Dao.insert(soci�t�);
	}

	@Override
	public void update(int id,String nom, String email, String t�l�phone, String adress) {
		Soci�t� soci�t�=new Soci�t�(id,nom, email, t�l�phone, adress);
 
		// TODO Auto-generated method stub
		soci�t�Dao.update(soci�t�);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Soci�t� soci�t�=soci�t�Dao.findById(Soci�t�.class, id);
		soci�t�Dao.delete(soci�t�);
	}

	@Override
	public Soci�t� findById(int id) {
		// TODO Auto-generated method stub
		return soci�t�Dao.findById(Soci�t�.class, id);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		List<Map<String, Object>> listOfMapOfAllScoi�t� = new ArrayList<Map<String, Object>>();
		Map<String, Object> allSoci�t�Map;
		for (int i = 0; i < soci�t�Dao.findAll().size(); i++) {
			allSoci�t�Map = new HashMap<String, Object>();
			allSoci�t�Map.put("idSoci�t�", soci�t�Dao.findAll().get(i).getIdSoci�t�());
			allSoci�t�Map.put("nom", soci�t�Dao.findAll().get(i).getNom());
			allSoci�t�Map.put("email", soci�t�Dao.findAll().get(i).getEmail());
			allSoci�t�Map.put("t�l�phone", soci�t�Dao.findAll().get(i).getT�l�phone());
			allSoci�t�Map.put("adresse", soci�t�Dao.findAll().get(i).getAdress());
			listOfMapOfAllScoi�t�.add(allSoci�t�Map);
			allSoci�t�Map = null;
		}
		return listOfMapOfAllScoi�t�;
	}

	@Override
	public List<Contacte> findAllContacts(Soci�t� soci�t�) {
		// TODO Auto-generated method stub
		return soci�t�Dao.findAllContacts(soci�t�);

	}

}
