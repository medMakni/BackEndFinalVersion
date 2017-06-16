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
		propriet�s.put("exp�diteur", "DirectionCommerciale");
		propriet�s.put("d�stinataire", "DirectionIT");
		propriet�s.put("isValidated", true);
		propriet�s.put("isFinished", false);
		propriet�s.put("soci�t�", "Steg");
		propriet�s.put("objet", "facturessss100");
		propriet�s.put("starter", "fbm");
		Map<String, Object> commentHistory = new HashMap<>();
		propriet�s.put("commentHistory", commentHistory);
		File file = new File("D://cv/cover letter.docx");
		List listePi�cesJointes = new ArrayList<>();
		listePi�cesJointes.add(file);
		propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		ProcessInstance processInstance = courriersIntereService.cr�erCourrier(propriet�s);
		System.out.println("� r�viser  " + courriersIntereService.getListActiveCourriersArriv�sParUser("mz"));

		courriersIntereService.r�viser(processInstance.getId(), true);
		// courriersIntereService.cr�erCourrier(courriersIntereService.getRuntimeService().getVariables(processInstance.getId()));
		System.out
				.println("active for other side  " + courriersIntereService.getListActiveCourriersArriv�sParUser("rb"));
		Map<String, Object> map = new HashMap<>();
		map.put("idCourrier", processInstance.getId());
		map.put("username", "fbm");
		map.put("affectedTo", "java");
		map.put("annotation", "hello fbm");
		courriersIntereService.traiterCourrier(map);
		System.out
				.println("active " + courriersIntereService.getRuntimeService().getVariables(processInstance.getId()));
		Map<String, Object> map2 = new HashMap<>();
		map2.put("idCourrier", processInstance.getId());
		map2.put("username", "ouss");
		map2.put("affectedTo", "java");
		map2.put("annotation", "hello ouss");
		courriersIntereService.traiterCourrier(map2);
		System.out
				.println("active " + courriersIntereService.getRuntimeService().getVariables(processInstance.getId()));
		courriersIntereService.archiverCourrier(processInstance.getId());
		System.out.println( "finis par direction" +
				  
				   courriersIntereService.getNbrOfFinishedCourrierArriv�ParDirection(
				   "DirectionCommerciale"));
		// propriet�s.put("idCourrier", processInstance.getId());
		// courriersIntereService.traiterCourrier(processInstance.getId(),
		// propriet�s);
		/*
		 * System.out.println( "� r�viser  " +
		 * courriersIntereService.getListActiveCourriersArriv�sParUser("mz"));
		 * 
		 * System.out.println( "l autre chef d�partement " +
		 * courriersIntereService.getListActiveCourriersArriv�sParUser("rb"));
		 * /*System.out.println( "active tasks for commerciale :p " +
		 * courriersIntereService.getListActiveCourriersArriv�sParUser("mz"));
		 * propriet�s.put("affectedTo", "secr�tairesC");
		 * propriet�s.replace("isFinished", false);
		 * 
		 * courriersIntereService.traiterCourrier(processInstance.getId(),
		 * propriet�s); System.out.println( "finis par direction" +
		 * 
		 * courriersIntereService.getNbrOfFinishedCourrierArriv�ParDirection(
		 * "DirectionRH"));
		 */
		;
		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(2).getName());

		// System.out.println("active tasks for weld ankoud :p "
		// + courriersIntereService.getListActiveCourriersArriv�sParUser("am"));

	}

}