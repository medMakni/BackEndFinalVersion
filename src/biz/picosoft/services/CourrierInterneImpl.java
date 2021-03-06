package biz.picosoft.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.iap.Response;

import biz.picosoft.dao.CourrierDao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.CourrierDaoImpl;
import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.daoImpl.SociétéDaoImpl;
import biz.picosoft.entity.CourrierSortie;
import biz.picosoft.factory.CourrierFactory;
import biz.picosoft.mains.TestDao;

public class CourrierInterneImpl implements CourriersServices {
	ProcessEngine processEngine;
	Session session;
	RuntimeService runtimeService;
	TaskService taskService;
	FolderDaoImpl folderDaoImpl;
	CourrierDao courrierDao;
	SociétéDaoImpl sociétéDao;
	ContacteDaoImpl contacteDaoImpl;
	// this method create a mail process and attach its file to it by calling
	// the attach file method
	// and then attach the folder of the mail
	@Override
	public ProcessInstance créerCourrier(Map<String, Object> proprietésCourrier) {
		System.out.println("prop here" + proprietésCourrier);

		if ((boolean) proprietésCourrier.get("isValidated") != false) {
			RuntimeService runtimeService = processEngine.getRuntimeService();
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("CourriersInternes",
					proprietésCourrier);
			@SuppressWarnings("unchecked")
			List<File> listePiécesJointes = (List<File>) proprietésCourrier.get("listePiécesJointes");
			if (listePiécesJointes != null) {

				String idCourrierArrivéFolder = attachFiles(listePiécesJointes,
						(String) proprietésCourrier.get("expéditeur"), processInstance.getId());
				List<String> listOfFolderChildrens = this.folderDaoImpl
						.getAllChildrens((Folder) this.folderDaoImpl.getFolderById(idCourrierArrivéFolder));
				proprietésCourrier.put("idCourrierArrivéFolder", idCourrierArrivéFolder);
				Map<String, Object> commentHistory = new HashMap<>();
				proprietésCourrier.put("isFinished", false);
				proprietésCourrier.put("commentHistory", commentHistory);
				proprietésCourrier.replace("listePiécesJointes", listOfFolderChildrens);
				runtimeService.setVariable(processInstance.getId(), "listePiécesJointes", listOfFolderChildrens);
				proprietésCourrier.put("idCourrier", processInstance.getId());
				runtimeService.setVariables(processInstance.getId(), proprietésCourrier);
				
				// local db
				CourrierFactory courrierFactory = new CourrierFactory();
				CourrierSortie courrier = (CourrierSortie) courrierFactory.getCourrier("Courrier Interne");
				courrier.setContacte(
						this.contacteDaoImpl.getContactFromNom(proprietésCourrier.get("contacte").toString()));
				courrier.setDateCréation(proprietésCourrier.get("date").toString());
				courrier.setIdDocument(idCourrierArrivéFolder);
				courrier.setIdDépartement(proprietésCourrier.get("départmentId").toString());
				courrier.setIdProcess(processInstance.getId());
				courrier.setSociété(this.sociétéDao.getSociétéFromNom(proprietésCourrier.get("société").toString()));
				this.courrierDao.insert(courrier);
				// check if the starter is a cheff to know if will validate or
				// not
				if (isCheff(runtimeService.getVariable(processInstance.getId(), "starter").toString())) {
					// to know where to go in the exclusive gateway
					runtimeService.setVariable(processInstance.getId(), "isStarterChef", true);

					// complete the creation step
					this.taskService.complete(this.taskService.createTaskQuery()
							.processInstanceId(processInstance.getId()).list().get(0).getId());
					System.out.println("HOLA  " + runtimeService.getVariable(processInstance.getId(), "starter"));

					String déstinataire = runtimeService.getVariable(processInstance.getId(), "déstinataire")
							.toString();
					taskService.addCandidateGroup(taskService.createTaskQuery()
							.processInstanceId(processInstance.getId()).list().get(0).getId(),
							"chefs" + déstinataire.substring("Direction ".length()));
					this.folderDaoImpl.folderPermission(runtimeService.getVariables(processInstance.getId())
							.get("idCourrierArrivéFolder").toString(),
							"chefs" + déstinataire.substring("Direction ".length()));

				} else {
					// to know where to go in the exclusive gateway
					runtimeService.setVariable(processInstance.getId(), "isStarterChef", false);
					this.taskService.complete(

							this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0)
									.getId());
					// add the groups to ldap and affect réviserCourrier to BO
					String expéditeur = runtimeService.getVariable(processInstance.getId(), "expéditeur").toString();
					System.out.println("expéditeur " + expéditeur);
					taskService.addCandidateGroup(taskService.createTaskQuery()
							.processInstanceId(processInstance.getId()).list().get(0).getId(),
							"chefs" + expéditeur.substring("Direction ".length()));
					this.folderDaoImpl.folderPermission(runtimeService.getVariables(processInstance.getId())
							.get("idCourrierArrivéFolder").toString(),
							"chefs" + expéditeur.substring("Direction ".length()));

				}
				return processInstance;
			}
		} else {

			mettreAjour((String) proprietésCourrier.get("idCourrier"), proprietésCourrier);

		}

