package biz.picosoft.services;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

	Map<String, Integer> getNbrCourrierPerCompany();

	Map<String, Integer> getNumberOfActiveCourrier();

	Map<String, Integer> getNumberOfFinishedCourrier();
	List<Map<String, Integer>> getCourrierFinisPerDirection();
	List<Map<String, Integer>> getCourrierActifsPerDirection();
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
