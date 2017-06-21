package biz.picosoft.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CourriersServices {
 
ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier);
void réviser(String idCourrier,boolean isValidated) ;
public void validerCourrier(String idCourrier);
public void refuserCourrier(String idCourrier);
public void traiterCourrier (Map<String,Object> map);
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePiécesJointes, String expéditeur, String id);
List<Map<String, Object> > getListCourriers();
List<Map<String, Object> > getListActiveCourriersParUser(String userName);
List<Map<String, Object> > getListActiveCourrierParDirection(String direction);
public File multipartToFile(MultipartFile multipart);
public int getNbrOfFinishedCourrierParDirection(String directionName);
List<String> getListFinishedCourrierPerUser(String userId);
Map<String, Object> getCourrierDetails(String idCourrier) throws Exception;
List<Map<String, Object>> getFinishedCourrier();
void delete(String idCOurrier);
ResponseEntity<InputStreamResource> postFile() throws IOException, Exception;
void mettreAjour( String idCourrier, Map<String, Object> nouvellesProprietésCourrier);
ResponseEntity<InputStreamResource> postFile(String id,String nbreCourrier) throws IOException, Exception;
List<Map<String, Object>> getCourrierByStarter(String uid);
 

}
