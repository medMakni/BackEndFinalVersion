package biz.picosoft.mains;

import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import biz.picosoft.daoImpl.FolderDaoImpl;

public class TestAlfresco {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		FolderDaoImpl folderDaoImpl=new  FolderDaoImpl(session);
		Folder folder=(Folder) folderDaoImpl.getFolderById( "workspace://SpacesStore/df5e452b-46bf-4482-b8ba-dff1f9ab1965");
 
		System.out.println(  folderDaoImpl.getAllChildrens(folder) );
		
	}
}
