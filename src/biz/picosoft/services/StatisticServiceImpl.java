package biz.picosoft.services;

import java.util.HashMap;
import java.util.Map;

public class StatisticServiceImpl implements StatisticsService {

	CourriersArriv�sImpl courriersArriv�sImpl;
	LdapServiceImpl ldapServiceImpl;

	public StatisticServiceImpl() {
		super();
		this.courriersArriv�sImpl = new CourriersArriv�sImpl();
		this.ldapServiceImpl = new LdapServiceImpl();
	}

	@Override
	public Map<String, Integer> getNbrCourrierPerCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getNumberOfActiveCourrier() {

		Map<String, Integer> mapNumberOActiveCourrier = new HashMap<>();
		mapNumberOActiveCourrier.put("courriersArriv�s", courriersArriv�sImpl.getListCourriers().size());
		mapNumberOActiveCourrier.put("courriersSorties", 0);
		mapNumberOActiveCourrier.put("courriersInternes", 0);

		return mapNumberOActiveCourrier;
	}

	@Override
	public Map<String, Integer> getNumberOfFinishedCourrier() {
		Map<String, Integer> mapNumberOfFinishedCourrier = new HashMap<>();
		mapNumberOfFinishedCourrier.put("courriersArriv�s", courriersArriv�sImpl.getFinishedCourrier().size());
		mapNumberOfFinishedCourrier.put("courriersSorties", 0);
		mapNumberOfFinishedCourrier.put("courriersInternes", 0);

		return mapNumberOfFinishedCourrier;
	}

	@Override
	public Map<String, Integer> getNbrActiveCourrierArriv�PerDirection() {
		// TODO Auto-generated method stub
		Map<String, Integer> mapNbrActiveCourrierArriv�PerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrActiveCourrierArriv�PerDirection.put(nomDirection,
					courriersArriv�sImpl.getListActiveCourrierParDirection(nomDirection).size());
		}
		return mapNbrActiveCourrierArriv�PerDirection;
	}

	@Override
	public Map<String, Integer> getNbrFinishedCourrierArriv�PerDirection() {
		// TODO Auto-generated method stub
		Map<String, Integer> mapNbrFinishedCourrierArriv�PerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrFinishedCourrierArriv�PerDirection.put(nomDirection,
					courriersArriv�sImpl.getNbrOfFinishedCourrierParDirection(nomDirection));
		}
		return mapNbrFinishedCourrierArriv�PerDirection;
		 
	}

	@Override
	public Map<String, Integer> getNbrActiveCourrierSortiesPerDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getNbrFinishedCourrierSortiesPerDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getNbrActiveCourrierInternesPerDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getNbrFinishedCourrierInternesPerDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRateOfCourrierArriv�PerUser(String uid) {
		 
		return( courriersArriv�sImpl.getListActiveCourriersParUser(uid).size()/( courriersArriv�sImpl.getListActiveCourriersParUser(uid).size()+courriersArriv�sImpl.getListFinishedCourrierPerUser(uid).size()))*100;
	}

}
