package biz.picosoft.mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.services.CourriersArrivésImpl;
import biz.picosoft.services.CourriersArrivésServices;

@Configuration
public class TestDao {
	@Bean
	public Session getAlfrescoSession() {
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, "admin");
		parameter.put(SessionParameter.PASSWORD, "admin");

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/alfresco/cmisatom");
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		System.out.println(BindingType.ATOMPUB.value());
		// set the alfresco object factory
		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

		// create session
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Session session = factory.getRepositories(parameter).get(0).createSession();
		return session;
	}

	public static void main(String[] args) throws FileNotFoundException {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn")
				.deploy().getId();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		System.out.println("idddddd" + deploymentId);

		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		
		CourriersArrivésServices courriersArrivésImplLocal = new CourriersArrivésImpl();
		System.out.println("robert is active tasks" +courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("rb"));
		
		/*Map<String, Object> proprietés = new HashMap<String, Object>();
=======
<<<<<<< HEAD
	/*	CourriersArrivésServices courriersArrivésImplLocal = new CourriersArrivésImpl();
		Map<String, Object> proprietés = new HashMap<String, Object>();
>>>>>>> branch 'master' of https://github.com/medMakni/BackEndFinalVersion.git
=======
	 	CourriersArrivésServices courriersArrivésImplLocal = new CourriersArrivésImpl();
	 	Map<String, Object> proprietés = new HashMap<String, Object>();
		proprietés.put("date", "19-5-5");
		proprietés.put("départmentId", "chefsIT");
		proprietés.put("isValidated", true);
		proprietés.put("expéditeur", "noz");
		proprietés.put("isFinished", false);
		proprietés.put("société", "Steg");
		proprietés.put("objet", "facture");
		File file = new File("D://cv/cover letter.docx");
		List listePiécesJointes = new ArrayList<>();
		listePiécesJointes.add(file);
		proprietés.put("listePiécesJointes", listePiécesJointes);
		ProcessInstance processInstance = courriersArrivésImplLocal.créerCourrier(proprietés);
	 
	 
		System.out.println("active tasks for weld ankoud :p "+courriersArrivésImplLocal.getListCourriersArrivées());
		
		 
	/*	Map<String, Object> proprietés = new HashMap<String, Object>();
>>>>>>> branch 'master' of https://github.com/medMakni/BackEndFinalVersion.git
		proprietés.put("date", "19-5-5");
		proprietés.put("départmentId", "chefsIT");
		proprietés.put("isValidated", true);
		proprietés.put("expéditeur", "Steg");
		proprietés.put("isFinished", false);
		File file = new File("D://cv/cover letter.docx");
		List listePiécesJointes = new ArrayList<>();
		listePiécesJointes.add(file);
		proprietés.put("listePiécesJointes", listePiécesJointes);
		ProcessInstance processInstance = courriersArrivésImplLocal.créerCourrier(proprietés);
<<<<<<< HEAD
		System.out.println("BO"+courriersArrivésImplLocal.getListCourriersArrivésParUser("rb"));
		courriersArrivésImplLocal.réviser(processInstance.getId(), false);     
		System.out.println("secrét"+courriersArrivésImplLocal.getListCourriersArrivésParUser("ac"));
		
		//System.out.println(courriersArrivésImplLocal.getListCourriersArrivésParUser("fbm"));
		HistoryService historyService=((CourriersArrivésImpl) courriersArrivésImplLocal).getProcessEngine().getHistoryService();
		List<HistoricActivityInstance> historicActivityInstances = historyService.
				  createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).
				  orderByHistoricActivityInstanceStartTime().asc().list();
	 System.out.println(historicActivityInstances);
	 historicActivityInstances = historyService.
			  createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).
			  orderByHistoricActivityInstanceStartTime().asc().list();
		/*	ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("myProcess");
		List<Task> taskb = taskService.createTaskQuery().taskCandidateUser("fbm").list();
		System.out.println(taskb);*/
		System.out.println(courriersArrivésImplLocal.getListCourriersArrivées());
		/*ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("myProcess");
		 taskService.addCandidateUser(taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list().get(0).getId(), "fbm");
		 
			List<Task> taskByProceeAndUser = taskService.createTaskQuery().processDefinitionKey("myProcess").taskCandidateUser("mwm")
					.list();
			List<Task> taskb = taskService.createTaskQuery().taskCandidateUser("fbm").list();
			System.out.println(taskByProceeAndUser.size());
			System.out.println(taskb.size());*/
/*		System.out.println("BO" + courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("jm"));
		courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		System.out.println("chef It" + courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("rb"));
		proprietés.put("affectedTo", "secrétaireitù");
		proprietés.put("isFinished", false);
		courriersArrivésImplLocal.traiterCourrier(processInstance.getId(), proprietés);
		System.out.println("siz oactive tasks "
				+ taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().size());
	 
		proprietés.replace("isFinished", true);
		courriersArrivésImplLocal.traiterCourrier(processInstance.getId(), proprietés);
		System.out.println("siz oactive tasks lev 2 "
				+ taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().size());
		System.out.println(runtimeService.createProcessInstanceQuery().active().count());
		HistoryService historyService = processEngine.getHistoryService();
	System.out.println(courriersArrivésImplLocal.getListFinishedCourrierArrivéPerUser("ha"));*/
 
			//	 ena taw 3malt ili finished w process which include mr x
		// courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		// proprietés);
		/*
		 * //System.out.println(courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("fbm")); HistoryService
		 * historyService=((CourriersArrivésImpl)
		 * courriersArrivésImplLocal).getProcessEngine().getHistoryService();
		 * List<HistoricActivityInstance> historicActivityInstances =
		 * historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list();
		 * System.out.println(historicActivityInstances);
		 * historicActivityInstances = historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list();
		 */
		/*
		 * ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess"); List<Task>
		 * taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskb);
		 * System.out.println(courriersArrivésImplLocal.getListCourriersArrivées
		 * ()); /*ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess");
		 * taskService.addCandidateUser(taskService.createTaskQuery().
		 * processInstanceId(processInstance1.getId()).list().get(0).getId(),
		 * "fbm");
		 * 
		 * List<Task> taskByProceeAndUser =
		 * taskService.createTaskQuery().processDefinitionKey("myProcess").
		 * taskCandidateUser("mwm") .list(); List<Task> taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskByProceeAndUser.size());
		 * System.out.println(taskb.size());
		 */
		// Folder root = session.getRootFolder();
		// FolderDaoImpl folderDaoImpl = new FolderDaoImpl();
		// folderDaoImpl.createFolder(root, "fatma2");

		/*
		 * Folder root = session.getRootFolder(); FolderDaoImpl
		 * folderDaoImpl=new FolderDaoImpl(); ItemIterable<CmisObject>
		 * list=folderDaoImpl.getAllChildrens(root); for (CmisObject o : list) {
		 * System.out.println(o.getName()); }
		 */

		/*
		 * DocumentDaoImpl doi=new DocumentDaoImpl(); doi.setSession(session);
		 * File file=new File("C://cover letter.pdf"); //doi.inserte(file);
		 * ObjectId o=new ObjectId() {
		 * 
		 * public String getId() { // TODO Auto-generated method stub return
		 * "workspace://SpacesStore/0139211c-67e0-4d81-a3f0-918de3e56b5b"; } };
		 * CmisObject obj= doi.getDocument(o);
		 * System.out.println(obj.getName()); doi.inserte(file);
		 */

		/*
		 * DocumentDaoImpl doi=new DocumentDaoImpl(); doi.setSession(session);
		 * File file=new File("C://cover letter.pdf"); //doi.inserte(file);
		 * ObjectId o=new ObjectId() {
		 * 
		 * public String getId() { // TODO Auto-generated method stub return
		 * "workspace://SpacesStore/ee6d0267-4ebc-43a7-a938-1115a9ad14b6"; } };
		 * folderDaoImpl.setSession(session); CmisObject
		 * obj=folderDaoImpl.getFolderById(o) ;
		 * System.out.println(obj.getName()); doi.inserte(file,(Folder) obj);
		 */

		/*
		 * CourriersArrivésImpl courriersArrivésImplLocal = new
		 * CourriersArrivésImpl(); ProcessEngine processEngine =
		 * courriersArrivésImplLocal.getProcessEngine(); RuntimeService
		 * runtimeService = processEngine.getRuntimeService();
		 * 
		 * // courriersArrivésImplLocal.setSession(session); Map<String, Object>
		 * proprietés = new HashMap<String, Object>(); proprietés.put("date",
		 * "19-5-5"); proprietés.put("départmentId", "ROLE_ADMIN");
		 * proprietés.put("isValidated", false); proprietés.put("expéditeur",
		 * "Steg");
		 * 
		 * proprietés.put("listePiécesJointes", listePiécesJointes);
		 * ProcessInstance processInstance =
		 * courriersArrivésImplLocal.créerCourrier(proprietés);
		 * courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		 * System.out.println(runtimeService.getVariables(processInstance.getId(
		 * )).toString());
		 */

		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().toString());

		// ProcessInstance processInstance1 =
		// runtimeService.startProcessInstanceByKey("myProcess");
		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list().toString());

	}
}