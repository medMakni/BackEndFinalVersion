package biz.picosoft.mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.chemistry.opencmis.client.api.Folder;
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

import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.services.CourriersArrivésImplLocal;
@Configuration
public class TestDao {
	@Bean
	public Session getAlfrescoSession(){
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
		 Session session=ctx.getBean(Session.class);
		//Folder root = session.getRootFolder();
		//FolderDaoImpl folderDaoImpl = new FolderDaoImpl();
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
		List<File> listePiécesJointes = new ArrayList<File>();
		File file = new File("C://cover letter.pdf");
		listePiécesJointes.add(file);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("yfz.bpmn")
				.deploy().getId();
		System.out.println("idddddd" + deploymentId);
	
		CourriersArrivésImplLocal courriersArrivésImplLocal = new CourriersArrivésImplLocal();
		ProcessEngine processEngine = courriersArrivésImplLocal.getProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();

		//courriersArrivésImplLocal.setSession(session);
		Map<String, Object> proprietés = new HashMap<String, Object>();
		proprietés.put("date", "19-5-5");
		proprietés.put("départmentId", "ROLE_ADMIN");
		proprietés.put("isValidated", false);
		proprietés.put("expéditeur", "Steg");

		proprietés.put("listePiécesJointes", listePiécesJointes);
		ProcessInstance processInstance = courriersArrivésImplLocal.créerCourrier(proprietés);
		courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		System.out.println(runtimeService.getVariables(processInstance.getId()).toString());
		TaskService taskService = processEngine.getTaskService();
		System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().toString());
		List<Task> taskb = taskService.createTaskQuery().taskCandidateUser("mwm").list();
		
		ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("myProcess2");
		taskService.addCandidateGroup(
				taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list().get(0).getId(),
				"ROLE_ADMIN");
		List<Task> taskByProceeAndUser = taskService.createTaskQuery().processDefinitionId("myProcess2").taskCandidateUser("mwm").list();
		System.out.println(taskByProceeAndUser);
		System.out.println(taskb);
		System.out.println("xbcvbcvbcvbb");
	}
}
