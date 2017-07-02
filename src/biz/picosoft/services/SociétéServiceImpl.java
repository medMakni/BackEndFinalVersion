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
	public void insert(String nom, String email, String télèphone, String adress) {
		Société société=new Société(nom, email, télèphone, adress);
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
	public Map<String, Object> findById(int id) {
		Société société=sociétéDao.findById(Société.class, id);
		Map<String, Object> mapOfSociété=new HashMap<String, Object> ();
		mapOfSociété.put("nom", société.getNom());
		mapOfSociété.put("email", société.getNom());
		mapOfSociété.put("télèphone", société.getTélèphone());
		mapOfSociété.put("adress", société.getAdress());
		// TODO Auto-generated method stub
		return mapOfSociété;
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
	public List<String> findAllContacts(int idSociété) {
		Société société=sociétéDao.findById(Société.class, idSociété);
		return sociétéDao.findAllContacts(société);

	}

	@Override
	public Société getSociétéFromNom(String nomSociété) {
		// TODO Auto-generated method stub
		return sociétéDao.getSociétéFromNom(nomSociété);
	}

}
