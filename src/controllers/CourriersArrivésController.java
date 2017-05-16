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
import biz.picosoft.services.CourriersArrivésImpl;
import biz.picosoft.services.CourriersArrivésServices;

@RestController
public class CourriersArrivésController {
	CourriersArrivésServices courriersArrivésServices;

	@RequestMapping(value = "/listCourriersArrivés", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAllCourriers() {
		courriersArrivésServices = new CourriersArrivésImpl();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();

		List<ProcessInstance> listeCourrier = courriersArrivésServices.getListCourriersArrivées();
		List<Map<String, Object>> customTaskList = new ArrayList<>();
		for (ProcessInstance task : listeCourrier) {

			customTaskList.add(runtimeService.getVariables(task.getId()));
		}
		return customTaskList;
	}

	@RequestMapping(value = "/créerCourriers", method = RequestMethod.GET)
	@ResponseBody
	public void créerCourriers(Map<String, Object> proprietésCourrier) {

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
