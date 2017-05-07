package biz.picosoft.services;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public class CourriersArrivésImplRemote implements CourriersArrivésServices{

	@Override
	public ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validerCourrier(String idCourrier,boolean isValidated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traiterCourrier(String idCourrier,	Map<String, Object> proprietésCourrier){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void archiverCourrier(String idCourrier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Courrier> getListCourriersArrivées() {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
