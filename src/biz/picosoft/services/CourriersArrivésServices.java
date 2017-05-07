package biz.picosoft.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public interface CourriersArrivésServices {
ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier);
void validerCourrier(String idCourrier,boolean isValidated) ;
void traiterCourrier(String idCourrier,	Map<String, Object> proprietésCourrier);
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePiécesJointes, String expéditeur, String id);
List <Courrier> getListCourriersArrivées();

}
