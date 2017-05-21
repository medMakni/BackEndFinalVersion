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
public class CourriersArriv�sImpl implements CourriersArriv�sServices {

	ProcessEngine processEngine;
	Session session;
	RuntimeService runtimeService;
	TaskService taskService;

	@Override
	// this method create a mail process and attach its file to it by calling
	// the attach file method
	// and then attach the folder of the mail

	public ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier) {
		System.out.println("prop here" + propriet�sCourrier);
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("courriersArriv�s",
				propriet�sCourrier);
		if ((boolean) propriet�sCourrier.get("isValidated") != false) {
			List<File> listePi�cesJointes = (List<File>) propriet�sCourrier.get("listePi�cesJointes");
			if (listePi�cesJointes != null) {
				String idCourrierArriv�Folder = attachFiles(listePi�cesJointes,
						(String) propriet�sCourrier.get("exp�diteur"), processInstance.getId());
				propriet�sCourrier.put("idCourrierArriv�Folder", idCourrierArriv�Folder);
				runtimeService.setVariables(processInstance.getId(), propriet�sCourrier);

			}
		} else {
			traiterCourrier(processInstance.getId(), propriet�sCourrier);
		}
		// TODO Do not forget redirection with dispatcher
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId());
		// add the groups to ldap and affect r�viserCourrier to BO

		taskService.addCandidateGroup(
				taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				"Bureau d'ordre");

		return processInstance;
	}

	@Override
	public void r�viser(String idCourrier, boolean isValidated) {

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
		Map<String, Object> propriet�sCourrier = runtimeService.getVariables((idCourrier));
		propriet�sCourrier.replace("isValidated", true);
		this.taskService = processEngine.getTaskService();
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				propriet�sCourrier);
		this.taskService.addCandidateGroup(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				propriet�sCourrier.get("d�partmentId").toString());
	}

	@Override
	public void traiterCourrier(String idCourrier, Map<String, Object> nouvellesPropriet�sCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		runtimeService.setVariables(processInstance.getId(), nouvellesPropriet�sCourrier);
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				nouvellesPropriet�sCourrier);
		if ((boolean) nouvellesPropriet�sCourrier.get("isFinished") != true) {
			taskService.addCandidateGroup(
					taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
					nouvellesPropriet�sCourrier.get("affectedTo").toString());
		}
	}

	@Override
	public void archiverCourrier(String idCourrier) {

	}

	// this method return all instances of courriers arriv�s Process
	@Override
	public List getListCourriersArriv�es() {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		List<ProcessInstance> listAllCourrierArriv� = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey("courriersArriv�s").list();
		return listAllCourrierArriv�;

	}

	// this method attach files to a process and return the folder id
	// it checks also if necessary folder are already created or not if not it
	// will create it
	@Override
	public String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id) {
		Folder folderCourrier = null;
		if (listePi�cesJointes != null) {
			DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl();
			FolderDaoImpl folderDaoImpl = new FolderDaoImpl(this.session);
			Folder courriersArriv�sFolderPerYear;
			Folder courriersArriv�sFolder;

			try {
				try {
					courriersArriv�sFolder = (Folder) folderDaoImpl.getFolderByPath("/courriersArriv�s");
				} catch (Exception myExction) {
					courriersArriv�sFolder = folderDaoImpl.createFolder((Folder) folderDaoImpl.getFolderByPath("/"),
							"courriersArriv�s");
				}
				courriersArriv�sFolderPerYear = (Folder) folderDaoImpl.getFolderByPath(
						courriersArriv�sFolder.getPath() + "/" + Calendar.getInstance().get(Calendar.YEAR));
			} catch (Exception myExction) {

				courriersArriv�sFolderPerYear = folderDaoImpl.createFolder(
						(Folder) folderDaoImpl.getFolderByPath("/courriersArriv�s"),
						Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			}
			folderCourrier = folderDaoImpl.createFolder(courriersArriv�sFolderPerYear, exp�diteur + id);
			for (int i = 0; i < listePi�cesJointes.size(); i++) {
				try {
					documentDaoImpl.inserte(listePi�cesJointes.get(i), folderCourrier);
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

	public CourriersArriv�sImpl() {
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
	public List<Task> getListActiveCourriersArriv�sParUser(String userName) {
		// TODO Auto-generated method stub
		List<Task> listTaskByProceeAndUser = this.taskService.createTaskQuery().processDefinitionKey("courriersArriv�s")
				.taskCandidateUser(userName).list();
		return listTaskByProceeAndUser;
	}

	@Override

	public List<String> getListFinishedCourrierArriv�PerUser(String userId) {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<String> listFinishedCourriersId = new ArrayList<>();
		List<HistoricProcessInstance> listFinishedCourriersArriv�Instances = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey("courriersArriv�s").finished().list();

		for (int j = 0; j < listFinishedCourriersArriv�Instances.size(); j++) {
			listFinishedCourriersId.add(listFinishedCourriersArriv�Instances.get(j).getId());
		}

		System.out.println(historyService.createHistoricProcessInstanceQuery().processDefinitionKey("courriersArriv�s")
				.finished().list().size());
		List<String> listFinishedCourriersInvolvedMrX = new ArrayList<>();
		for (int i = 0; i < historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
				.processDefinitionKey("courriersArriv�s").finished().list().size(); i++) {
			listFinishedCourriersInvolvedMrX
					.add(historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
							.processDefinitionKey("courriersArriv�s").finished().list().get(i).getProcessInstanceId());

		}

	
		return listFinishedCourriersInvolvedMrX;
	}

	@Override
	public List<Task> getListCourrierArriv�ParDirection(String directionName) {
		// TODO Auto-generated method stub

		List<Task> listTaskByDirection = this.taskService.createTaskQuery().processDefinitionKey("courriersArriv�s")
				.taskCandidateGroup(directionName).list();
		return listTaskByDirection;

	}

	@Override
	public void refuserCourrier(String idCourrier) {
		// TODO Auto-generated method stub
		Map<String, Object> propriet�sCourrier = runtimeService.getVariables((idCourrier));
		propriet�sCourrier.replace("isValidated", false);
		this.taskService = processEngine.getTaskService();
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				propriet�sCourrier);
		this.taskService.addCandidateGroup(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				"ROLE_Secr�taire G�n�rale");
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
