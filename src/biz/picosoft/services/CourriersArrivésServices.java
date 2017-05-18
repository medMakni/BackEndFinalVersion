package biz.picosoft.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import biz.picosoft.entity.Courrier;

public interface CourriersArrivésServices {
ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier);
void réviser(String idCourrier,boolean isValidated) ;
public void validerCourrier(String idCourrier);
public void refuserCourrier(String idCourrier);
void traiterCourrier(String idCourrier,	Map<String, Object> proprietésCourrier);
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePiécesJointes, String expéditeur, String id);
List<ProcessInstance> getListCourriersArrivées();
List<Task> getListCourriersArrivésParUser(String userName);
List<Task> getListCourrierArrivéParDirection(String direction);
public File multipartToFile(MultipartFile multipart);
}
