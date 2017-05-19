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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.mains.TestDao;
import biz.picosoft.services.CourriersArrivésImpl;
import biz.picosoft.services.CourriersArrivésServices;

@RestController
public class CourriersArrivésController {
	CourriersArrivésServices courriersArrivésServices= new CourriersArrivésImpl();

	@RequestMapping(value = "/listCourriersArrivés", method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getAllCourriers() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();

		List<ProcessInstance> listeCourrier = courriersArrivésServices.getListCourriersArrivées();
		
		List<Map<String, Object>> customTaskList = new ArrayList<>();
		int i=0;
		for (ProcessInstance task : listeCourrier) {
			System.out.println(i+"    "+listeCourrier.size());
			i++;
			System.out.println("bla bla"+task.getId());
			System.out.println(runtimeService.getVariables(task.getId()));
			customTaskList.add(runtimeService.getVariables(task.getId()));
		}
		return customTaskList;
	}

	@RequestMapping(value = "/créerCourriers", method = RequestMethod.POST,produces = "application/json",consumes="multipart/form-data")
	@ResponseBody
	public void créerCourriers(@RequestParam("listePiecesJointes") List<MultipartFile> listePiécesJointes,@RequestParam("objet") String objet) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn").deploy().getId();
	//	repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		System.out.println("idddddd" + deploymentId);
	
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		Map<String, Object> proprietésCourrier = new HashMap<String,Object>();
		for (int i = 0; i < listePiécesJointes.size(); i++) {
			System.out.println(listePiécesJointes.get(i).getClass());

		}
		proprietésCourrier.put("date", "19-5-5");
		proprietésCourrier.put("départmentId", "ROLE_ADMIN");
		proprietésCourrier.put("isValidated", true);
		proprietésCourrier.put("expéditeur", "Steg");
		List<File> listeFile =new ArrayList<>();
		for(int i=0;i<listePiécesJointes.size();i++){
			listeFile.add(courriersArrivésServices.multipartToFile(listePiécesJointes.get(i)));
		}
		

		proprietésCourrier.put("listePiécesJointes", listeFile);
		

System.out.println(proprietésCourrier);
		courriersArrivésServices.créerCourrier(proprietésCourrier);
		
	}

	@RequestMapping(value = "/getDoc", method = RequestMethod.GET)
	@ResponseBody
	public CmisObject getDoc() {
		DocumentDaoImpl daoDocOmpl = new DocumentDaoImpl();
		ObjectId id = new ObjectId() {

			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return "workspace://SpacesStore/6fc7bf67-e057-4b30-ae37-0d6734b48ddf";
			}
		};
		CmisObject a = daoDocOmpl.getDocument(id);
		return a;
	}

	@RequestMapping(value = "/réviser", method = RequestMethod.GET)
	@ResponseBody
	public void réviser(String idCourrier, boolean isValidated) {
		courriersArrivésServices.réviser(idCourrier, isValidated);
	}

	@RequestMapping(value = "/validerCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void validerCourrier(String idCourrier) {
		courriersArrivésServices.validerCourrier(idCourrier);
	}

	@RequestMapping(value = "/refuserCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void refuserCourrier(String idCourrier) {
		courriersArrivésServices.refuserCourrier(idCourrier);
	}

	@RequestMapping(value = "/traiterCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void traiterCourrier(String idCourrier, Map<String, Object> proprietésCourrier) {
		courriersArrivésServices.traiterCourrier(idCourrier, proprietésCourrier);
	}

	@RequestMapping(value = "/archiverCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void archiverCourrier(String idCourrier) {
		// TODO Auto-generated method stub
		courriersArrivésServices.archiverCourrier(idCourrier);
	}

	@RequestMapping(value = "/attachFiles", method = RequestMethod.GET)
	@ResponseBody
	public String attachFiles(List<File> listePiécesJointes, String expéditeur, String id) {
		courriersArrivésServices.attachFiles(listePiécesJointes, expéditeur, id);
		return null;
	}

	@RequestMapping(value = "/getListCourriersArrivésParUser", method = RequestMethod.GET)
	@ResponseBody
	public List<Task> getListCourriersArrivésParUser(String userName) {
		courriersArrivésServices.getListCourriersArrivésParUser(userName);
		return null;
	}

	@RequestMapping(value = "/getListCourrierArrivéParDirection", method = RequestMethod.GET)
	@ResponseBody
	public List<Task> getListCourrierArrivéParDirection(String direction) {
		courriersArrivésServices.getListCourrierArrivéParDirection(direction);
		return null;
	}
}
