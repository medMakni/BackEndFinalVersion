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
		Map<String, Object> proprietés = new HashMap<String, Object>();
		proprietés.put("date", "19-5-5");
		proprietés.put("départmentId", "chefsIT");
		proprietés.put("expéditeur", "DirectionCommerciale");
		proprietés.put("déstinataire", "DirectionIT");
		proprietés.put("isValidated", true);
		proprietés.put("isFinished", false);
		proprietés.put("société", "Steg");
		proprietés.put("objet", "facturessss100");
		proprietés.put("starter", "fbm");
		Map<String, Object> commentHistory = new HashMap<>();
		proprietés.put("commentHistory", commentHistory);
		File file = new File("D://cv/cover letter.docx");
		List listePiécesJointes = new ArrayList<>();
		listePiécesJointes.add(file);
		proprietés.put("listePiécesJointes", listePiécesJointes);
		ProcessInstance processInstance = courriersIntereService.créerCourrier(proprietés);
		System.out.println("à réviser  " + courriersIntereService.getListActiveCourriersArrivésParUser("mz"));

		courriersIntereService.réviser(processInstance.getId(), true);
		// courriersIntereService.créerCourrier(courriersIntereService.getRuntimeService().getVariables(processInstance.getId()));
		System.out
				.println("active for other side  " + courriersIntereService.getListActiveCourriersArrivésParUser("rb"));
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
				  
				   courriersIntereService.getNbrOfFinishedCourrierArrivéParDirection(
				   "DirectionCommerciale"));
		// proprietés.put("idCourrier", processInstance.getId());
		// courriersIntereService.traiterCourrier(processInstance.getId(),
		// proprietés);
		/*
		 * System.out.println( "à réviser  " +
		 * courriersIntereService.getListActiveCourriersArrivésParUser("mz"));
		 * 
		 * System.out.println( "l autre chef département " +
		 * courriersIntereService.getListActiveCourriersArrivésParUser("rb"));
		 * /*System.out.println( "active tasks for commerciale :p " +
		 * courriersIntereService.getListActiveCourriersArrivésParUser("mz"));
		 * proprietés.put("affectedTo", "secrétairesC");
		 * proprietés.replace("isFinished", false);
		 * 
		 * courriersIntereService.traiterCourrier(processInstance.getId(),
		 * proprietés); System.out.println( "finis par direction" +
		 * 
		 * courriersIntereService.getNbrOfFinishedCourrierArrivéParDirection(
		 * "DirectionRH"));
		 */
		;
		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(2).getName());

		// System.out.println("active tasks for weld ankoud :p "
		// + courriersIntereService.getListActiveCourriersArrivésParUser("am"));

	}

}