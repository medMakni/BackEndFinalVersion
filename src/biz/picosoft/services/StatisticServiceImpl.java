package biz.picosoft.services;

import java.util.HashMap;
import java.util.Map;

public class StatisticServiceImpl implements StatisticsService {

	CourriersArriv�sImpl courriersArriv�sImpl;
	CourrierInterneImpl courrierInterneImpl;
	CourrierSortieImpl courrierSortieImpl;
	LdapServiceImpl ldapServiceImpl;

	public StatisticServiceImpl() {
		super();
		this.courriersArriv�sImpl = new CourriersArriv�sImpl();
		this.courrierInterneImpl=new CourrierInterneImpl();
		this.courrierSortieImpl=new CourrierSortieImpl();
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
		mapNumberOActiveCourrier.put("courriersSorties", courrierSortieImpl.getListCourriers().size());
		mapNumberOActiveCourrier.put("courriersInternes", courrierInterneImpl.getListCourriers().size());

		return mapNumberOActiveCourrier;
	}

	@Override
	public Map<String, Integer> getNumberOfFinishedCourrier() {
		Map<String, Integer> mapNumberOfFinishedCourrier = new HashMap<>();
		mapNumberOfFinishedCourrier.put("courriersArriv�s", courriersArriv�sImpl.getFinishedCourrier().size());
		mapNumberOfFinishedCourrier.put("courriersSorties", courrierSortieImpl.getFinishedCourrier().size());
		mapNumberOfFinishedCourrier.put("courriersInternes",courrierInterneImpl.getFinishedCourrier().size());

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
		Map<String, Integer> mapNbrActiveCourrierSortiePerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrActiveCourrierSortiePerDirection.put(nomDirection,
					courrierSortieImpl.getListActiveCourrierParDirection(nomDirection).size());
		}
		return mapNbrActiveCourrierSortiePerDirection;
	}

	@Override
	public Map<String, Integer> getNbrFinishedCourrierSortiesPerDirection() {
		Map<String, Integer> mapNbrFinishedCourrierSortiePerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrFinishedCourrierSortiePerDirection.put(nomDirection,
					courrierSortieImpl.getNbrOfFinishedCourrierParDirection(nomDirection));
		}
		return mapNbrFinishedCourrierSortiePerDirection;
	}

	@Override
	public Map<String, Integer> getNbrActiveCourrierInternesPerDirection() {
		Map<String, Integer> mapNbrActiveCourrierInternePerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrActiveCourrierInternePerDirection.put(nomDirection,
					courrierInterneImpl.getListActiveCourrierParDirection(nomDirection).size());
		}
		return mapNbrActiveCourrierInternePerDirection;
	}

	@Override
	public Map<String, Integer> getNbrFinishedCourrierInternesPerDirection() {
		Map<String, Integer> mapNbrFinishedCourrierInternePerDirection = new HashMap<>();
		String nomDirection;
		for (int i = 0; i < ldapServiceImpl.getAllDirection().size(); i++) {
			nomDirection = ldapServiceImpl.getAllDirection().get(i);
			mapNbrFinishedCourrierInternePerDirection.put(nomDirection,
					courrierInterneImpl.getNbrOfFinishedCourrierParDirection(nomDirection));
		}
		return mapNbrFinishedCourrierInternePerDirection;
	}

	@Override
	public float getRateOfCourrierArriv�PerUser(String uid) {
		float rate = (float) courriersArriv�sImpl.getListActiveCourriersParUser(uid).size()
				/ courriersArriv�sImpl.getActiveAndFinishedCourriersPerUser(uid).size();
		return (rate*100);
	}

	@Override
	public float getRateOfCourrierInternePerUser(String uid) {
		float rate = (float) courrierInterneImpl.getListActiveCourriersParUser(uid).size()
				/ courrierInterneImpl.getActiveAndFinishedCourriersPerUser(uid).size();
		return (rate*100);
	}

	@Override
	public float getRateOfCourrierSortiePerUser(String uid) {
		float rate = (float) courrierSortieImpl.getListActiveCourriersParUser(uid).size()
				/ courrierSortieImpl.getActiveAndFinishedCourriersPerUser(uid).size();
		return (rate*100);
	}

}
