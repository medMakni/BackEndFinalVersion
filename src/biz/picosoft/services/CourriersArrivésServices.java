package biz.picosoft.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import biz.picosoft.entity.Courrier;

public interface CourriersArrivésServices {
ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier);
void réviser(String idCourrier,boolean isValidated) ;
public void validerCourrier(ProcessInstance processInstance, RuntimeService runtimeService);
void traiterCourrier(String idCourrier,	Map<String, Object> proprietésCourrier);
void archiverCourrier(String idCourrier);
String attachFiles(List<File> listePiécesJointes, String expéditeur, String id);
List<ProcessInstance> getListCourriersArrivées();
List<Task> getListCourriersArrivésParUser(String userName);
List<Task> getListCourrierArrivéParDirection(String direction);
}
