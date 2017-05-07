package biz.picosoft.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;

import biz.picosoft.daoImpl.DocumentDaoImpl;
import biz.picosoft.daoImpl.FolderDaoImpl;
import biz.picosoft.entity.Courrier;

public class CourriersArriv�sImplLocal implements CourriersArriv�sServices {
	ProcessEngine processEngine;
	Session session;

	@Override
	//this method create a mail process and attach its file to it by calling the attach file method 
	//and then attach the folder of the mail to 
	public ProcessInstance cr�erCourrier(Map<String, Object> propriet�sCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("courriersArriv�s",
				propriet�sCourrier);
		List<File> listePi�cesJointes = (List<File>) propriet�sCourrier.get("listePi�cesJointes");
		if (listePi�cesJointes != null) {
			String idCourrierArriv�Folder = attachFiles(listePi�cesJointes,
					(String) propriet�sCourrier.get("exp�diteur"), processInstance.getId());
			propriet�sCourrier.put("idCourrierArriv�Folder", idCourrierArriv�Folder);
			runtimeService.setVariables(processInstance.getId(), propriet�sCourrier);

		} // TODO Do not forget redirection with dipatcher
		return processInstance;
	}

	@Override
	public void validerCourrier(String idCourrier, boolean isValidated) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		Map<String, Object> propriet�sCourrier = runtimeService.getVariables((idCourrier));
		propriet�sCourrier.replace("isValidated", isValidated);

		runtimeService.setVariables(processInstance.getId(), propriet�sCourrier);
	}

	@Override
	public void traiterCourrier(String idCourrier, Map<String, Object> nouvellesPropriet�sCourrier) {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(idCourrier)
				.singleResult();
		runtimeService.setVariables(processInstance.getDeploymentId(), nouvellesPropriet�sCourrier);
	}

	@Override
	public void archiverCourrier(String idCourrier) {

	}

	@Override
	public List<Courrier> getListCourriersArriv�es() {

		return null;
	}
	// this method attach files to a process and return the folder id
	// it checks also if necessary folder are already created or not if not it will create it 
	@Override
		public String attachFiles(List<File> listePi�cesJointes, String exp�diteur, String id) {
			Folder folderCourrier = null;
			if (listePi�cesJointes != null) {
				DocumentDaoImpl documentDaoImpl = new DocumentDaoImpl();
				FolderDaoImpl folderDaoImpl = new FolderDaoImpl();
				folderDaoImpl.setSession(session);
				Folder courriersArriv�sFolderPerYear;
				Folder courriersArriv�sFolder;

				try {
					try {
						courriersArriv�sFolder = (Folder) folderDaoImpl.getFolderByPath("/courriersArriv�s");
					} catch (Exception myExction) {
						courriersArriv�sFolder = folderDaoImpl.createFolder((Folder) folderDaoImpl.getFolderByPath("/"),
								"courriersArriv�s");
					}
					courriersArriv�sFolderPerYear = (Folder) folderDaoImpl.getFolderByPath(
							courriersArriv�sFolder.getPath() + "/" + Calendar.getInstance().get(Calendar.YEAR));
				} catch (Exception myExction) {

					courriersArriv�sFolderPerYear = folderDaoImpl.createFolder(
							(Folder) folderDaoImpl.getFolderByPath("/courriersArriv�s"),
							Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
				}
				folderCourrier = folderDaoImpl.createFolder(courriersArriv�sFolderPerYear, exp�diteur + id);
				for (int i = 0; i < listePi�cesJointes.size(); i++) {
					try {
						documentDaoImpl.inserte(listePi�cesJointes.get(i), folderCourrier);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

			}

			return folderCourrier.getId();
		}
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public CourriersArriv�sImplLocal(ProcessEngine processEngine) {
		super();
		this.processEngine = processEngine;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	
}
