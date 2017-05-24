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
import biz.picosoft.services.CourriersArrivésImpl;
import biz.picosoft.services.CourriersArrivésServices;

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

		CourriersArrivésServices courriersSortieService = new CourrierSortieImpl();
		Map<String, Object> proprietés = new HashMap<String, Object>();
		proprietés.put("date", "19-5-5");
		proprietés.put("départmentId", "chefsIT");
		proprietés.put("isValidated", true);
		proprietés.put("expéditeur", "noz");
		proprietés.put("isFinished", false);
		proprietés.put("société", "Steg");
		proprietés.put("objet", "facture");
		proprietés.put("starter", "fbm");
		File file = new File("D://cv/cover letter.docx");
		List listePiécesJointes = new ArrayList<>();
		listePiécesJointes.add(file);
		proprietés.put("listePiécesJointes", listePiécesJointes);
		ProcessInstance processInstance = courriersSortieService.créerCourrier(proprietés);

		System.out.println(
				"active tasks for weld ankoud :p " + courriersSortieService.getListActiveCourriersArrivésParUser("rb"));
		System.out.println("Strateeer" + runtimeService.getVariable(processInstance.getId(), "starter"));
		courriersSortieService.réviser(processInstance.getId(), true);
		System.out.println("active tasks for weld ankoud :p "
				+ courriersSortieService.getListActiveCourriersArrivésParUser("fbm"));
		System.out.println("active tasks for weld ankoud :p "
				+ courriersSortieService.getListActiveCourriersArrivésParUser("am"));

	}

}
