package biz.picosoft.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public class CourriersArrivésImplLocal implements CourriersArrivésServices {
	ProcessEngine processEngine;

	@Override
	public ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("courriersArrivés",
				proprietésCourrier);
		// TODO Do not forget redirection with dipatcher
		return processInstance;
	}

	@Override
	public void validerCourrier(String idCourrier, boolean isValidated) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		Map<String, Object> proprietésCourrier = runtimeService.getVariables((idCourrier));
		proprietésCourrier.replace("isValidated", isValidated);

		runtimeService.setVariables(processInstance.getId(), proprietésCourrier);
	}

	@Override
	public void traiterCourrier(String idCourrier, Map<String, Object> nouvellesProprietésCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		runtimeService.setVariables(processInstance.getDeploymentId(), nouvellesProprietésCourrier);
	}

	@Override
	public void archiverCourrier(String idCourrier) {

	}

	@Override
	public List<Courrier> getListCourriersArrivées() {

		return null;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public CourriersArrivésImplLocal(ProcessEngine processEngine) {
		super();
		this.processEngine = processEngine;
	}

}
