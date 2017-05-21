package biz.picosoft.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

public interface CourriersArrivésServices {
ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier);
void réviser(String idCourrier,boolean isValidated) ;
public void validerCourrier(String idCourrier);
public void refuserCourrier(String idCourrier);
void traiterCourrier(String idCourrier,	Map<String, Object> proprietésCourrier );
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePiécesJointes, String expéditeur, String id);
List<ProcessInstance> getListCourriersArrivées();
List<Task> getListActiveCourriersArrivésParUser(String userName);
List<Task> getListCourrierArrivéParDirection(String direction);
public File multipartToFile(MultipartFile multipart);

List<String> getListFinishedCourrierArrivéPerUser(String userId);
}
