package biz.picosoft.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;

import biz.picosoft.dao.FolderDao;

public class FolderDaoImpl implements FolderDao {
	Session session;

	public List<String> getAllChildrens(Folder folder) {
		List<CmisObject> listOfChildrens = new ArrayList<>();
		folder.getChildren().iterator().forEachRemaining(listOfChildrens::add);
		List<String> idOfFoldChildrenList=new ArrayList<>(); 
		for(int i=0;i<listOfChildrens.size();i++){
			idOfFoldChildrenList.add(listOfChildrens.get(i).getId().substring(0,listOfChildrens.get(i).getId().indexOf(";")));
		}
		return idOfFoldChildrenList;
	}

	public CmisObject getFolderByPath(String path) {

		CmisObject cmisObject = session.getObjectByPath(path);

		return cmisObject;
	}

	public void updateFolder(Folder folder, Map<String, Object> updateProperties) {

		folder.updateProperties(updateProperties);
	}

	public void deleteFolder(Folder folder) {

		System.out.println("delete the 'ADGFolder1' tree");
		folder.deleteTree(true, UnfileObject.DELETE, true);
	}

	public Folder createFolder(Folder rootFolder, String name) {
		Map<String, String> newFolderProps = new HashMap<String, String>();
		newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		newFolderProps.put(PropertyIds.NAME, name);
		Folder newFolder = rootFolder.createFolder(newFolderProps);
		return newFolder;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public CmisObject getFolderById(String id) {
		ObjectId objectId = new ObjectId() {

			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return id;
			}
		};
		CmisObject cmisObject = session.getObject(objectId);

		return cmisObject;
	}

	public FolderDaoImpl(Session session) {
		super();
		this.session = session;
	}

	@Override
	public void folderPermission(String folderId,String affectedGroup){
		 
		List<String> permissions = new ArrayList<String>();
		permissions.add("cmis:read");
		String principal = "GROUP_"+affectedGroup;
		Ace aceIn = session.getObjectFactory().createAce(principal, permissions);
		System.out.println("is direct ? " + aceIn.isDirect());
		List<Ace> aceListIn = new ArrayList<Ace>();
		aceListIn.add(aceIn);
		 CmisObject folder = getFolderById(folderId);
		folder.addAcl(aceListIn, AclPropagation.REPOSITORYDETERMINED);

	}

}
