package biz.picosoft.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public class CourriersArrivésImplRemote implements CourriersArrivésServices{

	@Override
	public ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void réviser(String idCourrier, boolean isValidated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validerCourrier(ProcessInstance processInstance, RuntimeService runtimeService) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traiterCourrier(String idCourrier, Map<String, Object> proprietésCourrier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void archiverCourrier(String idCourrier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String attachFiles(List<File> listePiécesJointes, String expéditeur, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Courrier> getListCourriersArrivées() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getListCourriersArrivésParUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getListCourrierArrivéParDirection(String direction) {
		// TODO Auto-generated method stub
		return null;
	}
 
	 
}
