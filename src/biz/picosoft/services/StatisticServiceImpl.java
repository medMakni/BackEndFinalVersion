package biz.picosoft.services;

import java.util.HashMap;
import java.util.Map;

public class StatisticServiceImpl implements StatisticsService {

	CourriersArrivésImpl courriersArrivésImpl;
	LdapServiceImpl ldapServiceImpl;

	public StatisticServiceImpl() {
		super();
		this.courriersArrivésImpl = new CourriersArrivésImpl();
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
		mapNumberOActiveCourrier.put("courriersArrivés", courriersArrivésImpl.getListCourriers().size());
		mapNumberOActiveCourrier.put("courriersSorties", 0);
		mapNumberOActiveCourrier.put("courriersInternes", 0);

		return mapNumberOActiveCourrier;
	}

	@Override
	public Map<String, Integer> getNumberOfFinishedCourrier() {
		Map<String, Integer> mapNumberOfFinishedCourrier = new HashMap<>();
		mapNumberOfFinishedCourrier.put("courriersArrivés", courriersArrivésImpl.getFinishedCourrier().size());
		mapNumberOfFinishedCourrier.put("courriersSorties", 0);
		mapNumberOfFinishedCourrier.put("courriersInternes", 0);

		return mapNumberOfFinishedCourrier;
	}

	@Override
	public Map<String, Integer> getNbrActiveCourrierArrivéPerDirection() {
		// TODO Auto-generated method stub
		Map<String, Integer> mapNbrActiveCourrierArrivéPerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrActiveCourrierArrivéPerDirection.put(nomDirection,
					courriersArrivésImpl.getListActiveCourrierParDirection(nomDirection).size());
		}
		return mapNbrActiveCourrierArrivéPerDirection;
	}

	@Override
	public Map<String, Integer> getNbrFinishedCourrierArrivéPerDirection() {
		// TODO Auto-generated method stub
		Map<String, Integer> mapNbrFinishedCourrierArrivéPerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrFinishedCourrierArrivéPerDirection.put(nomDirection,
					courriersArrivésImpl.getNbrOfFinishedCourrierParDirection(nomDirection));
		}
		return mapNbrFinishedCourrierArrivéPerDirection;
		 
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
	public float getRateOfCourrierArrivéPerUser(String uid) {
		 
		return( courriersArrivésImpl.getListActiveCourriersParUser(uid).size()/( courriersArrivésImpl.getListActiveCourriersParUser(uid).size()+courriersArrivésImpl.getListFinishedCourrierPerUser(uid).size()))*100;
	}

}
