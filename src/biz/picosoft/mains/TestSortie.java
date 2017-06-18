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
		Map<String, Object> propriet�s = new HashMap<String, Object>();
		propriet�s.put("date", "19-5-5");
		propriet�s.put("d�partmentId", "DirectionRH");
		propriet�s.put("exp�diteur", "DirectionCommerciale");
		//propriet�s.put("d�stinataire", "DirectionIT");
		propriet�s.put("isValidated", true);
		propriet�s.put("isFinished", false);
		Map<String, Object> commentHistory = new HashMap<>();
		propriet�s.put("commentHistory", commentHistory);
		propriet�s.put("soci�t�", "Steg");
		propriet�s.put("objet", "facturaa404");
		propriet�s.put("starter", "fbm");
		File file = new File("D://cv/cover letter.docx");
		List listePi�cesJointes = new ArrayList<>();
		listePi�cesJointes.add(file);
		propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		ProcessInstance processInstance = courriersSortieService.cr�erCourrier(propriet�s);
	
		 System.out.println(
				"active tasks chef d�partement  " + courriersSortieService.getListActiveCourriersArriv�sParUser("am"));
		// System.out.println("Strateeer" +
		// runtimeService.getVariable(processInstance.getId(), "starter"));
		courriersSortieService.r�viser(processInstance.getId(), true);
		 System.out.println(
					"active tasks for BO" + courriersSortieService.getListActiveCourriersArriv�sParUser("rb"));
		
	//	courriersSortieService.cr�erCourrier(courriersSortieService.getRuntimeService().getVariables(processInstance.getId()));
		System.out.println(
				"active tasks chef d�partement  " + courriersSortieService.getListActiveCourriersArriv�sParUser("am"));
		Map<String, Object> map = new HashMap<>();
		map.put("idCourrier", processInstance.getId());
		map.put("username", "fbm");
		map.put("annotation", "hello fbm");
		map.put("isValidated", false);
		courriersSortieService.traiterCourrier(map);
		System.out.println(
				"active tasks for starter  " + courriersSortieService.getListActiveCourriersArriv�sParUser("fbm"));
	
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("idCourrier", processInstance.getId());
		map2.put("username", "fbm2");
		map2.put("annotation", "hello fbm2");
		map2.put("isValidated", true);
		courriersSortieService.traiterCourrier(map2);
		
		System.out.println(
				"active tasks for starter  " + courriersSortieService.getListActiveCourriersArriv�sParUser("fbm"));
	
		/*System.out.println(
				"active tasks for starter " + courriersSortieService.getListActiveCourriersArriv�sParUser("fbm"));
		System.out.println("active tasks for BO " + courriersSortieService.getListActiveCourriersArriv�sParUser("rb"));
		System.out.println(
				"active tasks for starter " + courriersSortieService.getListActiveCourriersArriv�sParUser("fbm"));
		//courriersSortieService.traiterCourrier(processInstance.getId(), propriet�s);*/
		/*System.out.println("nbr of finished " +courriersSortieService.getFinishedCourrier().size());
		System.out.println("nbr of finished par direction" +courriersSortieService.getNbrOfFinishedCourrierArriv�ParDirection( "DirectionCommerciale"));
		System.out.println("active courrier" +courriersSortieService.getListCourriersArriv�es());
		 */
		/*propriet�s.replace("isValidated", false);
		courriersSortieService.traiterCourrier(processInstance.getId(), propriet�s);
		System.out.println(
				"active tasks for starter " + courriersSortieService.getListActiveCourriersArriv�sParUser("fbm"));
		
		//restart schema in cas of refuse 
		propriet�s.put("idCourrier", processInstance.getId());
		courriersSortieService.cr�erCourrier(propriet�s);
		System.out.println(
				"active tasks chef d�partement  " + courriersSortieService.getListActiveCourriersArriv�sParUser("mz"));

		courriersSortieService.r�viser(processInstance.getId(), true);
		System.out.println(
				"active tasks chef d�partement  " + courriersSortieService.getListActiveCourriersArriv�sParUser("mz"));
*/
	}

}