		return null;
	}

	@Override
	public void réviser(String idCourrier, boolean isValidated) {
		System.out.println("ertert"+isValidated);

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
		proprietésCourrier.replace("isChecked", true);
		System.out.println("ertert"+proprietésCourrier);
		this.taskService = processEngine.getTaskService();
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				proprietésCourrier);
		this.taskService.addCandidateGroup(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				"chefs" + proprietésCourrier.get("déstinataire").toString().substring("Direction ".length()));
		this.folderDaoImpl.folderPermission(proprietésCourrier.get("idCourrierArrivéFolder").toString(),
				"chefs" + proprietésCourrier.get("déstinataire").toString().substring("Direction ".length()));

	}

	// this method return all instances of courriers arrivés Process
	@Override
	public List<Map<String, Object>> getListCourriers() {
		String expéditeur;
		String société;
		String date;
		String objet;
		String idCourrier;
		RuntimeService runtimeService = processEngine.getRuntimeService();
		List<ProcessInstance> listAllCourrierArrivé = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey("CourriersInternes").list();
		List<Map<String, Object>> listVarsOfAllCourriers = new ArrayList<Map<String, Object>>();
		Map<String, Object> varsOfAnActiveProcess;
		for (int i = 0; i < listAllCourrierArrivé.size(); i++) {
			idCourrier = listAllCourrierArrivé.get(i).getProcessInstanceId();
			varsOfAnActiveProcess = new HashMap<String, Object>();
			expéditeur = (String) runtimeService.getVariable(listAllCourrierArrivé.get(i).getProcessInstanceId(),
					"expéditeur");
			société = (String) runtimeService.getVariable(listAllCourrierArrivé.get(i).getProcessInstanceId(),
					"société");
			date = (String) runtimeService.getVariable(listAllCourrierArrivé.get(i).getProcessInstanceId(), "dateOut");
			objet = (String) runtimeService.getVariable(listAllCourrierArrivé.get(i).getProcessInstanceId(), "objet");
			varsOfAnActiveProcess.put("idCourrier", idCourrier);
			varsOfAnActiveProcess.put("expéditeur", expéditeur);
			varsOfAnActiveProcess.put("société", société);
			varsOfAnActiveProcess.put("date", date);
			varsOfAnActiveProcess.put("objet", objet);
			listVarsOfAllCourriers.add(varsOfAnActiveProcess);
		}
		return listVarsOfAllCourriers;

	}

	// this method attach files to a process and return the folder id
	// it checks also if necessary folder are already created or not if not it
	// will create it
	@Override
	public String attachFiles(List<File> listePiécesJointes, String expéditeur, String id) {
		Folder folderCourrier = null;
		if (listePiécesJointes != null) {
			DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl();

			Folder CourriersInternesFolderPerYear;
			Folder CourriersInternesFolder;

			try {
				try {
					CourriersInternesFolder = (Folder) this.folderDaoImpl.getFolderByPath("/CourriersInternes");
				} catch (Exception myExction) {
					CourriersInternesFolder = this.folderDaoImpl
							.createFolder((Folder) this.folderDaoImpl.getFolderByPath("/"), "CourriersInternes");
				}
				CourriersInternesFolderPerYear = (Folder) this.folderDaoImpl.getFolderByPath(
						CourriersInternesFolder.getPath() + "/" + Calendar.getInstance().get(Calendar.YEAR));
			} catch (Exception myExction) {

				CourriersInternesFolderPerYear = this.folderDaoImpl.createFolder(
						(Folder) this.folderDaoImpl.getFolderByPath("/CourriersInternes"),
						Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			}
			folderCourrier = this.folderDaoImpl.createFolder(CourriersInternesFolderPerYear, expéditeur + id);
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

	public CourrierInterneImpl() {
		super();
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		this.processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		this.runtimeService = processEngine.getRuntimeService();
		this.taskService = processEngine.getTaskService();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		session = ctx.getBean(Session.class);
		folderDaoImpl = new FolderDaoImpl(this.session);
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.courrierDao=(CourrierDaoImpl) context.getBean("courrierDaoImpl");
		this.sociétéDao = (SociétéDaoImpl) context.getBean("sociétéDaoImpl");
		this.contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	// this method will return vars of active process per user
	public List<Map<String, Object>> getListActiveCourriersParUser(String userName) {
		// list of vars of active process per user
		List<Map<String, Object>> listVarsOfActiveProcesPerUser = new ArrayList<Map<String, Object>>();
		// get the list active tasks per user
		List<Task> listTaskByProceeAndUser = this.taskService.createTaskQuery()
				.processDefinitionKey("CourriersInternes").taskCandidateUser(userName).list();
		System.out.println("hhhh" + listTaskByProceeAndUser);
		System.out.println("Coucou cheff" + listTaskByProceeAndUser);
		if (listTaskByProceeAndUser != null) {
			// this will hold the vars of one task of the list of active process
			// per user
			String idCourrier;
			String expéditeur;
			String société;
			String date;
			String objet;
			Map<String, Object> varsOfAnActiveProcessPerUser;
			for (int i = 0; i < listTaskByProceeAndUser.size(); i++) {
				varsOfAnActiveProcessPerUser = new HashMap<String, Object>();
				idCourrier = listTaskByProceeAndUser.get(i).getProcessInstanceId();
				expéditeur = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"expéditeur");
				société = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"société");
				date = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"dateOut");
				objet = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"objet");
				System.out.println("la soci" + société);
				varsOfAnActiveProcessPerUser.put("expéditeur", expéditeur);
				varsOfAnActiveProcessPerUser.put("date", date);
				varsOfAnActiveProcessPerUser.put("objet", objet);
				varsOfAnActiveProcessPerUser.put("société", société);
				varsOfAnActiveProcessPerUser.put("idCourrier", idCourrier);
				listVarsOfActiveProcesPerUser.add(varsOfAnActiveProcessPerUser);

			}
		}
		return listVarsOfActiveProcesPerUser;
	}

	@Override
	public List<Map<String, Object>> getListFinishedCourrierPerUser(String userId) {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<String> listFinishedCourriersId = new ArrayList<>();
		List<HistoricProcessInstance> listFinishedCourriersArrivéInstances = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey("CourriersInternes").finished().list();

		for (int j = 0; j < listFinishedCourriersArrivéInstances.size(); j++) {
			listFinishedCourriersId.add(listFinishedCourriersArrivéInstances.get(j).getId());
		}

		System.out.println(historyService.createHistoricProcessInstanceQuery().processDefinitionKey("CourriersInternes")
				.finished().list().size());
		List<String> listFinishedCourriersInvolvedMrX = new ArrayList<>();
		for (int i = 0; i < historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
				.processDefinitionKey("CourriersInternes").finished().list().size(); i++) {
			listFinishedCourriersInvolvedMrX
					.add(historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId)
							.processDefinitionKey("CourriersInternes").finished().list().get(i).getProcessInstanceId());

		}

		List<Map<String, Object>> listVarsOfFinshedCourrier = new ArrayList<>();
		Map<String, Object> parameter;
		String varName;
		Object varValue;
		for (int i = 0; i < listFinishedCourriersInvolvedMrX.size(); i++) {
			parameter = new HashMap<String, Object>();
			for (int j = 0; j < historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(listFinishedCourriersInvolvedMrX.get(i)).orderByVariableName().desc().list()
					.size(); j++) {
				varName = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(listFinishedCourriersInvolvedMrX.get(i)).orderByVariableName().desc().list()
						.get(j).getVariableName();
				varValue = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(listFinishedCourriersInvolvedMrX.get(i)).orderByVariableName().desc().list()
						.get(j).getValue();
				parameter.put(varName, varValue);
			}
			listVarsOfFinshedCourrier.add(parameter);
		}

		return listVarsOfFinshedCourrier;
	}

	@Override
	public List<Map<String, Object>> getListActiveCourrierParDirection(String directionName) {
		// TODO Auto-generated method stub

		// list of vars of active process per direction
		List<Map<String, Object>> listVarsOfActiveProcesPerDirection = new ArrayList<Map<String, Object>>();
		// get the list active tasks per user
		List<Task> listOfActiveTasksByDirection = this.taskService.createTaskQuery()
				.processDefinitionKey("CourriersInternes").processVariableValueEquals("déstinataire", directionName)
				.list();

		if (listOfActiveTasksByDirection != null) {
			// this will hold the vars of one task of the list of active process
			// per direction
			Map<String, Object> varsOfAnActiveProcessPerDirection;
			for (int i = 0; i < listOfActiveTasksByDirection.size(); i++) {
				varsOfAnActiveProcessPerDirection = runtimeService
						.getVariables(listOfActiveTasksByDirection.get(i).getProcessInstanceId());
				listVarsOfActiveProcesPerDirection.add(varsOfAnActiveProcessPerDirection);
			}
		}
		return listVarsOfActiveProcesPerDirection;

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
		this.taskService.addCandidateUser(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				runtimeService.getVariable(idCourrier, "starter").toString());
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
	public Map<String, Object> getCourrierDetails(String idCourrier) throws Exception {
		Map<String, Object> courriersDetails = runtimeService.getVariables(idCourrier);
		courriersDetails.put("idCourrier", idCourrier);
		// List<CmisObject> listePiéceJointeObject = new ArrayList<>();
		// List<String> listPiéceJointeId = new ArrayList<>();
		/*
		 * listPiéceJointeId = (List<String>)
		 * courriersDetails.get("listePiécesJointes"); DocumentDaoImpl
		 * documentDaoImpl = new DocumentDaoImpl(); for (int i = 0; i <
		 * listPiéceJointeId.size(); i++) {
		 * listePiéceJointeObject.add(documentDaoImpl.getDocument(
		 * listPiéceJointeId.get(i).substring(0,
		 * listPiéceJointeId.get(i).indexOf(";")))); }
		 * courriersDetails.put("listePiéceJointeObject",
		 * listePiéceJointeObject);
		 */

		/*
		 * DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl(); CmisObject
		 * doc = documentDaoImpl.getDocument(
		 * "workspace://SpacesStore/bda6fb3c-b19c-45c6-a1f2-0d70b2492ff5");
		 * courriersDetails.put("doc", doc);
		 * System.out.println(doc.getProperties());
		 */

		// List<CmisObject> listePiéceJointeObject = new ArrayList<>();
		// List<String> listPiéceJointeId = new ArrayList<>();
		// listPiéceJointeId = (List<String>)
		// courriersDetails.get("listePiécesJointes");
		/*
		 * DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl(); for (int i =
		 * 0; i < listPiéceJointeId.size(); i++) {
		 * listePiéceJointeObject.add(documentDaoImpl.getDocument(
		 * listPiéceJointeId.get(i).substring(0,
		 * listPiéceJointeId.get(i).indexOf(";"))));
		 * 
		 * System.out.println("idddddd"+listPiéceJointeId.get(i).substring(0,
		 * listPiéceJointeId.get(i).indexOf(";")));}
		 * courriersDetails.put("listePiéceJointeObject",
		 * listePiéceJointeObject);
		 */

		return courriersDetails;
	}

	@Override
	public ResponseEntity<InputStreamResource> postFile() throws Exception {

		DocumentDaoImpl dao = new DocumentDaoImpl();

		Document docCmis = (Document) dao.getDocument("workspace://SpacesStore/d33081a5-c862-46d7-8852-2c73b502a16b");
		byte[] myByteArray = readContent(docCmis.getContentStream().getStream());

		// ClassPathResource myFile = new
		// ClassPathResource(docCmis.getContentStreamFileName());
		// System.out.println("eeeee"+pdfFile);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return null;/*
					 * ResponseEntity.ok().headers(headers).contentLength(
					 * myByteArray.length)
					 * .contentType(MediaType.parseMediaType(
					 * "application/octet-stream")) .body(new
					 * InputStreamResource(docCmis.getContentStream().getStream(
					 * )));
					 */

	}

	protected static byte[] readContent(InputStream stream) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[4096];
		int b;
		while ((b = stream.read(buffer)) > -1) {
			baos.write(buffer, 0, b);
		}

		return baos.toByteArray();
	}

	@Override
	public int getNbrOfFinishedCourrierParDirection(String directionName) {

		HistoryService historyService = processEngine.getHistoryService();

		List<HistoricProcessInstance> listOfFnishedProcesPerDirection = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey("CourriersInternes").finished()
				.variableValueEquals("expéditeur", directionName).list();

		return listOfFnishedProcesPerDirection.size();

	}

	@Override
	public List<Map<String, Object>> getFinishedCourrier() {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<String> listFinishedCourriersId = new ArrayList<>();
		List<HistoricProcessInstance> listFinishedCourriersArrivéInstances = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey("CourriersInternes").finished().list();

		for (int j = 0; j < listFinishedCourriersArrivéInstances.size(); j++) {
			listFinishedCourriersId.add(listFinishedCourriersArrivéInstances.get(j).getId());
		}

		System.out.println(historyService.createHistoricProcessInstanceQuery().processDefinitionKey("CourriersInternes")
				.finished().list().size());
		List<Map<String, Object>> listVarsOfFinshedCourrier = new ArrayList<>();
		Map<String, Object> parameter;
		String varName;
		Object varValue;
		for (int i = 0; i < historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey("CourriersInternes").finished().list().size(); i++) {
			parameter = new HashMap<String, Object>();
			for (int j = 0; j < historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(listFinishedCourriersArrivéInstances.get(i).getId()).orderByVariableName().desc()
					.list().size(); j++) {
				varName = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(listFinishedCourriersArrivéInstances.get(i).getId()).orderByVariableName()
						.desc().list().get(j).getVariableName();
				varValue = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(listFinishedCourriersArrivéInstances.get(i).getId()).orderByVariableName()
						.desc().list().get(j).getValue();
				parameter.put(varName, varValue);
			}
			listVarsOfFinshedCourrier.add(parameter);
		}

		return listVarsOfFinshedCourrier;
	}

	public boolean isCheff(String uid) {

		boolean isChef = false;
		List<Group> groupList = processEngine.getIdentityService().createGroupQuery().groupMember(uid).list();
		for (Group group : groupList) {
			String groupName = group.getName();
			if (groupName.contains("Chef")) {
				isChef = true;
				return isChef;
			}

		}

		return isChef;
	}

	@Override
	public void traiterCourrier(Map<String, Object> map) {
		// TODO Auto-generated method stub
		RuntimeService runtimeService = processEngine.getRuntimeService();

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId((String) map.get("idCourrier")).singleResult();
		Map<String, String> commentHistory = (Map<String, String>) runtimeService
				.getVariable((String) map.get("idCourrier"), "commentHistory");
		commentHistory.put((String) map.get("username"), (String) map.get("annotation"));
		runtimeService.setVariable((String) map.get("idCourrier"), "commentHistory", commentHistory);
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId());
		if ((boolean) runtimeService.getVariable((String) map.get("idCourrier"), "isFinished") != true) {

			taskService.addCandidateGroup(
					taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),

					map.get("affectedTo").toString());
			this.folderDaoImpl.folderPermission(
					runtimeService.getVariables(processInstance.getId()).get("idCourrierArrivéFolder").toString(),
					map.get("affectedTo").toString());

		}
	}

	@Override
	public void mettreAjour(String idCourrier, Map<String, Object> nouvellesProprietésCourrier) {
		// TODO Auto-generated method stub

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		runtimeService.setVariables(processInstance.getId(), nouvellesProprietésCourrier);
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				nouvellesProprietésCourrier);
		nouvellesProprietésCourrier.replace("isValidated", true);
		// add the groups to ldap and affect réviserCourrier to BO
		String expéditeur = runtimeService
				.getVariable((String) nouvellesProprietésCourrier.get("idCourrier"), "expéditeur").toString();
		taskService.addCandidateGroup(taskService.createTaskQuery()
				.processInstanceId((String) nouvellesProprietésCourrier.get("idCourrier")).list().get(0).getId(),
				"chefs" + expéditeur.substring("Direction ".length()));
		this.	folderDaoImpl.folderPermission(runtimeService.getVariables(processInstance.getId()).get("idCourrierArrivéFolder").toString(), 	"chefs" + expéditeur.substring("Direction ".length()));
		
		System.out.println("Coucou Chefs2" + expéditeur.substring("Direction ".length()));
	}

	@Override
	public void archiverCourrier(String idCourrier) {
		runtimeService.setVariable(idCourrier, "isFinished", true);
		this.taskService
				.complete(this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId());

	}

	@Override
	public void delete(String idCourrier) {
		// TODO Auto-generated method stub
		runtimeService.deleteProcessInstance(idCourrier, "Supprimer définitivement le courrier");
	}

	@Override
	public ResponseEntity<InputStreamResource> postFile(String id, String nbreCourrier) throws IOException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getCourrierByStarter(String uid) {
		List<Map<String, Object>> listCourrierByStarter = new ArrayList<Map<String, Object>>();
		// TODO Auto-generated method stub
		HistoryService historyService = this.processEngine.getHistoryService();
		List<HistoricProcessInstance> listFinishedCourriersInstances = historyService
				.createHistoricProcessInstanceQuery().variableValueEquals("starter", uid).finished().list();
		List<ProcessInstance> listActiveCourrierByStarter = runtimeService.createProcessInstanceQuery()
				.variableValueEquals("starter", uid).list();
		for (ProcessInstance processInstance : listActiveCourrierByStarter) {
			listCourrierByStarter.add(runtimeService.getVariables(processInstance.getId()));
		}
		for (HistoricProcessInstance processInstance : listFinishedCourriersInstances) {
			listCourrierByStarter.add(processInstance.getProcessVariables());
			Map<String, Object> parameter;
			String varName;
			Object varValue;
			parameter = new HashMap<String, Object>();
			for (int j = 0; j < historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getId()).orderByVariableName().desc().list().size(); j++) {
				varName = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(processInstance.getId()).orderByVariableName().desc().list().get(j)
						.getVariableName();
				varValue = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(processInstance.getId()).orderByVariableName().desc().list().get(j)
						.getValue();
				parameter.put(varName, varValue);
			}
			listCourrierByStarter.add(parameter);
		}

		return listCourrierByStarter;
	}

	@Override
	public List<Map<String, Object>> getActiveAndFinishedCourriersPerUser(String uid) {
		List<Map<String, Object>> ActiveAndFinishedCourriersPerUser = getListActiveCourriersParUser(uid);
		ActiveAndFinishedCourriersPerUser.addAll(getListFinishedCourrierPerUser(uid));
		return ActiveAndFinishedCourriersPerUser;
	}

}
