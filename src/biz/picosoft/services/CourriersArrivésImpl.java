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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import biz.picosoft.dao.CourrierDao;
import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.CourrierDaoImpl;
import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.daoImpl.Soci�t�DaoImpl;
import biz.picosoft.entity.CourrierSortie;
import biz.picosoft.factory.CourrierFactory;
import biz.picosoft.mains.TestDao;

@Service
public class CourriersArriv�sImpl implements CourriersServices {

	ProcessEngine processEngine;
	Session session;
	FolderDaoImpl folderDaoImpl;
	RuntimeService runtimeService;
	TaskService taskService;
	/*CourrierDao courrierDao;
	Soci�t�DaoImpl soci�t�Dao;
	ContacteDaoImpl contacteDaoImpl;
*/
	@Override
	// this method create a mail process and attach its file to it by calling
	// the attach file method
	// and then attach the folder of the mail

	public ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier) {
		System.out.println("prop here" + propriet�sCourrier);

		if ((boolean) propriet�sCourrier.get("isValidated") != false) {
			RuntimeService runtimeService = processEngine.getRuntimeService();
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("courriersArriv�s",
					propriet�sCourrier);
			@SuppressWarnings("unchecked")
			List<File> listePi�cesJointes = (List<File>) propriet�sCourrier.get("listePi�cesJointes");
			if (listePi�cesJointes != null) {
				String idCourrierArriv�Folder = attachFiles(listePi�cesJointes,
						(String) propriet�sCourrier.get("exp�diteur"), processInstance.getId());
				List<String> listOfFolderChildrens = this.folderDaoImpl
						.getAllChildrens((Folder) this.folderDaoImpl.getFolderById(idCourrierArriv�Folder));
				propriet�sCourrier.put("idCourrierArriv�Folder", idCourrierArriv�Folder);
				Map<String, Object> commentHistory = new HashMap<>();
				propriet�sCourrier.put("isFinished", false);
				propriet�sCourrier.put("commentHistory", commentHistory);
				propriet�sCourrier.replace("listePi�cesJointes", listOfFolderChildrens);
				runtimeService.setVariable(processInstance.getId(), "listePi�cesJointes", listOfFolderChildrens);
				propriet�sCourrier.put("idCourrier", processInstance.getId());
				runtimeService.setVariables(processInstance.getId(), propriet�sCourrier);

				/* local db
				CourrierFactory courrierFactory = new CourrierFactory();
				CourrierSortie courrier = (CourrierSortie) courrierFactory.getCourrier("Courrier Arriv�");
				courrier.setContacte(
						this.contacteDaoImpl.getContactFromNom(propriet�sCourrier.get("contacte").toString()));
				courrier.setDateCr�ation(propriet�sCourrier.get("date").toString());
				courrier.setIdDocument(idCourrierArriv�Folder);
				courrier.setIdD�partement(propriet�sCourrier.get("d�partmentId").toString());
				courrier.setIdProcess(processInstance.getId());
				courrier.setSoci�t�(this.soci�t�Dao.getSoci�t�FromNom(propriet�sCourrier.get("soci�t�").toString()));
				this.courrierDao.insert(courrier);*/
				this.taskService.complete(this.taskService.createTaskQuery().processInstanceId(processInstance.getId())
						.list().get(0).getId());
				// add the groups to ldap and affect r�viserCourrier to BO

				taskService.addCandidateGroup(
						taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
						"Bureau d'ordre");
				// Alfresco Folder Security
				this.folderDaoImpl.folderPermission(propriet�sCourrier.get("idCourrierArriv�Folder").toString(),
						"Bureau d'ordre");
				return processInstance;
			}
		} else {
			propriet�sCourrier.replace("isValidated", true);
			mettreAjour((String) propriet�sCourrier.get("idCourrier"), propriet�sCourrier);
		}
		return null;

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
		System.out.println("nkl" + idCourrier);
		Map<String, Object> propriet�sCourrier = runtimeService.getVariables((idCourrier));
		propriet�sCourrier.replace("isValidated", true);
		propriet�sCourrier.replace("isChecked", true);
		this.taskService = processEngine.getTaskService();
		System.out.println("����" + propriet�sCourrier.get("d�partmentId"));
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				propriet�sCourrier);
		this.taskService.addCandidateGroup(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				"chefs" + propriet�sCourrier.get("d�partmentId").toString().substring("Direction ".length()));
		// Alfresco Folder Security
		this.folderDaoImpl.folderPermission(propriet�sCourrier.get("idCourrierArriv�Folder").toString(),
				"chefs" + propriet�sCourrier.get("d�partmentId").toString().substring("Direction ".length()));

	}

	@Override
	public void traiterCourrier(Map<String, Object> map) {
		System.out.println("my map " + map.get("username"));
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
					(String) map.get("idDepartement"));

			this.folderDaoImpl.folderPermission(
					runtimeService.getVariables(processInstance.getId()).get("idCourrierArriv�Folder").toString(),
					(String) map.get("idDepartement"));

		}

	}

	@Override
	public void archiverCourrier(String idCourrier) {
		runtimeService.setVariable(idCourrier, "isFinished", true);
		this.taskService
				.complete(this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId());

	}

	// this method return all instances of courriers arriv�s Process
	@Override
	public List<Map<String, Object>> getListCourriers() {
		String exp�diteur;
		String soci�t�;
		String date;
		String objet;
		String idCourrier;
		RuntimeService runtimeService = processEngine.getRuntimeService();
		List<ProcessInstance> listAllCourrierArriv� = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey("courriersArriv�s").list();
		List<Map<String, Object>> listVarsOfAllCourriers = new ArrayList<Map<String, Object>>();
		Map<String, Object> varsOfAnActiveProcess;
		for (int i = 0; i < listAllCourrierArriv�.size(); i++) {
			idCourrier = listAllCourrierArriv�.get(i).getProcessInstanceId();
			varsOfAnActiveProcess = new HashMap<String, Object>();
			exp�diteur = (String) runtimeService.getVariable(listAllCourrierArriv�.get(i).getProcessInstanceId(),
					"exp�diteur");
			soci�t� = (String) runtimeService.getVariable(listAllCourrierArriv�.get(i).getProcessInstanceId(),
					"soci�t�");
			date = (String) runtimeService.getVariable(listAllCourrierArriv�.get(i).getProcessInstanceId(), "dateOut");
			objet = (String) runtimeService.getVariable(listAllCourrierArriv�.get(i).getProcessInstanceId(), "objet");
			varsOfAnActiveProcess.put("idCourrier", idCourrier);
			varsOfAnActiveProcess.put("exp�diteur", exp�diteur);
			varsOfAnActiveProcess.put("soci�t�", soci�t�);
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
	public String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id) {
		Folder folderCourrier = null;
		if (listePi�cesJointes != null) {
			DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl();

			Folder courriersArriv�sFolderPerYear;
			Folder courriersArriv�sFolder;

			try {
				try {
					courriersArriv�sFolder = (Folder) this.folderDaoImpl.getFolderByPath("/courriersArriv�s");
				} catch (Exception myExction) {
					courriersArriv�sFolder = this.folderDaoImpl
							.createFolder((Folder) this.folderDaoImpl.getFolderByPath("/"), "courriersArriv�s");
				}
				courriersArriv�sFolderPerYear = (Folder) this.folderDaoImpl.getFolderByPath(
						courriersArriv�sFolder.getPath() + "/" + Calendar.getInstance().get(Calendar.YEAR));
			} catch (Exception myExction) {

				courriersArriv�sFolderPerYear = this.folderDaoImpl.createFolder(
						(Folder) this.folderDaoImpl.getFolderByPath("/courriersArriv�s"),
						Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			}
			folderCourrier = this.folderDaoImpl.createFolder(courriersArriv�sFolderPerYear, exp�diteur + id);
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
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		this.processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		this.runtimeService = processEngine.getRuntimeService();
		this.taskService = processEngine.getTaskService();

		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		session = ctx.getBean(Session.class);
		/*@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		this.folderDaoImpl = new FolderDaoImpl(this.session);
		this.courrierDao=(CourrierDaoImpl) context.getBean("courrierDaoImpl");
		this.soci�t�Dao = (Soci�t�DaoImpl) context.getBean("soci�t�DaoImpl");
		this.contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");*/
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
		List<Task> listTaskByProceeAndUser = this.taskService.createTaskQuery().processDefinitionKey("courriersArriv�s")
				.taskCandidateUser(userName).list();
		System.out.println("makmak" + listTaskByProceeAndUser);

		if (listTaskByProceeAndUser != null) {
			// this will hold the vars of one task of the list of active process
			// per user
			String idCourrier;
			String exp�diteur;
			String soci�t�;
			String date;
			String objet;
			Map<String, Object> varsOfAnActiveProcessPerUser;
			for (int i = 0; i < listTaskByProceeAndUser.size(); i++) {
				varsOfAnActiveProcessPerUser = new HashMap<String, Object>();
				idCourrier = listTaskByProceeAndUser.get(i).getProcessInstanceId();
				exp�diteur = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"exp�diteur");
				soci�t� = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"soci�t�");
				date = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"dateOut");
				objet = (String) runtimeService.getVariable(listTaskByProceeAndUser.get(i).getProcessInstanceId(),
						"objet");
				System.out.println("la soci" + soci�t�);
				varsOfAnActiveProcessPerUser.put("exp�diteur", exp�diteur);
				varsOfAnActiveProcessPerUser.put("date", date);
				varsOfAnActiveProcessPerUser.put("objet", objet);
				varsOfAnActiveProcessPerUser.put("soci�t�", soci�t�);
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
		List<HistoricProcessInstance> listFinishedCourriersArriv�Instances = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey("courriersArriv�s").finished().list();

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
				.processDefinitionKey("courriersArriv�s").processVariableValueEquals("d�stinataire", directionName)
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

	@Override
	public Map<String, Object> getCourrierDetails(String idCourrier) throws Exception {
		Map<String, Object> courriersDetails = runtimeService.getVariables(idCourrier);
		courriersDetails.put("idCourrier", idCourrier);
		return courriersDetails;
	}

	@Override

	public ResponseEntity<InputStreamResource> postFile(String id, String nbreCourrier) throws Exception {

		Map<String, Object> courriersDetails = runtimeService.getVariables(id);
		@SuppressWarnings("unchecked")
		List<String> pg = (List<String>) courriersDetails.get("listePi�cesJointes");
		System.out.println("azerty" + pg);
		DocumentDaoImpl dao = new DocumentDaoImpl();
		HttpHeaders headers = new HttpHeaders();
		byte[] myByteArray = null;
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		System.out.println("aaaa" + nbreCourrier);

		Document docCmis = ((Document) dao.getDocument(pg.get(Integer.parseInt(nbreCourrier))));

		myByteArray = readContent(docCmis.getContentStream().getStream());

		// ClassPathResource myFile = new
		// ClassPathResource(docCmis.getContentStreamFileName());
		// System.out.println("eeeee"+pdfFile);

		headers.add("filename" + Integer.parseInt(nbreCourrier), docCmis.getName());

		return ResponseEntity.ok().headers(headers).contentLength(myByteArray.length)
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(docCmis.getContentStream().getStream()));

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
				.createHistoricProcessInstanceQuery().processDefinitionKey("courriersArriv�s").finished()
				.variableValueEquals("d�stinataire", directionName).list();

		return listOfFnishedProcesPerDirection.size();

	}

	@Override
	public List<Map<String, Object>> getFinishedCourrier() {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<String> listFinishedCourriersId = new ArrayList<>();
		List<HistoricProcessInstance> listFinishedCourriersArriv�Instances = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey("courriersArriv�s").finished().list();

		for (int j = 0; j < listFinishedCourriersArriv�Instances.size(); j++) {
			listFinishedCourriersId.add(listFinishedCourriersArriv�Instances.get(j).getId());
		}

		System.out.println(historyService.createHistoricProcessInstanceQuery().processDefinitionKey("courriersArriv�s")
				.finished().list().size());
		List<Map<String, Object>> listVarsOfFinshedCourrier = new ArrayList<>();
		Map<String, Object> parameter;
		String varName;
		Object varValue;
		for (int i = 0; i < historyService.createHistoricProcessInstanceQuery().processDefinitionKey("courriersArriv�s")
				.finished().list().size(); i++) {
			parameter = new HashMap<String, Object>();
			for (int j = 0; j < historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(listFinishedCourriersArriv�Instances.get(i).getId()).orderByVariableName().desc()
					.list().size(); j++) {
				varName = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(listFinishedCourriersArriv�Instances.get(i).getId()).orderByVariableName()
						.desc().list().get(j).getVariableName();
				varValue = historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(listFinishedCourriersArriv�Instances.get(i).getId()).orderByVariableName()
						.desc().list().get(j).getValue();
				parameter.put(varName, varValue);
			}
			listVarsOfFinshedCourrier.add(parameter);
		}

		return listVarsOfFinshedCourrier;
	}

	@Override
	public void mettreAjour(String idCourrier, Map<String, Object> nouvellesPropriet�sCourrier) {
		// TODO Auto-generated method stub

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		runtimeService.setVariables(processInstance.getId(), nouvellesPropriet�sCourrier);
		this.taskService.complete(
				this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId(),
				nouvellesPropriet�sCourrier);
		this.taskService.addCandidateGroup(
				this.taskService.createTaskQuery().processInstanceId(idCourrier).list().get(0).getId(),
				"Bureau d'ordre");

	}

	@Override
	public ResponseEntity<InputStreamResource> postFile() throws IOException, Exception {

		return null;
	}

	@Override
	public void delete(String idCourrier) {
		// TODO Auto-generated method stub
		runtimeService.deleteProcessInstance(idCourrier, "Supprimer d�finitivement le courrier");
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