package biz.picosoft.mains;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.services.CourriersArriv�sImpl;
import biz.picosoft.services.StatisticServiceImpl;

@Configuration
public class TestDao {
	@Bean
	public Session getAlfrescoSession() {
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, "admin");
		parameter.put(SessionParameter.PASSWORD, "admin");

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/alfresco/cmisatom");
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		System.out.println(BindingType.ATOMPUB.value());
		// set the alfresco object factory
		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

		// create session
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Session session = factory.getRepositories(parameter).get(0).createSession();
		return session;
	}

	protected static byte[] readContent(InputStream stream) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[4096];
		int b;
		while ((b = stream.read(buffer)) > -1) {
			baos.write(buffer, 0, b);
		}

		return baos.toByteArray();
	}

	public static void main(String[] args) throws Exception {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDao.class);
		Session session = ctx.getBean(Session.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArriv�s.bpmn")
				.deploy().getId();
		/*
		 * DocumentDaoImpl dao=new DocumentDaoImpl();
		 * 
		 * 
		 * Document docCmis = (Document) dao.getDocument(
		 * "workspace://SpacesStore/c7ff0e49-f2f6-44ed-9857-8e9fbcd21423");
		 * 
		 * System.out.println(docCmis.getPaths());;
		 */

		/*
		 * byte[] myByteArray =
		 * readContent(docCmis.getContentStream().getStream()); File outputFile
		 * = new File("D:/"+ docCmis.getContentStreamFileName());
		 * FileOutputStream fileOuputStream = null; try { fileOuputStream = new
		 * FileOutputStream(outputFile); fileOuputStream.write(myByteArray); }
		 * catch (Exception e) { e.printStackTrace(); }
		 */

		/*
		 * InputStream stream = null; List<Rendition> renditions =
		 * docCmis.getRenditions(); if (renditions != null) { for (Rendition
		 * rendition: renditions) { if (rendition.getHeight() == 16) { String
		 * streamId = rendition.getStreamId(); stream =
		 * rendition.getContentStream().getStream(); break; } } } byte[]
		 * myByteArray= readContent( stream ) ;
		 * 
		 * Path path = Paths.get("C:/myfile"); Files.write(path, myByteArray);
		 * 
		 * 
		 */

		CourriersArriv�sImpl courriersArriv�sImplLocal = new CourriersArriv�sImpl();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		Map<String, Object> propriet�s = new HashMap<String, Object>();
		propriet�s.put("date", "19-5-5");
		propriet�s.put("d�partmentId", "Direction G�n�rale");
		propriet�s.put("isValidated", true);
		propriet�s.put("exp�diteur", "noz");
		propriet�s.put("d�stinataire", "Direction IT");
		propriet�s.put("isFinished", false);
		propriet�s.put("soci�t�", "ENISo45");
		propriet�s.put("objet", "facture");
		Map<String, Object> commentHistory = new HashMap<>();
		propriet�s.put("commentHistory", commentHistory);
		File file = new File("C:/test2.jpg");
		File file2 = new File("C:/Mon-CV.pdf");
		List listePi�cesJointes = new ArrayList<>();
		listePi�cesJointes.add(file);
		listePi�cesJointes.add(file2);
		propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		ProcessInstance processInstance = courriersArriv�sImplLocal.cr�erCourrier(propriet�s);

		System.out.println(courriersArriv�sImplLocal.getRuntimeService().getVariables(processInstance.getId()));
StatisticServiceImpl statisticServiceImpl=new  StatisticServiceImpl();
		System.out.println(statisticServiceImpl.getRateOfCourrierArriv�PerUser("rb"));
 
		
		/*courriersArriv�sImplLocal.r�viser(processInstance.getId(), true);
		System.out.println("active " + courriersArriv�sImplLocal.getListActiveCourriersParUser("rb"));
	/*	Map<String, Object> map = new HashMap<>();
		map.put("idCourrier", processInstance.getId());
		map.put("username", "fbm");
		map.put("idDepartement", "java");
		map.put("annotation", "hello fbm");
		courriersArriv�sImplLocal.traiterCourrier(map);
		System.out.println(
				"active " + courriersArriv�sImplLocal.getRuntimeService().getVariables(processInstance.getId()));
		//courriersArriv�sImplLocal.archiverCourrier(processInstance.getId());
		courriersArriv�sImplLocal.delete(processInstance.getId());
		System.out.println("active " + courriersArriv�sImplLocal.getListActiveCourriersParUser("fb"));
	 
		System.out.println("finished"+courriersArriv�sImplLocal.getListActiveCourriersParUser("fbm") );
		// propriet�s.put("affectedTo", "DirectionCommerciale");

		// courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		// propriet�s);
		/*
		 * System.out.println("siz oactive tasks " +
		 * courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("rb"))
		 * ; propriet�s.put("affectedTo", "java");
		 * courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		 * propriet�s); System.out.println("siz oactive tasks " +
		 * courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("fb"))
		 * ; propriet�s.put("isFinished", true);
		 * courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		 * propriet�s);
		 */

		// System.out.println("finished"+courriersArriv�sImplLocal.getListFinishedCourrierArriv�PerUser("am").size());
		// StatisticServiceImpl statisticServiceImpl=new StatisticServiceImpl();

		// System.out.println( "actif
		// pro"+courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("am").size()+
		// " "+(
		// courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("am").size()+
		// courriersArriv�sImplLocal.getListFinishedCourrierArriv�PerUser("am").size())
		// ) ;
		/*
		 * System.out.println("ended by dire "+
		 * courriersArriv�sImplLocal.getNbrOfFinishedCourrierArriv�ParDirection(
		 * "DirectionG�n�rale"));
		 * System.out.println("is it ended ?  "+courriersArriv�sImplLocal.
		 * getFinishedCourrier().size());
		 * System.out.println("siz oactive tasks " +
		 * courriersArriv�sImplLocal.getTaskService().createTaskQuery()
		 * .processInstanceId(processInstance.getId()).list().size());
		 * System.out.println("finished proc per dire "+statisticsService.
		 * getNbrFinishedCourrierArriv�PerDirection()+"active proc are "
		 * +statisticsService.getNbrActiveCourrierArriv�PerDirection()
		 * +"active in total "+courriersArriv�sImplLocal.
		 * getListCourriersArriv�es().size()+"   total finished "+
		 * statisticsService.getNumberOfFinishedCourrier());
		 */
		// System.out.println("robert is active tasks"
		// +courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("rb"));

		// System.out.println(
		// courriersArriv�sImplLocal.getCourrierDetails(processInstance.getId()
		// ));

		/*
		 * Map<String, Object> propriet�s = new HashMap<String, Object>();
		 * ======= <<<<<<< HEAD /* CourriersArriv�sServices
		 * courriersArriv�sImplLocal = new CourriersArriv�sImpl(); Map<String,
		 * Object> propriet�s = new HashMap<String, Object>(); >>>>>>> branch
		 * 'master' of https://github.com/medMakni/BackEndFinalVersion.git
		 * ======= CourriersArriv�sServices courriersArriv�sImplLocal = new
		 * CourriersArriv�sImpl(); Map<String, Object> propriet�s = new
		 * HashMap<String, Object>(); propriet�s.put("date", "19-5-5");
		 * propriet�s.put("d�partmentId", "chefsIT");
		 * propriet�s.put("isValidated", true); propriet�s.put("exp�diteur",
		 * "noz"); propriet�s.put("isFinished", false);
		 * propriet�s.put("soci�t�", "Steg"); propriet�s.put("objet",
		 * "facture"); File file = new File("D://cv/cover letter.docx"); List
		 * listePi�cesJointes = new ArrayList<>(); listePi�cesJointes.add(file);
		 * propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		 * ProcessInstance processInstance =
		 * courriersArriv�sImplLocal.cr�erCourrier(propriet�s);
		 * 
		 * 
		 * System.out.println("active tasks for weld ankoud :p "
		 * +courriersArriv�sImplLocal.getListCourriersArriv�es());
		 * 
		 * 
		 * /* Map<String, Object> propriet�s = new HashMap<String, Object>();
		 * >>>>>>> branch 'master' of
		 * https://github.com/medMakni/BackEndFinalVersion.git
		 * propriet�s.put("date", "19-5-5"); propriet�s.put("d�partmentId",
		 * "chefsIT"); propriet�s.put("isValidated", true);
		 * propriet�s.put("exp�diteur", "Steg"); propriet�s.put("isFinished",
		 * false); File file = new File("D://cv/cover letter.docx"); List
		 * listePi�cesJointes = new ArrayList<>(); listePi�cesJointes.add(file);
		 * propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		 * ProcessInstance processInstance =
		 * courriersArriv�sImplLocal.cr�erCourrier(propriet�s); <<<<<<< HEAD
		 * System.out.println("BO"+courriersArriv�sImplLocal.
		 * getListCourriersArriv�sParUser("rb"));
		 * courriersArriv�sImplLocal.r�viser(processInstance.getId(), false);
		 * System.out.println("secr�t"+courriersArriv�sImplLocal.
		 * getListCourriersArriv�sParUser("ac"));
		 * 
		 * //System.out.println(courriersArriv�sImplLocal.
		 * getListCourriersArriv�sParUser("fbm")); HistoryService
		 * historyService=((CourriersArriv�sImpl)
		 * courriersArriv�sImplLocal).getProcessEngine().getHistoryService();
		 * List<HistoricActivityInstance> historicActivityInstances =
		 * historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list();
		 * System.out.println(historicActivityInstances);
		 * historicActivityInstances = historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list(); /*
		 * ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess"); List<Task>
		 * taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskb);
		 */
		// System.out.println(courriersArriv�sImplLocal.getListCourriersArriv�es());
		/*
		 * ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess");
		 * taskService.addCandidateUser(taskService.createTaskQuery().
		 * processInstanceId(processInstance1.getId()).list().get(0).getId(),
		 * "fbm");
		 * 
		 * List<Task> taskByProceeAndUser =
		 * taskService.createTaskQuery().processDefinitionKey("myProcess").
		 * taskCandidateUser("mwm") .list(); List<Task> taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskByProceeAndUser.size());
		 * System.out.println(taskb.size());
		 */
		/*
		 * System.out.println("BO" +
		 * courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("jm"))
		 * ; courriersArriv�sImplLocal.r�viser(processInstance.getId(), true);
		 * System.out.println("chef It" +
		 * courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("rb"))
		 * ; propriet�s.put("affectedTo", "secr�taireit�");
		 * propriet�s.put("isFinished", false);
		 * courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		 * propriet�s); System.out.println("siz oactive tasks " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * 
		 * propriet�s.replace("isFinished", true);
		 * courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		 * propriet�s); System.out.println("siz oactive tasks lev 2 " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * System.out.println(runtimeService.createProcessInstanceQuery().active
		 * ().count()); HistoryService historyService =
		 * processEngine.getHistoryService();
		 * System.out.println(courriersArriv�sImplLocal.
		 * getListFinishedCourrierArriv�PerUser("ha"));
		 */

		// ena taw 3malt ili finished w process which include mr x

		/*
		 * Document docCmis = (Document) dao.getDocument(
		 * "workspace://SpacesStore/18a09e1b-cb0b-42c8-b0a8-16e53dff75a8");
		 * 
		 * byte[] myByteArray =
		 * readContent(docCmis.getContentStream().getStream());
		 * 
		 * Path path = Paths.get("C:/" + docCmis.getContentStreamFileName());
		 * File outputFile = new File("D:/"+
		 * docCmis.getContentStreamFileName()); FileOutputStream fileOuputStream
		 * = null; try { fileOuputStream = new FileOutputStream(outputFile);
		 * fileOuputStream.write(myByteArray); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		// CourriersArriv�sImpl courriersArriv�sImplLocal=new
		// CourriersArriv�sImpl();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		/*
		 * Map<String, Object> propriet�s = new HashMap<String, Object>();
		 * propriet�s.put("date", "19-5-5"); propriet�s.put("d�partmentId",
		 * "chefsIT"); propriet�s.put("isValidated", true);
		 * propriet�s.put("exp�diteur", "noz"); propriet�s.put("isFinished",
		 * false); propriet�s.put("soci�t�", "Steg"); propriet�s.put("objet",
		 * "facture");
		 * 
		 * File file = new File("D://cv/cover letter.docx"); List
		 * listePi�cesJointes = new ArrayList<>(); listePi�cesJointes.add(file);
		 * propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		 * ProcessInstance processInstance =
		 * courriersArriv�sImplLocal.cr�erCourrier(propriet�s);
		 */
		/*
		 * ProcessEngine processEngine = (ProcessEngine)
		 * applicationContext.getBean("processEngine"); RuntimeService
		 * runtimeService = processEngine.getRuntimeService(); TaskService
		 * taskService = processEngine.getTaskService();
		 */

		// System.out.println("robert is active tasks"
		// +courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("rb"));

		// System.out.println(
		// courriersArriv�sImplLocal.getCourrierDetails(processInstance.getId()
		// ));

		/*
		 * Map<String, Object> propriet�s = new HashMap<String, Object>();
		 * ======= <<<<<<< HEAD /* CourriersArriv�sServices
		 * courriersArriv�sImplLocal = new CourriersArriv�sImpl(); Map<String,
		 * Object> propriet�s = new HashMap<String, Object>(); >>>>>>> branch
		 * 'master' of https://github.com/medMakni/BackEndFinalVersion.git
		 * ======= CourriersArriv�sServices courriersArriv�sImplLocal = new
		 * CourriersArriv�sImpl(); Map<String, Object> propriet�s = new
		 * HashMap<String, Object>(); propriet�s.put("date", "19-5-5");
		 * propriet�s.put("d�partmentId", "chefsIT");
		 * propriet�s.put("isValidated", true); propriet�s.put("exp�diteur",
		 * "noz"); propriet�s.put("isFinished", false);
		 * propriet�s.put("soci�t�", "Steg"); propriet�s.put("objet",
		 * "facture"); File file = new File("D://cv/cover letter.docx"); List
		 * listePi�cesJointes = new ArrayList<>(); listePi�cesJointes.add(file);
		 * propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		 * ProcessInstance processInstance =
		 * courriersArriv�sImplLocal.cr�erCourrier(propriet�s);
		 * 
		 * 
		 * System.out.println("active tasks for weld ankoud :p "
		 * +courriersArriv�sImplLocal.getListCourriersArriv�es());
		 * 
		 * 
		 * /* Map<String, Object> propriet�s = new HashMap<String, Object>();
		 * >>>>>>> branch 'master' of
		 * https://github.com/medMakni/BackEndFinalVersion.git
		 * propriet�s.put("date", "19-5-5"); propriet�s.put("d�partmentId",
		 * "chefsIT"); propriet�s.put("isValidated", true);
		 * propriet�s.put("exp�diteur", "Steg"); propriet�s.put("isFinished",
		 * false); File file = new File("D://cv/cover letter.docx"); List
		 * listePi�cesJointes = new ArrayList<>(); listePi�cesJointes.add(file);
		 * propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		 * ProcessInstance processInstance =
		 * courriersArriv�sImplLocal.cr�erCourrier(propriet�s); <<<<<<< HEAD
		 * System.out.println("BO"+courriersArriv�sImplLocal.
		 * getListCourriersArriv�sParUser("rb"));
		 * courriersArriv�sImplLocal.r�viser(processInstance.getId(), false);
		 * System.out.println("secr�t"+courriersArriv�sImplLocal.
		 * getListCourriersArriv�sParUser("ac"));
		 * 
		 * //System.out.println(courriersArriv�sImplLocal.
		 * getListCourriersArriv�sParUser("fbm")); HistoryService
		 * historyService=((CourriersArriv�sImpl)
		 * courriersArriv�sImplLocal).getProcessEngine().getHistoryService();
		 * List<HistoricActivityInstance> historicActivityInstances =
		 * historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list();
		 * System.out.println(historicActivityInstances);
		 * historicActivityInstances = historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list(); /*
		 * ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess"); List<Task>
		 * taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskb);
		 */
		// System.out.println(courriersArriv�sImplLocal.getListCourriersArriv�es());
		/*
		 * ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess");
		 * taskService.addCandidateUser(taskService.createTaskQuery().
		 * processInstanceId(processInstance1.getId()).list().get(0).getId(),
		 * "fbm");
		 * 
		 * List<Task> taskByProceeAndUser =
		 * taskService.createTaskQuery().processDefinitionKey("myProcess").
		 * taskCandidateUser("mwm") .list(); List<Task> taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskByProceeAndUser.size());
		 * System.out.println(taskb.size());
		 */
		/*
		 * System.out.println("BO" +
		 * courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("jm"))
		 * ; courriersArriv�sImplLocal.r�viser(processInstance.getId(), true);
		 * System.out.println("chef It" +
		 * courriersArriv�sImplLocal.getListActiveCourriersArriv�sParUser("rb"))
		 * ; propriet�s.put("affectedTo", "secr�taireit�");
		 * propriet�s.put("isFinished", false);
		 * courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		 * propriet�s); System.out.println("siz oactive tasks " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * 
		 * propriet�s.replace("isFinished", true);
		 * courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		 * propriet�s); System.out.println("siz oactive tasks lev 2 " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * System.out.println(runtimeService.createProcessInstanceQuery().active
		 * ().count()); HistoryService historyService =
		 * processEngine.getHistoryService();
		 * System.out.println(courriersArriv�sImplLocal.
		 * getListFinishedCourrierArriv�PerUser("ha"));
		 */

		// ena taw 3malt ili finished w process which include mr x
		// courriersArriv�sImplLocal.traiterCourrier(processInstance.getId(),
		// propriet�s);
		/*
		 * //System.out.println(courriersArriv�sImplLocal.
		 * getListCourriersArriv�sParUser("fbm")); HistoryService
		 * historyService=((CourriersArriv�sImpl)
		 * courriersArriv�sImplLocal).getProcessEngine().getHistoryService();
		 * List<HistoricActivityInstance> historicActivityInstances =
		 * historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list();
		 * System.out.println(historicActivityInstances);
		 * historicActivityInstances = historyService.
		 * createHistoricActivityInstanceQuery().processInstanceId(
		 * processInstance.getId()).
		 * orderByHistoricActivityInstanceStartTime().asc().list();
		 */
		/*
		 * ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess"); List<Task>
		 * taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskb);
		 * System.out.println(courriersArriv�sImplLocal.getListCourriersArriv�es
		 * ()); /*ProcessInstance processInstance1 =
		 * runtimeService.startProcessInstanceByKey("myProcess");
		 * taskService.addCandidateUser(taskService.createTaskQuery().
		 * processInstanceId(processInstance1.getId()).list().get(0).getId(),
		 * "fbm");
		 * 
		 * List<Task> taskByProceeAndUser =
		 * taskService.createTaskQuery().processDefinitionKey("myProcess").
		 * taskCandidateUser("mwm") .list(); List<Task> taskb =
		 * taskService.createTaskQuery().taskCandidateUser("fbm").list();
		 * System.out.println(taskByProceeAndUser.size());
		 * System.out.println(taskb.size());
		 */
		// Folder root = session.getRootFolder();
		// FolderDaoImpl folderDaoImpl = new FolderDaoImpl();
		// folderDaoImpl.createFolder(root, "fatma2");

		/*
		 * Folder root = session.getRootFolder(); FolderDaoImpl
		 * folderDaoImpl=new FolderDaoImpl(); ItemIterable<CmisObject>
		 * list=folderDaoImpl.getAllChildrens(root); for (CmisObject o : list) {
		 * System.out.println(o.getName()); }
		 */

		/*
		 * DocumentDaoImpl doi=new DocumentDaoImpl(); doi.setSession(session);
		 * File file=new File("C://cover letter.pdf"); //doi.inserte(file);
		 * ObjectId o=new ObjectId() {
		 * 
		 * public String getId() { // TODO Auto-generated method stub return
		 * "workspace://SpacesStore/0139211c-67e0-4d81-a3f0-918de3e56b5b"; } };
		 * CmisObject obj= doi.getDocument(o);
		 * System.out.println(obj.getName()); doi.inserte(file);
		 */

		/*
		 * DocumentDaoImpl doi=new DocumentDaoImpl(); doi.setSession(session);
		 * File file=new File("C://cover letter.pdf"); //doi.inserte(file);
		 * ObjectId o=new ObjectId() {
		 * 
		 * public String getId() { // TODO Auto-generated method stub return
		 * "workspace://SpacesStore/ee6d0267-4ebc-43a7-a938-1115a9ad14b6"; } };
		 * folderDaoImpl.setSession(session); CmisObject
		 * obj=folderDaoImpl.getFolderById(o) ;
		 * System.out.println(obj.getName()); doi.inserte(file,(Folder) obj);
		 */

		/*
		 * CourriersArriv�sImpl courriersArriv�sImplLocal = new
		 * CourriersArriv�sImpl(); ProcessEngine processEngine =
		 * courriersArriv�sImplLocal.getProcessEngine(); RuntimeService
		 * runtimeService = processEngine.getRuntimeService();
		 * 
		 * // courriersArriv�sImplLocal.setSession(session); Map<String, Object>
		 * propriet�s = new HashMap<String, Object>(); propriet�s.put("date",
		 * "19-5-5"); propriet�s.put("d�partmentId", "ROLE_ADMIN");
		 * propriet�s.put("isValidated", false); propriet�s.put("exp�diteur",
		 * "Steg");
		 * 
		 * propriet�s.put("listePi�cesJointes", listePi�cesJointes);
		 * ProcessInstance processInstance =
		 * courriersArriv�sImplLocal.cr�erCourrier(propriet�s);
		 * courriersArriv�sImplLocal.r�viser(processInstance.getId(), true);
		 * System.out.println(runtimeService.getVariables(processInstance.getId(
		 * )).toString());
		 */

		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().toString());

		// ProcessInstance processInstance1 =
		// runtimeService.startProcessInstanceByKey("myProcess");
		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list().toString());

	}
}