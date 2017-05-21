package biz.picosoft.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

public interface CourriersArriv�sServices {
ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier);
void r�viser(String idCourrier,boolean isValidated) ;
public void validerCourrier(String idCourrier);
public void refuserCourrier(String idCourrier);
void traiterCourrier(String idCourrier,	Map<String, Object> propriet�sCourrier );
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id);
List<ProcessInstance> getListCourriersArriv�es();
List<Task> getListActiveCourriersArriv�sParUser(String userName);
List<Task> getListCourrierArriv�ParDirection(String direction);
public File multipartToFile(MultipartFile multipart);

List<String> getListFinishedCourrierArriv�PerUser(String userId);
}
