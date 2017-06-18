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
import biz.picosoft.services.CourriersArriv�sImpl;
import biz.picosoft.services.CourriersServices;
import biz.picosoft.services.LdapService;
import biz.picosoft.services.LdapServiceImpl;

@RestController
public class CourriersArriv�sController {

	CourriersServices courriersArriv�sServices = (CourriersServices) new CourriersArriv�sImpl();
	LdapService ls=new LdapServiceImpl();

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
			@RequestParam("objet") String objet, @RequestParam("societe") String soci�t�,
			@RequestParam("dateOut") Object dateOut, @RequestParam("direction") String direction) {
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
		CmisObject a = daoDocOmpl.getDocument("workspace://SpacesStore/6fc7bf67-e057-4b30-ae37-0d6734b48ddf");
		return a;
	}

	@RequestMapping(value = "/r�viser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void r�viser(@RequestBody Map<String, Object> data) {
		System.out.println("rrr"+data.get("idCourrier"));
		courriersArriv�sServices.r�viser((String)data.get("idCourrier"), (boolean)data.get("isValidated"));
	}

	
	@RequestMapping(value = "/traiterCourrier", method = RequestMethod.POST)
	@ResponseBody
	public void traiterCourrier(@RequestBody Map<String, Object> map) {
		courriersArriv�sServices.traiterCourrier(map);
	}

	@RequestMapping(value = "/archiverCourrier", method = RequestMethod.GET)
	@ResponseBody
	public void archiverCourrier(@RequestParam("id")String idCourrier) {
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
	public List<Map<String, Object>> getListActiveCourriersArriv�sParUser(@RequestParam("username") String userName) {

		return courriersArriv�sServices.getListActiveCourriersArriv�sParUser(userName);
	}

	@RequestMapping(value = "/getListCourrierArriv�ParDirection", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getListActiveCourrierArriv�ParDirection(String direction) {

		return courriersArriv�sServices.getListActiveCourrierArriv�ParDirection(direction);
	}
	@RequestMapping(value = "/getCourrierDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCourrierDetails(@RequestParam("id")String id) throws Exception {
		
		return courriersArriv�sServices.getCourrierDetails(id);
	}
	@RequestMapping(value = "/downloadPDFFile",produces = { "application/json" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> downloadPDFFile(@RequestParam("idCourrier")String id,@RequestParam("nbreCourrier")String nbreCourrier) throws Exception {
		return courriersArriv�sServices.postFile(id,nbreCourrier);
	}
	@RequestMapping(value = "/getSousGroup", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getSousGroup(@RequestParam("id")String id,@RequestParam("direction")String direction) throws Exception {
		Map<String, Object> map= courriersArriv�sServices.getCourrierDetails(id);
		return ls.getSousGroup("DirectionG�n�rale");
		
	}
}