package biz.picosoft.services;

import java.util.Map;

public interface StatisticsService {

	Map<String, Integer> getNbrCourrierPerCompany();
	int getNumberOfActiveCourrier();
	int getNumberOfFinishedCourrier();
	int  getNbrActiveCourrierArriv�PerDirection(String idDirection);
	int getNbrFinishedCourrierArriv�PerDirection(String idDirection);
	int  getNbrActiveCourrierSortiesPerDirection(String idDirection);
	int getNbrFinishedCourrierSortiesPerDirection(String idDirection);
	int getNbrActiveCourrierInternesPerDirection(String idDirection);
	int getNbrFinishedCourrierInternesPerDirection(String idDirection);
}
