package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public interface CourriersArriv�sServices {
ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier);
void validerCourrier(String idCourrier,boolean isValidated) ;
void traiterCourrier(String idCourrier,	Map<String, Object> propriet�sCourrier);
void archiverCourrier(String idCourrier);
List <Courrier> getListCourriersArriv�es();
}
