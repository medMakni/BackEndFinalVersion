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
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.mains.TestDao;
import biz.picosoft.services.CourriersArrivésImpl;
import biz.picosoft.services.CourriersServices;
import biz.picosoft.services.LdapService;
import biz.picosoft.services.LdapServiceImpl;

@RestController
public class CourriersArrivésController {

	CourriersServices courriersArrivésServices = (CourriersServices) new CourriersArrivésImpl();
	LdapService ls=new LdapServiceImpl();

	@RequestMapping(value = "/listCourriersArrivés", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAllCourriers() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();

		List<Map<String, Object>> listeCourrier = courriersArrivésServices.getListCourriersArrivées();

		return listeCourrier;
	}

	@RequestMapping(value = "/créerCourriers", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
	@ResponseBody
	public void créerCourriers(@RequestParam("listePiecesJointes") List<MultipartFile> listePiécesJointes,
			@RequestParam("objet") String objet, @RequestParam("societe") String société,
			@RequestParam("dateOut") Object dateOut, @RequestParam("direction") String direction) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn")
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
		proprietésCourrier.put("objet", objet);
		proprietésCourrier.put("société", société);
		proprietésCourrier.put("dateOut", dateOut);
		proprietésCourrier.put("départmentId", direction);
		proprietésCourrier.put("isValidated", true);
		proprietésCourrier.put("expéditeur", "Steg");
		List<File> listeFile = new ArrayList<>();
		for (int i = 0; i < listePiécesJointes.size(); i++) {
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
		CmisObject a = daoDocOmpl.getDocument("workspace://SpacesStore/6fc7bf67-e057-4b30-ae37-0d6734b48ddf");
		return a;
	}

	@RequestMapping(value = "/réviser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void réviser(@RequestBody Map<String, Object> data) {
		System.out.println("rrr"+data.get("idCourrier"));
		courriersArrivésServices.réviser((String)data.get("idCourrier"), (boolean)data.get("isValidated"));
	}

	
	@RequestMapping(value = "/traiterCourrier", method = RequestMethod.POST)
	@ResponseBody
	public void traiterCourrier(@RequestBody Map<String, Object> map) {
		courriersArrivésServices.traiterCourrier(map);
	}

	@RequestMapping(value = "/archiverCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void archiverCourrier(@RequestParam("id")String idCourrier) {
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
	public List<Map<String, Object>> getListActiveCourriersArrivésParUser(@RequestParam("username") String userName) {

		return courriersArrivésServices.getListActiveCourriersArrivésParUser(userName);
	}

	@RequestMapping(value = "/getListCourrierArrivéParDirection", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getListActiveCourrierArrivéParDirection(String direction) {

		return courriersArrivésServices.getListActiveCourrierArrivéParDirection(direction);
	}
	@RequestMapping(value = "/getCourrierDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCourrierDetails(@RequestParam("id")String id) throws Exception {
		
		return courriersArrivésServices.getCourrierDetails(id);
	}
	@RequestMapping(value = "/downloadPDFFile",produces = { "application/json" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> downloadPDFFile(@RequestParam("idCourrier")String id,@RequestParam("nbreCourrier")String nbreCourrier) throws Exception {
		return courriersArrivésServices.postFile(id,nbreCourrier);
	}
	@RequestMapping(value = "/getSousGroup", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getSousGroup(@RequestParam("id")String id,@RequestParam("direction")String direction) throws Exception {
		Map<String, Object> map= courriersArrivésServices.getCourrierDetails(id);
		return ls.getSousGroup("DirectionGénérale");
		
	}
}