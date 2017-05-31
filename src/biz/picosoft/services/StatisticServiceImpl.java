package biz.picosoft.services;

import java.util.Map;

public class StatisticServiceImpl implements StatisticsService{
	
	CourriersArrivésImpl courriersArrivésImpl;

	 
	public StatisticServiceImpl() {
		super();
		this.courriersArrivésImpl=new CourriersArrivésImpl();
	}


	@Override
	public Map<String, Integer> getNbrCourrierPerCompany() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getNumberOfActiveCourrier() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getNumberOfFinishedCourrier() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getNbrActiveCourrierArrivéPerDirection(String idDirection) {
		// TODO Auto-generated method stub
		 
		return courriersArrivésImpl.getNbrOfFinishedCourrierArrivéParDirection(idDirection);
	}


	@Override
	public int getNbrFinishedCourrierArrivéPerDirection(String idDirection) {
		// TODO Auto-generated method stub
	 
		return 	courriersArrivésImpl.getNbrOfFinishedCourrierArrivéParDirection(idDirection);
	}


	@Override
	public int getNbrActiveCourrierSortiesPerDirection(String idDirection) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getNbrFinishedCourrierSortiesPerDirection(String idDirection) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getNbrActiveCourrierInternesPerDirection(String idDirection) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getNbrFinishedCourrierInternesPerDirection(String idDirection) {
		// TODO Auto-generated method stub
		return 0;
	}
 
}
