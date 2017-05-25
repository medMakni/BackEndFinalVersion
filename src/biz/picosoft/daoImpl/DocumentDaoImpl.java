package biz.picosoft.daoImpl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import biz.picosoft.dao.DocumentDao;
import biz.picosoft.mains.TestDao;

public class DocumentDaoImpl implements DocumentDao {
	Session session;

	// just inserting a doc and not uploading it
	public void inserte(File file, Folder folder) throws FileNotFoundException {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document,P:cm:titled");
		properties.put(PropertyIds.NAME, file.getName());
		properties.put(PropertyIds.CREATED_BY, "admin");
		properties.put("cm:title", "Title8");
		properties.put("cm:description", "description8");

		// AlfrescoFolder folder1 = (AlfrescoFolder)
		// session.getObjectByPath("/");

		try {

			InputStream fis = new FileInputStream(file);
			VersioningState vs = VersioningState.MAJOR;
			DataInputStream dis = new DataInputStream(fis);
			byte[] bytes = new byte[(int) file.length()];
			dis.readFully(bytes);
			ContentStream contentStream =
					session.getObjectFactory().createContentStream(file.getName(),
					file.length(), "text/plain", new ByteArrayInputStream(bytes));
			
			AlfrescoDocument newDocument = (AlfrescoDocument) folder.createDocument(properties, contentStream, vs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete(CmisObject obj) {
		obj.delete(true);
	}

	public CmisObject getDocument(String id) {
		ObjectId idStringToId=new ObjectId() {
			
			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return id;
			}
		};
		return session.getObject(idStringToId);
	}

	public void update(Document document, Map<String, String> properties) {
		document.updateProperties(properties, true);
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public DocumentDaoImpl() {
		super();
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		this.session = ctx.getBean(Session.class);
		  
	}

}
