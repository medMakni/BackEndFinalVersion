package biz.picosoft.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.mains.TestDao;

@Service
public class CourriersArrivésImpl implements CourriersArrivésServices {

	ProcessEngine processEngine;
	Session session;
	RuntimeService runtimeService;
	TaskService taskService;

	@Override
	// this method create a mail process and attach its file to it by calling
	// the attach file method
	// and then attach the folder of the mail

	public ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier) {
		System.out.println("prop here" + proprietésCourrier);
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("courriersArrivés",
				proprietésCourrier);
		if ((boolean) proprietésCourrier.get("isValidated") != false) {
			List<File> listePiécesJointes = (List<File>) proprietésCourrier.get("listePiécesJointes");
			if (listePiécesJointes != null) {
				String idCourrierArrivéFolder = attachFiles(listePiécesJointes,
						(String) proprietésCourrier.get("expéditeur"), processInstance.getId());
				proprietésCourrier.put("idCourrierArrivéFolder", idCourrierArrivéFolder);
				runtimeService.setVariables(processInstance.getId(), proprietésCourrier);

			}
		} else {
			traiterCourrier(processInstance.getId(), proprietésCourrier);
		}
		// TODO Do not forget redirection with dispatcher
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId());
		// add the groups to ldap and affect réviserCourrier to BO

		taskService.addCandidateGroup(
				taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				"Bureau d'ordre");

		return processInstance;
	}

	@Override
	public void réviser(String idCourrier, boolean isValidated) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();

		if (isValidated) {

			validerCourrier(processInstance.getId());

		} else {
			refuserCourrier(processInstance.getId());
		}

	}

	@Override
	public void validerCourrier(String idCourrier) {
		Map<String, Object> proprietésCourrier = runtimeService.getVariables((idCourrier));
		proprietésCourrier.replace("isValidated", true);
		this.taskService = processEngine.getTaskService();
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				proprietésCourrier);
		this.taskService.addCandidateGroup(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				proprietésCourrier.get("départmentId").toString());
	}

	@Override
	public void traiterCourrier(String idCourrier, Map<String, Object> nouvellesProprietésCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		runtimeService.setVariables(processInstance.getId(), nouvellesProprietésCourrier);
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				nouvellesProprietésCourrier);
		if ((boolean) nouvellesProprietésCourrier.get("isFinished") != true) {
			taskService.addCandidateGroup(
					taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
					nouvellesProprietésCourrier.get("affectedTo").toString());
		}
	}

	@Override
	public void archiverCourrier(String idCourrier) {

	}

	// this method return all instances of courriers arrivés Process
	@Override
	public List getListCourriersArrivées() {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		List<ProcessInstance> listAllCourrierArrivé = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey("courriersArrivés").list();
		return listAllCourrierArrivé;

	}

	// this method attach files to a process and return the folder id
	// it checks also if necessary folder are already created or not if not it
	// will create it
	@Override
	public String attachFiles(List<File> listePiécesJointes, String expéditeur, String id) {
		Folder folderCourrier = null;
		if (listePiécesJointes != null) {
			DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl();
			FolderDaoImpl folderDaoImpl = new FolderDaoImpl(this.session);
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

	public CourriersArrivésImpl() {
		super();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		this.processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		this.runtimeService = processEngine.getRuntimeService();
		this.taskService = processEngine.getTaskService();
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		session = ctx.getBean(Session.class);

	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public List<Task> getListActiveCourriersArrivésParUser(String userName) {
		// TODO Auto-generated method stub
		List<Task> listTaskByProceeAndUser = this.taskService.createTaskQuery().processDefinitionKey("courriersArrivés")
				.taskCandidateUser(userName).list();
		return listTaskByProceeAndUser;
	}

	@Override

	public List<String> getListFinishedCourrierArrivéPerUser(String userId) {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<String> listFinishedCourriersId = new ArrayList<>();
		List<HistoricProcessInstance> listFinishedCourriersArrivéInstances = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey("courriersArrivés").finished().list();

		for (int j = 0; j < listFinishedCourriersArrivéInstances.size(); j++) {
			listFinishedCourriersId.add(listFinishedCourriersArrivéInstances.get(j).getId());
		}

		System.out.println(historyService.createHistoricProcessInstanceQuery().processDefinitionKey("courriersArrivés")
				.finished().list().size());
		List<String> listFinishedCourriersInvolvedMrX = new ArrayList<>();
		for (int i = 0; i < historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
				.processDefinitionKey("courriersArrivés").finished().list().size(); i++) {
			listFinishedCourriersInvolvedMrX
					.add(historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
							.processDefinitionKey("courriersArrivés").finished().list().get(i).getProcessInstanceId());

		}

	
		return listFinishedCourriersInvolvedMrX;
	}

	@Override
	public List<Task> getListCourrierArrivéParDirection(String directionName) {
		// TODO Auto-generated method stub

		List<Task> listTaskByDirection = this.taskService.createTaskQuery().processDefinitionKey("courriersArrivés")
				.taskCandidateGroup(directionName).list();
		return listTaskByDirection;

	}

	@Override
	public void refuserCourrier(String idCourrier) {
		// TODO Auto-generated method stub
		Map<String, Object> proprietésCourrier = runtimeService.getVariables((idCourrier));
		proprietésCourrier.replace("isValidated", false);
		this.taskService = processEngine.getTaskService();
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				proprietésCourrier);
		this.taskService.addCandidateGroup(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				"ROLE_Secrétaire Générale");
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public File multipartToFile(MultipartFile multipart) {
		File convFile = new File(multipart.getOriginalFilename());
		try {
			multipart.transferTo(convFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convFile;
	}

}
