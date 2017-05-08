package biz.picosoft.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;

public interface DocumentDao {
	void inserte(File file,Folder folder) throws FileNotFoundException;

	void delete(CmisObject  document);

	CmisObject getDocument(ObjectId id);

	void update(Document document, Map<String, String> properties);
}
