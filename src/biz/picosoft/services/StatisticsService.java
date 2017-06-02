package biz.picosoft.services;

import java.util.Map;

public interface StatisticsService {

	Map<String, Integer> getNbrCourrierPerCompany();
	int getNumberOfActiveCourrier();
	int getNumberOfFinishedCourrier();
	int  getNbrActiveCourrierArrivéPerDirection(String idDirection);
	int getNbrFinishedCourrierArrivéPerDirection(String idDirection);
	int  getNbrActiveCourrierSortiesPerDirection(String idDirection);
	int getNbrFinishedCourrierSortiesPerDirection(String idDirection);
	int getNbrActiveCourrierInternesPerDirection(String idDirection);
	int getNbrFinishedCourrierInternesPerDirection(String idDirection);
}
