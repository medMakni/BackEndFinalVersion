package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import biz.picosoft.mains.TestDao;
import biz.picosoft.services.CourrierInterneImpl;
import biz.picosoft.services.CourriersServices;
import biz.picosoft.services.LdapService;
import biz.picosoft.services.LdapServiceImpl;
@RestController
public class CourrierInterneController {
	CourriersServices courriersInterneServices = (CourriersServices) new CourrierInterneImpl();
	LdapService ls=new LdapServiceImpl();
	@RequestMapping(value = "/créerCourrierInterne", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
	@ResponseBody
	public void créerCourrierInterne(@RequestParam("listePiecesJointes") List<MultipartFile> listePiécesJointes,
			@RequestParam("objet") String objet, @RequestParam("societe") String société,
			@RequestParam("dateOut") Object dateOut, @RequestParam("direction") String direction, @RequestParam("starter") String starter ) {
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
		Map<String, Object> proprietésCourrier = new HashMap<String, Object>();
		for (int i = 0; i < listePiécesJointes.size(); i++) {
			System.out.println(listePiécesJointes.get(i).getClass());

		}
		proprietésCourrier.put("objet",objet);
		proprietésCourrier.put("société", société);
		proprietésCourrier.put("dateOut", dateOut);
		proprietésCourrier.put("départmentId", direction);
		proprietésCourrier.put("isValidated", true);
		proprietésCourrier.put("expéditeur", "Direction IT");
		proprietésCourrier.put("starter",starter);
		proprietésCourrier.put("déstinataire","Direction Générale");

		proprietésCourrier.put("isChecked", false);

		List<File> listeFile = new ArrayList<>();
		for (int i = 0; i < listePiécesJointes.size(); i++) {
			listeFile.add(courriersInterneServices.multipartToFile(listePiécesJointes.get(i)));
		}

		proprietésCourrier.put("listePiécesJointes", listeFile);

		System.out.println(proprietésCourrier);
		courriersInterneServices.créerCourrier(proprietésCourrier);

	}
	@RequestMapping(value = "/listCourriersInternes", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAllCourriers() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();

		List<Map<String, Object>> listeCourrier = courriersInterneServices.getListCourriers();

		return listeCourrier;
	}
	@RequestMapping(value = "/getListCourriersInternesParUser", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getListActiveCourriersArrivésParUser(@RequestParam("username") String userName) {

		return courriersInterneServices.getListActiveCourriersParUser(userName);
	}
	@RequestMapping(value = "/réviserInterne", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void réviser(@RequestBody Map<String, Object> data) {
		System.out.println("rrr"+data.get("idCourrier"));
		courriersInterneServices.réviser((String)data.get("idCourrier"), (boolean)data.get("isValidated"));
	}
	@RequestMapping(value = "/traiterCourrierInterne", method = RequestMethod.POST)
	@ResponseBody
	public void traiterCourrierInterne(@RequestBody Map<String, Object> map) {
		courriersInterneServices.traiterCourrier(map);
	}
	
}
