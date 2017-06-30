package biz.picosoft.services;

import java.util.HashMap;
import java.util.Map;

public class StatisticServiceImpl implements StatisticsService {

	CourriersArrivésImpl courriersArrivésImpl;
	CourrierInterneImpl courrierInterneImpl;
	CourrierSortieImpl courrierSortieImpl;
	LdapServiceImpl ldapServiceImpl;

	public StatisticServiceImpl() {
		super();
		this.courriersArrivésImpl = new CourriersArrivésImpl();
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
		mapNumberOActiveCourrier.put("courriersArrivés", courriersArrivésImpl.getListCourriers().size());
		mapNumberOActiveCourrier.put("courriersSorties", courrierSortieImpl.getListCourriers().size());
		mapNumberOActiveCourrier.put("courriersInternes", courrierInterneImpl.getListCourriers().size());

		return mapNumberOActiveCourrier;
	}

	@Override
	public Map<String, Integer> getNumberOfFinishedCourrier() {
		Map<String, Integer> mapNumberOfFinishedCourrier = new HashMap<>();
		mapNumberOfFinishedCourrier.put("courriersArrivés", courriersArrivésImpl.getFinishedCourrier().size());
		mapNumberOfFinishedCourrier.put("courriersSorties", courrierSortieImpl.getFinishedCourrier().size());
		mapNumberOfFinishedCourrier.put("courriersInternes",courrierInterneImpl.getFinishedCourrier().size());

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
	public float getRateOfCourrierArrivéPerUser(String uid) {
		float rate = (float) courriersArrivésImpl.getListActiveCourriersParUser(uid).size()
				/ courriersArrivésImpl.getActiveAndFinishedCourriersPerUser(uid).size();
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
