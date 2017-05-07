package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public interface CourriersArrivésServices {
ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier);
void validerCourrier(String idCourrier,boolean isValidated) ;
void traiterCourrier(String idCourrier,	Map<String, Object> proprietésCourrier);
void archiverCourrier(String idCourrier);
List <Courrier> getListCourriersArrivées();
}
