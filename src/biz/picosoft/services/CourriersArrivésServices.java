package biz.picosoft.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import biz.picosoft.entity.Courrier;

public interface CourriersArriv�sServices {
ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier);
void r�viser(String idCourrier,boolean isValidated) ;
public void validerCourrier(ProcessInstance processInstance, RuntimeService runtimeService);
void traiterCourrier(String idCourrier,	Map<String, Object> propriet�sCourrier);
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id);
List<ProcessInstance> getListCourriersArriv�es();
List<Task> getListCourriersArriv�sParUser(String userName);
List<Task> getListCourrierArriv�ParDirection(String direction);
}
