package biz.picosoft.daoImpl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.enums.BindingType;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.alfresco.cmis.client.AlfrescoFolder;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

import biz.picosoft.dao.DocumentDao;

public class DocumentDaoImpl implements DocumentDao {
	Session session;
	
//just inserting a doc and not uploading it 
	public void inserte(File file,Folder folder) throws FileNotFoundException{
		

		   Map<String, Object> properties = new HashMap<String, Object>();
		    properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document,P:cm:titled");
		    properties.put(PropertyIds.NAME, file.getName());
		    properties.put(PropertyIds.CREATED_BY, "admin");
		    properties.put("cm:title", "Title8");
		    properties.put("cm:description", "description8");


		   //    AlfrescoFolder folder1 = (AlfrescoFolder) session.getObjectByPath("/");
		      
				try {
					
					InputStream fis = new FileInputStream(file);
			         VersioningState vs = VersioningState.MAJOR;
			         DataInputStream dis = new DataInputStream(fis);
			         byte[] bytes = new byte[(int) file.length()];
			         dis.readFully(bytes);
			         ContentStream contentStream = new ContentStreamImpl(file
			               .getAbsolutePath(), null,URLConnection.guessContentTypeFromName(file.getName()),
			               new ByteArrayInputStream(bytes));
			         
			         AlfrescoDocument newDocument = (AlfrescoDocument) folder
			               .createDocument(properties, contentStream, vs);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 
	}


	public void delete(CmisObject obj) {
		obj.delete(true);
	}

	public CmisObject getDocument(ObjectId id) {
		return  session.getObject(id);
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

 

}
