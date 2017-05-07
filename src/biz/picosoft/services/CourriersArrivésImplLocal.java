package biz.picosoft.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import biz.picosoft.entity.Courrier;

public class CourriersArriv�sImplLocal implements CourriersArriv�sServices {
	ProcessEngine processEngine;

	@Override
	public ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("courriersArriv�s",
				propriet�sCourrier);
		// TODO Do not forget redirection with dipatcher
		return processInstance;
	}

	@Override
	public void validerCourrier(String idCourrier, boolean isValidated) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		Map<String, Object> propriet�sCourrier = runtimeService.getVariables((idCourrier));
		propriet�sCourrier.replace("isValidated", isValidated);

		runtimeService.setVariables(processInstance.getId(), propriet�sCourrier);
	}

	@Override
	public void traiterCourrier(String idCourrier, Map<String, Object> nouvellesPropriet�sCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		runtimeService.setVariables(processInstance.getDeploymentId(), nouvellesPropriet�sCourrier);
	}

	@Override
	public void archiverCourrier(String idCourrier) {

	}

	@Override
	public List<Courrier> getListCourriersArriv�es() {

		return null;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public CourriersArriv�sImplLocal(ProcessEngine processEngine) {
		super();
		this.processEngine = processEngine;
	}

}
