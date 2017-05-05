package biz.picosoft.mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;

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
	
	DocumentDaoImpl doi=new DocumentDaoImpl();
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
	doi.inserte(file,(Folder) obj);
}
}
