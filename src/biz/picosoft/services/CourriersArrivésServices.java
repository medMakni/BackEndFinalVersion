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

public interface CourriersArriv�sServices {
ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier);
void r�viser(String idCourrier,boolean isValidated) ;
public void validerCourrier(String idCourrier);
public void refuserCourrier(String idCourrier);
void traiterCourrier(String idCourrier,	Map<String, Object> propriet�sCourrier );
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id);
List<Map<String, Object> > getListCourriersArriv�es();
List<Map<String, Object> > getListActiveCourriersArriv�sParUser(String userName);
List<Map<String, Object> > getListActiveCourrierArriv�ParDirection(String direction);
public File multipartToFile(MultipartFile multipart);
List<String> getListFinishedCourrierArriv�PerUser(String userId);
Map<String, Object> getCourrierDetails(String idCourrier);
}
