package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.EngineServices;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.services.CourriersArriv�sImpl;
import biz.picosoft.services.CourriersArriv�sServices;

@RestController
public class CourriersArriv�sController {
	CourriersArriv�sServices courriersArriv�sServices;

	@RequestMapping(value = "/listCourriersArriv�s", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAllCourriers() {
		courriersArriv�sServices = new CourriersArriv�sImpl();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();

		List<ProcessInstance> listeCourrier = courriersArriv�sServices.getListCourriersArriv�es();
		List<Map<String, Object>> customTaskList = new ArrayList<>();
		for (ProcessInstance task : listeCourrier) {

			customTaskList.add(runtimeService.getVariables(task.getId()));
		}
		return customTaskList;
	}

	@RequestMapping(value = "/cr�erCourriers", method = RequestMethod.GET)
	@ResponseBody
	public void cr�erCourriers(Map<String, Object> propriet�sCourrier) {

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
	public List<Task> getListCourriersArriv�sParUser(String userName) {
		courriersArriv�sServices.getListCourriersArriv�sParUser(userName);
		return null;
	}

	@RequestMapping(value = "/getListCourrierArriv�ParDirection", method = RequestMethod.GET)
	@ResponseBody
	public List<Task> getListCourrierArriv�ParDirection(String direction) {
		courriersArriv�sServices.getListCourrierArriv�ParDirection(direction);
		return null;
	}
}
