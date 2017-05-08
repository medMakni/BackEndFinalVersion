package biz.picosoft.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.Task;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.entity.Courrier;

public class CourriersArrivésImplLocal implements CourriersArrivésServices {
	ProcessEngine processEngine;
	Session session;
	TaskService taskService = processEngine.getTaskService();

	@Override
	// this method create a mail process and attach its file to it by calling
	// the attach file method
	// and then attach the folder of the mail to
	public ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("courriersArrivés",
				proprietésCourrier);
		List<File> listePiécesJointes = (List<File>) proprietésCourrier.get("listePiécesJointes");
		if (listePiécesJointes != null) {
			String idCourrierArrivéFolder = attachFiles(listePiécesJointes,
					(String) proprietésCourrier.get("expéditeur"), processInstance.getId());
			proprietésCourrier.put("idCourrierArrivéFolder", idCourrierArrivéFolder);
			runtimeService.setVariables(processInstance.getId(), proprietésCourrier);

		}
		// TODO Do not forget redirection with dipatcher

		taskService.complete(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId());
		//add the groups to ldap and affect réviserCourrier to BO
		/*taskService.addCandidateGroup(
				taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				"Bureau D'ordre");*/
		return processInstance;
	}

	@Override
	public void réviser(String idCourrier, boolean isValidated) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();

		if (isValidated) {

			validerCourrier(processInstance, runtimeService);

		}

	}
	@Override
	public void validerCourrier(ProcessInstance processInstance, RuntimeService runtimeService) {
		Map<String, Object> proprietésCourrier = runtimeService.getVariables((processInstance.getId()));
		proprietésCourrier.replace("isValidated", true);
		taskService = processEngine.getTaskService();
		taskService.complete(
				taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId());
		String départmentId = (String) proprietésCourrier.get("départmentId");
		taskService.addCandidateGroup(
				taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				"ROLE_ADMIN");
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

	// this method attach files to a process and return the folder id
	// it checks also if necessary folder are already created or not if not it
	// will create it
	@Override
	public String attachFiles(List<File> listePiécesJointes, String expéditeur, String id) {
		Folder folderCourrier = null;
		if (listePiécesJointes != null) {
			DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl();
			FolderDaoImpl folderDaoImpl = new FolderDaoImpl();
			folderDaoImpl.setSession(session);
			Folder courriersArrivésFolderPerYear;
			Folder courriersArrivésFolder;

			try {
				try {
					courriersArrivésFolder = (Folder) folderDaoImpl.getFolderByPath("/courriersArrivés");
				} catch (Exception myExction) {
					courriersArrivésFolder = folderDaoImpl.createFolder((Folder) folderDaoImpl.getFolderByPath("/"),
							"courriersArrivés");
				}
				courriersArrivésFolderPerYear = (Folder) folderDaoImpl.getFolderByPath(
						courriersArrivésFolder.getPath() + "/" + Calendar.getInstance().get(Calendar.YEAR));
			} catch (Exception myExction) {

				courriersArrivésFolderPerYear = folderDaoImpl.createFolder(
						(Folder) folderDaoImpl.getFolderByPath("/courriersArrivés"),
						Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			}
			folderCourrier = folderDaoImpl.createFolder(courriersArrivésFolderPerYear, expéditeur + id);
			for (int i = 0; i < listePiécesJointes.size(); i++) {
				try {
					documentDaoImpl.inserte(listePiécesJointes.get(i), folderCourrier);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		}

		return folderCourrier.getId();
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

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
