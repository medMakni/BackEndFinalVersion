package biz.picosoft.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public interface CourriersArriv�sServices {
ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier);
void validerCourrier(String idCourrier,boolean isValidated) ;
void traiterCourrier(String idCourrier,	Map<String, Object> propriet�sCourrier);
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id);
List <Courrier> getListCourriersArriv�es();

}
