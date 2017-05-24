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
import biz.picosoft.services.CourriersArriv�sImpl;
import biz.picosoft.services.CourriersArriv�sServices;

@RestController
public class CourriersArriv�sController {
	CourriersArriv�sServices courriersArriv�sServices = new CourriersArriv�sImpl();

	@RequestMapping(value = "/listCourriersArriv�s", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAllCourriers() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();

		  List<Map<String, Object>> listeCourrier = courriersArriv�sServices.getListCourriersArriv�es();
	 
		return listeCourrier;
	}

	@RequestMapping(value = "/cr�erCourriers", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
	@ResponseBody
	public void cr�erCourriers(@RequestParam("listePiecesJointes") List<MultipartFile> listePi�cesJointes,
			@RequestParam("objet") String objet ,@RequestParam("societe") String soci�t�,@RequestParam("dateOut")Object dateOut,@RequestParam("direction") String direction) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArriv�s.bpmn")
				.deploy().getId();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		System.out.println("idddddd" + deploymentId);

		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		Map<String, Object> propriet�sCourrier = new HashMap<String, Object>();
		for (int i = 0; i < listePi�cesJointes.size(); i++) {
			System.out.println(listePi�cesJointes.get(i).getClass());

		}
		propriet�sCourrier.put("objet", objet);
		propriet�sCourrier.put("soci�t�", soci�t�);
		propriet�sCourrier.put("dateOut", dateOut);
		propriet�sCourrier.put("d�partmentId", direction);
		propriet�sCourrier.put("isValidated", true);
		propriet�sCourrier.put("exp�diteur", "Steg");
		List<File> listeFile = new ArrayList<>();
		for (int i = 0; i < listePi�cesJointes.size(); i++) {
			listeFile.add(courriersArriv�sServices.multipartToFile(listePi�cesJointes.get(i)));
		}

		propriet�sCourrier.put("listePi�cesJointes", listeFile);

		System.out.println(propriet�sCourrier);
		courriersArriv�sServices.cr�erCourrier(propriet�sCourrier);

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

	@RequestMapping(value = "/r�viser", method = RequestMethod.GET)
	@ResponseBody
	public void r�viser(String idCourrier, boolean isValidated) {
		courriersArriv�sServices.r�viser(idCourrier, isValidated);
	}

	@RequestMapping(value = "/validerCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void validerCourrier(String idCourrier) {
		courriersArriv�sServices.validerCourrier(idCourrier);
	}

	@RequestMapping(value = "/refuserCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void refuserCourrier(String idCourrier) {
		courriersArriv�sServices.refuserCourrier(idCourrier);
	}

	@RequestMapping(value = "/traiterCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void traiterCourrier(String idCourrier, Map<String, Object> propriet�sCourrier) {
		courriersArriv�sServices.traiterCourrier(idCourrier, propriet�sCourrier);
	}

	@RequestMapping(value = "/archiverCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void archiverCourrier(String idCourrier) {
		// TODO Auto-generated method stub
		courriersArriv�sServices.archiverCourrier(idCourrier);
	}

	@RequestMapping(value = "/attachFiles", method = RequestMethod.GET)
	@ResponseBody
	public String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id) {
		courriersArriv�sServices.attachFiles(listePi�cesJointes, exp�diteur, id);
		return null;
	}

	@RequestMapping(value = "/getListCourriersArriv�sParUser", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getListActiveCourriersArriv�sParUser(@RequestParam("username")String userName) {
	
		return 	courriersArriv�sServices.getListActiveCourriersArriv�sParUser(userName);
	}

	@RequestMapping(value = "/getListCourrierArriv�ParDirection", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getListActiveCourrierArriv�ParDirection(String direction) {
		
		return courriersArriv�sServices.getListActiveCourrierArriv�ParDirection(direction);
	}
}