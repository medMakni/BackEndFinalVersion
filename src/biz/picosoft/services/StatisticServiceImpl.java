package biz.picosoft.services;

import java.util.Map;

public class StatisticServiceImpl implements StatisticsService{
	
	CourriersArriv�sImpl courriersArriv�sImpl;

	 
	public StatisticServiceImpl() {
		super();
		this.courriersArriv�sImpl=new CourriersArriv�sImpl();
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
	public int getNbrActiveCourrierArriv�PerDirection(String idDirection) {
		// TODO Auto-generated method stub
		 
		return courriersArriv�sImpl.getNbrOfFinishedCourrierArriv�ParDirection(idDirection);
	}


	@Override
	public int getNbrFinishedCourrierArriv�PerDirection(String idDirection) {
		// TODO Auto-generated method stub
	 
		return 	courriersArriv�sImpl.getNbrOfFinishedCourrierArriv�ParDirection(idDirection);
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
