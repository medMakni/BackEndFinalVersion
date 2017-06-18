package biz.picosoft.mains;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.services.CourrierSortieImpl;
import biz.picosoft.services.CourriersServices;

public class TestSortie {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersSorties.bpmn")
				.deploy().getId();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		System.out.println("idddddd" + deploymentId);

		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();

		CourrierSortieImpl courriersSortieService = new CourrierSortieImpl();
		Map<String, Object> proprietés = new HashMap<String, Object>();
		proprietés.put("date", "19-5-5");
		proprietés.put("départmentId", "DirectionRH");
		proprietés.put("expéditeur", "DirectionCommerciale");
		//proprietés.put("déstinataire", "DirectionIT");
		proprietés.put("isValidated", true);
		proprietés.put("isFinished", false);
		Map<String, Object> commentHistory = new HashMap<>();
		proprietés.put("commentHistory", commentHistory);
		proprietés.put("société", "Steg");
		proprietés.put("objet", "facturaa404");
		proprietés.put("starter", "fbm");
		File file = new File("D://cv/cover letter.docx");
		List listePiécesJointes = new ArrayList<>();
		listePiécesJointes.add(file);
		proprietés.put("listePiécesJointes", listePiécesJointes);
		ProcessInstance processInstance = courriersSortieService.créerCourrier(proprietés);
	
		 System.out.println(
				"active tasks chef département  " + courriersSortieService.getListActiveCourriersArrivésParUser("am"));
		// System.out.println("Strateeer" +
		// runtimeService.getVariable(processInstance.getId(), "starter"));
		courriersSortieService.réviser(processInstance.getId(), true);
		 System.out.println(
					"active tasks for BO" + courriersSortieService.getListActiveCourriersArrivésParUser("rb"));
		
	//	courriersSortieService.créerCourrier(courriersSortieService.getRuntimeService().getVariables(processInstance.getId()));
		System.out.println(
				"active tasks chef département  " + courriersSortieService.getListActiveCourriersArrivésParUser("am"));
		Map<String, Object> map = new HashMap<>();
		map.put("idCourrier", processInstance.getId());
		map.put("username", "fbm");
		map.put("annotation", "hello fbm");
		map.put("isValidated", false);
		courriersSortieService.traiterCourrier(map);
		System.out.println(
				"active tasks for starter  " + courriersSortieService.getListActiveCourriersArrivésParUser("fbm"));
	
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("idCourrier", processInstance.getId());
		map2.put("username", "fbm2");
		map2.put("annotation", "hello fbm2");
		map2.put("isValidated", true);
		courriersSortieService.traiterCourrier(map2);
		
		System.out.println(
				"active tasks for starter  " + courriersSortieService.getListActiveCourriersArrivésParUser("fbm"));
	
		/*System.out.println(
				"active tasks for starter " + courriersSortieService.getListActiveCourriersArrivésParUser("fbm"));
		System.out.println("active tasks for BO " + courriersSortieService.getListActiveCourriersArrivésParUser("rb"));
		System.out.println(
				"active tasks for starter " + courriersSortieService.getListActiveCourriersArrivésParUser("fbm"));
		//courriersSortieService.traiterCourrier(processInstance.getId(), proprietés);*/
		/*System.out.println("nbr of finished " +courriersSortieService.getFinishedCourrier().size());
		System.out.println("nbr of finished par direction" +courriersSortieService.getNbrOfFinishedCourrierArrivéParDirection( "DirectionCommerciale"));
		System.out.println("active courrier" +courriersSortieService.getListCourriersArrivées());
		 */
		/*proprietés.replace("isValidated", false);
		courriersSortieService.traiterCourrier(processInstance.getId(), proprietés);
		System.out.println(
				"active tasks for starter " + courriersSortieService.getListActiveCourriersArrivésParUser("fbm"));
		
		//restart schema in cas of refuse 
		proprietés.put("idCourrier", processInstance.getId());
		courriersSortieService.créerCourrier(proprietés);
		System.out.println(
				"active tasks chef département  " + courriersSortieService.getListActiveCourriersArrivésParUser("mz"));

		courriersSortieService.réviser(processInstance.getId(), true);
		System.out.println(
				"active tasks chef département  " + courriersSortieService.getListActiveCourriersArrivésParUser("mz"));
*/
	}

}