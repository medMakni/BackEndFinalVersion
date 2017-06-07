package biz.picosoft.mains;

import java.io.File;
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
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.services.CourrierInterneImpl;

public class TestInterne {

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersInetrnes.bpmn")
				.deploy().getId();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		System.out.println("idddddd" + deploymentId);
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();

		CourrierInterneImpl courriersIntereService = new CourrierInterneImpl();
		Map<String, Object> propriet�s = new HashMap<String, Object>();
		propriet�s.put("date", "19-5-5");
		propriet�s.put("d�partmentId", "chefsIT");
		propriet�s.put("exp�diteur", "DirectionRH");
		propriet�s.put("d�stinataire", "DirectionCommerciale");
		propriet�s.put("isValidated", true);
		propriet�s.put("isFinished", false);
		propriet�s.put("soci�t�", "Steg");
		propriet�s.put("objet", "facturessss");
		propriet�s.put("starter", "rb");
		File file = new File("D://cv/cover letter.docx");
		List listePi�cesJointes = new ArrayList<>();
		listePi�cesJointes.add(file);
		propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		ProcessInstance processInstance = courriersIntereService.cr�erCourrier(propriet�s);
  
		System.out.println(
				"� r�viser  " + courriersIntereService.getListActiveCourriersArriv�sParUser("am"));
 
		courriersIntereService.r�viser(processInstance.getId(), false);
		System.out.println("active for starter  "
				+ courriersIntereService.getListActiveCourriersArriv�sParUser("rb"));

		/*System.out.println(
				"active tasks for commerciale :p " + courriersIntereService.getListActiveCourriersArriv�sParUser("mz"));
		propriet�s.put("affectedTo", "secr�tairesC");
		propriet�s.replace("isFinished", false);
 
		courriersIntereService.traiterCourrier(processInstance.getId(), propriet�s);
		System.out.println(
				"finis par direction" +   
						
						 courriersIntereService.getNbrOfFinishedCourrierArriv�ParDirection("DirectionRH"));*/
	 ;
//		System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(2).getName());
	 
		// System.out.println("active tasks for weld ankoud :p "
		// + courriersIntereService.getListActiveCourriersArriv�sParUser("am"));
 
	}

}