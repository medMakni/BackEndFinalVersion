package biz.picosoft.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.daoImpl.ContacteDaoImpl;
import biz.picosoft.daoImpl.CourrierDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.daoImpl.SociétéDaoImpl;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Courrier;
import biz.picosoft.entity.CourrierArrivé;
import biz.picosoft.entity.CourrierInterne;
import biz.picosoft.entity.CourrierSortie;
import biz.picosoft.entity.Société;

public class Test {
	public static void main(String args[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CourrierDao courrierDao=(CourrierDaoImpl) context.getBean("courrierDaoImpl");
		SociétéDaoImpl sociétéDao=(SociétéDaoImpl) context.getBean("sociétéDaoImpl");
		  ContacteDaoImpl contacteDaoImpl = (ContacteDaoImpl) context.getBean("contactDaoImpl");
	Contacte contact=contacteDaoImpl.findById(Contacte.class, 15);
	Société société=sociétéDao.findById(Société.class, 5);
	CourrierSortie c=new CourrierSortie("sss", "20", "resource", "17-5-4", société, contact);
	CourrierInterne c2=new CourrierInterne("sss"," 20","sss", "resource", "17-5-4");
	courrierDao.insert(c2);
		/*Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, "admin");
		parameter.put(SessionParameter.PASSWORD, "admin");

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/alfresco/cmisatom");
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		// set the alfresco object factory
		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

		// create session
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Session session = factory.getRepositories(parameter).get(0).createSession();

		List<Repository> repositories = factory.getRepositories(parameter);
		for (Repository r : repositories) {
			System.out.println("Found repository: " + r.getName());
			System.out.println("teeeeeeeeeees");
		}
		Repository repository = repositories.get(0);

		System.out.println(
				"Got a connection to repository: " + repository.getName() + ", with id: " + repository.getId());
		Folder root = session.getRootFolder();
		ItemIterable<CmisObject> children = root.getChildren();
		System.out.println("pathhhhh" + root.getPath());
		System.out.println("Found the following objects in the root folder:-");
		for (CmisObject o : children) {
			System.out.println(o.getName() + " which is of type " + o.getType().getDisplayName());
		}

		System.out.println("Creating 'ADGNewFolder' in the root folder");
		Map<String, String> newFolderProps = new HashMap<String, String>();
		newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		newFolderProps.put(PropertyIds.NAME, "ADGNewFolderPermit2222");

		// Folder newFolder = root.createFolder(newFolderProps);

		FolderDaoImpl folderDaoImpl = new FolderDaoImpl();
		ItemIterable<CmisObject> list = folderDaoImpl.getAllChildrens(root);
		for (CmisObject o : list) {
			System.out.println(o.getName());
		}

		Folder folderFound = (Folder) folderDaoImpl.getFolder("/ADGNewFolderPermit22", session);
		System.out.println("found folder" + folderFound.getName());
		folderDaoImpl.deleteFolder(folderFound);
		System.out.println("yep deleted ");

		/*
		 * List<String> permissions = new ArrayList<String>();
		 * permissions.add("cmis:all"); String principal = "admin"; Ace aceIn =
		 * session.getObjectFactory().createAce(principal, permissions);
		 * System.out.println("is direct ? "+aceIn.isDirect()); List<Ace>
		 * aceListIn = new ArrayList<Ace>(); aceListIn.add(aceIn);
		 * newFolder.addAcl(aceListIn, AclPropagation.REPOSITORYDETERMINED); for
		 * (Action a: newFolder.getAllowableActions().getAllowableActions()) {
		 * System.out.println("\t" + a.value()); }
		 * 
		
		// Did it work?

		System.out.println("Now finding the following objects in the root folder:-");
		for (CmisObject o : children) {
			System.out.println(o.getName());
		}*/
	}

}
