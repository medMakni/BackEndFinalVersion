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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.iap.Response;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.mains.TestDao;

public class CourrierInterneImpl implements CourriersServices {

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
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("CourriersInternes",
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
		// TODO do not forget to change that with of the owner
		taskService.addCandidateGroup(
				taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				"chefsIT");

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
				.processDefinitionKey("CourriersInternes").list();
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
			Folder CourriersInternesFolderPerYear;
			Folder CourriersInternesFolder;

			try {
				try {
					CourriersInternesFolder = (Folder) folderDaoImpl.getFolderByPath("/courriersInternes");
				} catch (Exception myExction) {
					CourriersInternesFolder = folderDaoImpl.createFolder((Folder) folderDaoImpl.getFolderByPath("/"),
							"courriersInternes");
				}
				CourriersInternesFolderPerYear = (Folder) folderDaoImpl.getFolderByPath(
						CourriersInternesFolder.getPath() + "/" + Calendar.getInstance().get(Calendar.YEAR));
			} catch (Exception myExction) {

				CourriersInternesFolderPerYear = folderDaoImpl.createFolder(
						(Folder) folderDaoImpl.getFolderByPath("/courriersInternes"),
						Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			}
			folderCourrier = folderDaoImpl.createFolder(CourriersInternesFolderPerYear, exp�diteur + id);
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

	public CourrierInterneImpl() {
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
	// this method will return vars of active process per user
	public List<Map<String, Object>> getListActiveCourriersArriv�sParUser(String userName) {
		// list of vars of active process per user
		List<Map<String, Object>> listVarsOfActiveProcesPerUser = new ArrayList<Map<String, Object>>();
		// get the list active tasks per user
		List<Task> listTaskByProceeAndUser = this.taskService.createTaskQuery().processDefinitionKey("CourriersInternes")
				.taskCandidateUser(userName).list();

		if (listTaskByProceeAndUser != null) {
			// this will hold the vars of one task of the list of active process
			// per user
			Map<String, Object> varsOfAnActiveProcessPerUser;
			for (int i = 0; i < listTaskByProceeAndUser.size(); i++) {
				varsOfAnActiveProcessPerUser = runtimeService
						.getVariables(listTaskByProceeAndUser.get(i).getProcessInstanceId());
				listVarsOfActiveProcesPerUser.add(varsOfAnActiveProcessPerUser);
			}
		}
		return listVarsOfActiveProcesPerUser;
	}

	@Override

	public List<String> getListFinishedCourrierArriv�PerUser(String userId) {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<String> listFinishedCourriersId = new ArrayList<>();
		List<HistoricProcessInstance> listFinishedCourriersArriv�Instances = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey("courri�Sortie").finished().list();

		for (int j = 0; j < listFinishedCourriersArriv�Instances.size(); j++) {
			listFinishedCourriersId.add(listFinishedCourriersArriv�Instances.get(j).getId());
		}

		System.out.println(historyService.createHistoricProcessInstanceQuery().processDefinitionKey("courri�Sortie")
				.finished().list().size());
		List<String> listFinishedCourriersInvolvedMrX = new ArrayList<>();
		for (int i = 0; i < historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
				.processDefinitionKey("courri�Sortie").finished().list().size(); i++) {
			listFinishedCourriersInvolvedMrX
					.add(historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
							.processDefinitionKey("courri�Sortie").finished().list().get(i).getProcessInstanceId());

		}

		return listFinishedCourriersInvolvedMrX;
	}

	@Override
	public List<Map<String, Object>> getListActiveCourrierArriv�ParDirection(String directionName) {
		// TODO Auto-generated method stub

		// list of vars of active process per direction
		List<Map<String, Object>> listVarsOfActiveProcesPerDirection = new ArrayList<Map<String, Object>>();
		// get the list active tasks per user
		List<Task> listOfActiveTasksByDirection = this.taskService.createTaskQuery()
				.processDefinitionKey("CourriersInternes").taskCandidateGroup(directionName).list();

		if (listOfActiveTasksByDirection != null) {
			// this will hold the vars of one task of the list of active process
			// per direction
			Map<String, Object> varsOfAnActiveProcessPerUser;
			for (int i = 0; i < listOfActiveTasksByDirection.size(); i++) {
				varsOfAnActiveProcessPerUser = runtimeService
						.getVariables(listOfActiveTasksByDirection.get(i).getProcessInstanceId());
				listVarsOfActiveProcesPerDirection.add(varsOfAnActiveProcessPerUser);
			}
		}
		return listVarsOfActiveProcesPerDirection;

	}

	@Override
	public void refuserCourrier(String idCourrier) {
		// TODO Auto-generated method stub
		ProcessInstance processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		Map<String, Object> propriet�sCourrier = runtimeService.getVariables((idCourrier));
		propriet�sCourrier.replace("isValidated", false);
		this.taskService = processEngine.getTaskService();
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				propriet�sCourrier);
 
				this.taskService.addCandidateUser (
						this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(), this.runtimeService.getVariable(processInstance.getId(), "starter").toString());
 
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

	@Override
	public Map<String, Object> getCourrierDetails(String idCourrier) {
		Map<String, Object> courriersDetails = runtimeService.getVariables(idCourrier);
		return courriersDetails;
	}

	@Override
	public int getNbrOfFinishedCourrierArriv�ParDirection(String directionName) {
		// TODO Auto-generated method stub
		return (Integer) null;
	}

	@Override
	public List<Map<String, Object>>  getFinishedCourrier() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity<InputStreamResource> postFile() throws IOException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
