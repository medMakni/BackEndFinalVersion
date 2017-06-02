package biz.picosoft.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		this.sociétéDao = (SociétéDaoImpl) context.getBean("sociétéDaoImpl");
	}

	@Override
	public void insert(Société société) {
		// TODO Auto-generated method stub
		sociétéDao.insert(société);
	}

	@Override
	public void update(int id,String nom, String email, String télèphone, String adress) {
		Société société=new Société(id,nom, email, télèphone, adress);
 
		// TODO Auto-generated method stub
		sociétéDao.update(société);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Société société=sociétéDao.findById(Société.class, id);
		sociétéDao.delete(société);
	}

	@Override
	public Société findById(int id) {
		// TODO Auto-generated method stub
		return sociétéDao.findById(Société.class, id);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		List<Map<String, Object>> listOfMapOfAllScoiété = new ArrayList<Map<String, Object>>();
		Map<String, Object> allSociétéMap;
		for (int i = 0; i < sociétéDao.findAll().size(); i++) {
			allSociétéMap = new HashMap<String, Object>();
			allSociétéMap.put("idSociété", sociétéDao.findAll().get(i).getIdSociété());
			allSociétéMap.put("nom", sociétéDao.findAll().get(i).getNom());
			allSociétéMap.put("email", sociétéDao.findAll().get(i).getEmail());
			allSociétéMap.put("téléphone", sociétéDao.findAll().get(i).getTélèphone());
			allSociétéMap.put("adresse", sociétéDao.findAll().get(i).getAdress());
			listOfMapOfAllScoiété.add(allSociétéMap);
			allSociétéMap = null;
		}
		return listOfMapOfAllScoiété;
	}

	@Override
	public List<Contacte> findAllContacts(Société société) {
		// TODO Auto-generated method stub
		return sociétéDao.findAllContacts(société);

	}

}
