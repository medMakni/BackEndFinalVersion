package biz.picosoft.dao;

import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;

public interface FolderDao {
	public ItemIterable<CmisObject> getAllChildrens(Folder folder);

	public CmisObject getFolderByPath(String path);

	public CmisObject getFolderById(ObjectId id);

	public void updateFolder(Folder folder, Map<String, Object> updateProperties);

	public void deleteFolder(Folder folder);

	public Folder createFolder(Folder rootFolder, String name);
}
