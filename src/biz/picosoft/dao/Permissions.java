package biz.picosoft.dao;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.CapabilityAcl;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

public class Permissions {
	public static void main(String[] args) {
		Map<String, String> parameter = new HashMap<String, String>();

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

		// Check if the repo supports ACLs
		if (!session.getRepositoryInfo().getCapabilities().getAclCapability().equals(CapabilityAcl.MANAGE)) {
			System.out.println("Repository does not allow ACL management");
		} else {
			System.out.println("Repository allows ACL management");

			System.out.println("Creating folders for permissions example");

			HashMap<String, String> newFolderProps = new HashMap<String, String>();
			newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
			newFolderProps.put(PropertyIds.NAME, "ADGFolderPermissionssss");

			Folder folderAssociations = session.getRootFolder().createFolder(newFolderProps);

			List<String> permissions = new ArrayList<String>();
			permissions.add("cmis:read");
			String principal = "GROUP_user";
			Ace aceIn = session.getObjectFactory().createAce(principal, permissions);
			System.out.println("is direct ? " + aceIn.isDirect());
			List<Ace> aceListIn = new ArrayList<Ace>();
			aceListIn.add(aceIn);
			folderAssociations.addAcl(aceListIn, AclPropagation.REPOSITORYDETERMINED);

			HashMap<String, String> newFileProps = new HashMap<String, String>();
			ContentStream contentStream = new ContentStreamImpl("permissions.txt", null, "plain/text",
					new ByteArrayInputStream("some content".getBytes()));

			newFileProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			newFileProps.put(PropertyIds.NAME, "ADGFilePermissions");
			Document testDoc = folderAssociations.createDocument(newFileProps, contentStream, VersioningState.MAJOR);

			OperationContext operationContext = new OperationContextImpl();
			operationContext.setIncludeAcls(true);
			testDoc = (Document) session.getObject(testDoc, operationContext);

			System.out.println("ACL before adding an ace...");

			for (Ace ace : testDoc.getAcl().getAces()) {
				System.out.println("Found ace: " + ace.getPrincipalId() + " toString " + ace.toString());
			}

			List<String> permissions2 = new ArrayList<String>();
			permissions.add("cmis:read");
			String principal2 = "GROUP_user";
			Ace aceIn2 = session.getObjectFactory().createAce(principal2, permissions2);
			System.out.println("is direct ? " + aceIn2.isDirect());
			List<Ace> aceListIn2 = new ArrayList<Ace>();
			aceListIn.add(aceIn2);
			testDoc.addAcl(aceListIn2, AclPropagation.REPOSITORYDETERMINED);
			testDoc = (Document) session.getObject(testDoc, operationContext);

			System.out.println("ACL after adding an ace...");
			for (Ace ace : testDoc.getAcl().getAces()) {
				System.out.println("Found ace: " + ace.getPrincipalId() + " toString " + ace.toString());
			}
		}

	}
}
