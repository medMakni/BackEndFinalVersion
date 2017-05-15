package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
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
public    List<Map<String, Object>>  getAllCourriers(){
	courriersArrivésServices=new CourriersArrivésImpl();
	ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
	ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
	RuntimeService runtimeService=processEngine.getRuntimeService();
	
    List<ProcessInstance> listeCourrier = courriersArrivésServices.getListCourriersArrivées()  ;
    List<Map<String, Object>> customTaskList = new ArrayList<>();
    for (ProcessInstance task : listeCourrier) {
        

        customTaskList.add( runtimeService.getVariables(task.getId()));
    }
return customTaskList ;
}

@RequestMapping(value = "/getDoc", method = RequestMethod.GET)
@ResponseBody
public CmisObject getDoc()
{
	DocumentDaoImpl daoDocOmpl=new DocumentDaoImpl();
	ObjectId id=new ObjectId() {
		
		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return "workspace://SpacesStore/6fc7bf67-e057-4b30-ae37-0d6734b48ddf" ;
		}
	};
	CmisObject a = daoDocOmpl.getDocument (id);
	return a;
}
}
