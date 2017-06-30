package biz.picosoft.services;

import java.util.Map;

public interface StatisticsService {

	Map<String, Integer> getNbrCourrierPerCompany();

	Map<String, Integer> getNumberOfActiveCourrier();

	Map<String, Integer> getNumberOfFinishedCourrier();

	Map<String, Integer> getNbrActiveCourrierArrivéPerDirection( );

	Map<String, Integer> getNbrFinishedCourrierArrivéPerDirection( );

	Map<String, Integer> getNbrActiveCourrierSortiesPerDirection( );

	Map<String, Integer> getNbrFinishedCourrierSortiesPerDirection( );

	Map<String, Integer> getNbrActiveCourrierInternesPerDirection( );

	Map<String, Integer> getNbrFinishedCourrierInternesPerDirection( );
	
	float getRateOfCourrierArrivéPerUser(String uid);
	float getRateOfCourrierInternePerUser(String uid);
	float getRateOfCourrierSortiePerUser(String uid);
}
