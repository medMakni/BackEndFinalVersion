package biz.picosoft.mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.services.CourriersArrivésImplLocal;

public class TestDao {
public static void main(String[] args) throws FileNotFoundException {
	
	Map<String, String> parameter = new HashMap<String, String>();
	 
	// user credentials
	parameter.put(SessionParameter.USER, "admin");
	parameter.put(SessionParameter.PASSWORD, "admin");

	// connection settings
	parameter.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/alfresco/cmisatom");
	 parameter.put(SessionParameter.BINDING_TYPE,
                BindingType.ATOMPUB.value());

	// set the alfresco object factory
	parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

	// create session
	SessionFactory factory = SessionFactoryImpl.newInstance();
	Session session = factory.getRepositories(parameter).get(0).createSession();
	Folder root = session.getRootFolder();
	FolderDaoImpl folderDaoImpl=new FolderDaoImpl();
	//folderDaoImpl.createFolder(root, "fatma2");
	
	/*Folder root = session.getRootFolder();
	FolderDaoImpl folderDaoImpl=new FolderDaoImpl();
	ItemIterable<CmisObject>  list=folderDaoImpl.getAllChildrens(root);
	  for (CmisObject o : list) {
          System.out.println(o.getName());
      }*/
	
	/*DocumentDaoImpl doi=new DocumentDaoImpl();
	doi.setSession(session);
	File file=new File("C://cover letter.pdf");
	//doi.inserte(file);
	 ObjectId o=new ObjectId() {
		
		public String getId() {
			// TODO Auto-generated method stub
			return "workspace://SpacesStore/0139211c-67e0-4d81-a3f0-918de3e56b5b";
		}
	};
	CmisObject obj= doi.getDocument(o);   
	System.out.println(obj.getName()); 
	doi.inserte(file);*/
	
	/*DocumentDaoImpl doi=new DocumentDaoImpl();
	doi.setSession(session);
	File file=new File("C://cover letter.pdf");
	//doi.inserte(file);
	  ObjectId o=new ObjectId() {
		
		public String getId() {
			// TODO Auto-generated method stub
			return "workspace://SpacesStore/ee6d0267-4ebc-43a7-a938-1115a9ad14b6";
		}
	}; 
	folderDaoImpl.setSession(session);
	CmisObject obj=folderDaoImpl.getFolderById(o) ;   
	System.out.println(obj.getName()); 
	doi.inserte(file,(Folder) obj);*/
	List<File> listePiécesJointes=new ArrayList<File>();
	File file=new File("C://cover letter.pdf");
	listePiécesJointes.add(file);
	ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
	RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
	String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn").deploy().getId();
	System.out.println("idddddd"+deploymentId);
	 ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		 CourriersArrivésImplLocal courriersArrivésImplLocal=new CourriersArrivésImplLocal(processEngine);
			courriersArrivésImplLocal.setSession(session);
			Map<String, Object> proprietés = new HashMap<String, Object>();
			proprietés.put("date", "19-5-5");
			proprietés.put("département", "rh");
			proprietés.put("isValidated", false);
			proprietés.put("expéditeur", "Steg");
			
			proprietés.put("listePiécesJointes", listePiécesJointes);
			 ProcessInstance processInstance=courriersArrivésImplLocal.créerCourrier(proprietés);
		// courriersArrivésImplLocal.validerCourrier(processInstance.getId(), true);
			System.out.println(runtimeService.getVariables(processInstance.getId()).toString());
		 
			System.out.println("xbcvbcvbcvbb");
}
}
