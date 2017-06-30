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

import biz.picosoft.services.CourriersArrivésImpl;
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
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn")
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

		CourriersArrivésImpl courriersArrivésImplLocal = new CourriersArrivésImpl();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		Map<String, Object> proprietés = new HashMap<String, Object>();
		proprietés.put("date", "19-5-5");
		proprietés.put("départmentId", "Direction Générale");
		proprietés.put("isValidated", true);
		proprietés.put("expéditeur", "noz");
		proprietés.put("déstinataire", "Direction IT");
		proprietés.put("isFinished", false);
		proprietés.put("société", "ENISo45");
		proprietés.put("objet", "facture");
		Map<String, Object> commentHistory = new HashMap<>();
		proprietés.put("commentHistory", commentHistory);
		File file = new File("C:/test2.jpg");
		File file2 = new File("C:/Mon-CV.pdf");
		List listePiécesJointes = new ArrayList<>();
		listePiécesJointes.add(file);
		listePiécesJointes.add(file2);
		proprietés.put("listePiécesJointes", listePiécesJointes);
		ProcessInstance processInstance = courriersArrivésImplLocal.créerCourrier(proprietés);

		System.out.println(courriersArrivésImplLocal.getRuntimeService().getVariables(processInstance.getId()));
StatisticServiceImpl statisticServiceImpl=new  StatisticServiceImpl();
		System.out.println(statisticServiceImpl.getRateOfCourrierArrivéPerUser("rb"));
 
		
		/*courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		System.out.println("active " + courriersArrivésImplLocal.getListActiveCourriersParUser("rb"));
	/*	Map<String, Object> map = new HashMap<>();
		map.put("idCourrier", processInstance.getId());
		map.put("username", "fbm");
		map.put("idDepartement", "java");
		map.put("annotation", "hello fbm");
		courriersArrivésImplLocal.traiterCourrier(map);
		System.out.println(
				"active " + courriersArrivésImplLocal.getRuntimeService().getVariables(processInstance.getId()));
		//courriersArrivésImplLocal.archiverCourrier(processInstance.getId());
		courriersArrivésImplLocal.delete(processInstance.getId());
		System.out.println("active " + courriersArrivésImplLocal.getListActiveCourriersParUser("fb"));
	 
		System.out.println("finished"+courriersArrivésImplLocal.getListActiveCourriersParUser("fbm") );
		// proprietés.put("affectedTo", "DirectionCommerciale");

		// courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		// proprietés);
		/*
		 * System.out.println("siz oactive tasks " +
		 * courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("rb"))
		 * ; proprietés.put("affectedTo", "java");
		 * courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		 * proprietés); System.out.println("siz oactive tasks " +
		 * courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("fb"))
		 * ; proprietés.put("isFinished", true);
		 * courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		 * proprietés);
		 */

		// System.out.println("finished"+courriersArrivésImplLocal.getListFinishedCourrierArrivéPerUser("am").size());
		// StatisticServiceImpl statisticServiceImpl=new StatisticServiceImpl();

		// System.out.println( "actif
		// pro"+courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("am").size()+
		// " "+(
		// courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("am").size()+
		// courriersArrivésImplLocal.getListFinishedCourrierArrivéPerUser("am").size())
		// ) ;
		/*
		 * System.out.println("ended by dire "+
		 * courriersArrivésImplLocal.getNbrOfFinishedCourrierArrivéParDirection(
		 * "DirectionGénérale"));
		 * System.out.println("is it ended ?  "+courriersArrivésImplLocal.
		 * getFinishedCourrier().size());
		 * System.out.println("siz oactive tasks " +
		 * courriersArrivésImplLocal.getTaskService().createTaskQuery()
		 * .processInstanceId(processInstance.getId()).list().size());
		 * System.out.println("finished proc per dire "+statisticsService.
		 * getNbrFinishedCourrierArrivéPerDirection()+"active proc are "
		 * +statisticsService.getNbrActiveCourrierArrivéPerDirection()
		 * +"active in total "+courriersArrivésImplLocal.
		 * getListCourriersArrivées().size()+"   total finished "+
		 * statisticsService.getNumberOfFinishedCourrier());
		 */
		// System.out.println("robert is active tasks"
		// +courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("rb"));

		// System.out.println(
		// courriersArrivésImplLocal.getCourrierDetails(processInstance.getId()
		// ));

		/*
		 * Map<String, Object> proprietés = new HashMap<String, Object>();
		 * ======= <<<<<<< HEAD /* CourriersArrivésServices
		 * courriersArrivésImplLocal = new CourriersArrivésImpl(); Map<String,
		 * Object> proprietés = new HashMap<String, Object>(); >>>>>>> branch
		 * 'master' of https://github.com/medMakni/BackEndFinalVersion.git
		 * ======= CourriersArrivésServices courriersArrivésImplLocal = new
		 * CourriersArrivésImpl(); Map<String, Object> proprietés = new
		 * HashMap<String, Object>(); proprietés.put("date", "19-5-5");
		 * proprietés.put("départmentId", "chefsIT");
		 * proprietés.put("isValidated", true); proprietés.put("expéditeur",
		 * "noz"); proprietés.put("isFinished", false);
		 * proprietés.put("société", "Steg"); proprietés.put("objet",
		 * "facture"); File file = new File("D://cv/cover letter.docx"); List
		 * listePiécesJointes = new ArrayList<>(); listePiécesJointes.add(file);
		 * proprietés.put("listePiécesJointes", listePiécesJointes);
		 * ProcessInstance processInstance =
		 * courriersArrivésImplLocal.créerCourrier(proprietés);
		 * 
		 * 
		 * System.out.println("active tasks for weld ankoud :p "
		 * +courriersArrivésImplLocal.getListCourriersArrivées());
		 * 
		 * 
		 * /* Map<String, Object> proprietés = new HashMap<String, Object>();
		 * >>>>>>> branch 'master' of
		 * https://github.com/medMakni/BackEndFinalVersion.git
		 * proprietés.put("date", "19-5-5"); proprietés.put("départmentId",
		 * "chefsIT"); proprietés.put("isValidated", true);
		 * proprietés.put("expéditeur", "Steg"); proprietés.put("isFinished",
		 * false); File file = new File("D://cv/cover letter.docx"); List
		 * listePiécesJointes = new ArrayList<>(); listePiécesJointes.add(file);
		 * proprietés.put("listePiécesJointes", listePiécesJointes);
		 * ProcessInstance processInstance =
		 * courriersArrivésImplLocal.créerCourrier(proprietés); <<<<<<< HEAD
		 * System.out.println("BO"+courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("rb"));
		 * courriersArrivésImplLocal.réviser(processInstance.getId(), false);
		 * System.out.println("secrét"+courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("ac"));
		 * 
		 * //System.out.println(courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("fbm")); HistoryService
		 * historyService=((CourriersArrivésImpl)
		 * courriersArrivésImplLocal).getProcessEngine().getHistoryService();
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
		// System.out.println(courriersArrivésImplLocal.getListCourriersArrivées());
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
		 * courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("jm"))
		 * ; courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		 * System.out.println("chef It" +
		 * courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("rb"))
		 * ; proprietés.put("affectedTo", "secrétaireitù");
		 * proprietés.put("isFinished", false);
		 * courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		 * proprietés); System.out.println("siz oactive tasks " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * 
		 * proprietés.replace("isFinished", true);
		 * courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		 * proprietés); System.out.println("siz oactive tasks lev 2 " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * System.out.println(runtimeService.createProcessInstanceQuery().active
		 * ().count()); HistoryService historyService =
		 * processEngine.getHistoryService();
		 * System.out.println(courriersArrivésImplLocal.
		 * getListFinishedCourrierArrivéPerUser("ha"));
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

		// CourriersArrivésImpl courriersArrivésImplLocal=new
		// CourriersArrivésImpl();
		// repositoryService.createDeployment().addClasspathResource("myProcess.bpmn").deploy();
		/*
		 * Map<String, Object> proprietés = new HashMap<String, Object>();
		 * proprietés.put("date", "19-5-5"); proprietés.put("départmentId",
		 * "chefsIT"); proprietés.put("isValidated", true);
		 * proprietés.put("expéditeur", "noz"); proprietés.put("isFinished",
		 * false); proprietés.put("société", "Steg"); proprietés.put("objet",
		 * "facture");
		 * 
		 * File file = new File("D://cv/cover letter.docx"); List
		 * listePiécesJointes = new ArrayList<>(); listePiécesJointes.add(file);
		 * proprietés.put("listePiécesJointes", listePiécesJointes);
		 * ProcessInstance processInstance =
		 * courriersArrivésImplLocal.créerCourrier(proprietés);
		 */
		/*
		 * ProcessEngine processEngine = (ProcessEngine)
		 * applicationContext.getBean("processEngine"); RuntimeService
		 * runtimeService = processEngine.getRuntimeService(); TaskService
		 * taskService = processEngine.getTaskService();
		 */

		// System.out.println("robert is active tasks"
		// +courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("rb"));

		// System.out.println(
		// courriersArrivésImplLocal.getCourrierDetails(processInstance.getId()
		// ));

		/*
		 * Map<String, Object> proprietés = new HashMap<String, Object>();
		 * ======= <<<<<<< HEAD /* CourriersArrivésServices
		 * courriersArrivésImplLocal = new CourriersArrivésImpl(); Map<String,
		 * Object> proprietés = new HashMap<String, Object>(); >>>>>>> branch
		 * 'master' of https://github.com/medMakni/BackEndFinalVersion.git
		 * ======= CourriersArrivésServices courriersArrivésImplLocal = new
		 * CourriersArrivésImpl(); Map<String, Object> proprietés = new
		 * HashMap<String, Object>(); proprietés.put("date", "19-5-5");
		 * proprietés.put("départmentId", "chefsIT");
		 * proprietés.put("isValidated", true); proprietés.put("expéditeur",
		 * "noz"); proprietés.put("isFinished", false);
		 * proprietés.put("société", "Steg"); proprietés.put("objet",
		 * "facture"); File file = new File("D://cv/cover letter.docx"); List
		 * listePiécesJointes = new ArrayList<>(); listePiécesJointes.add(file);
		 * proprietés.put("listePiécesJointes", listePiécesJointes);
		 * ProcessInstance processInstance =
		 * courriersArrivésImplLocal.créerCourrier(proprietés);
		 * 
		 * 
		 * System.out.println("active tasks for weld ankoud :p "
		 * +courriersArrivésImplLocal.getListCourriersArrivées());
		 * 
		 * 
		 * /* Map<String, Object> proprietés = new HashMap<String, Object>();
		 * >>>>>>> branch 'master' of
		 * https://github.com/medMakni/BackEndFinalVersion.git
		 * proprietés.put("date", "19-5-5"); proprietés.put("départmentId",
		 * "chefsIT"); proprietés.put("isValidated", true);
		 * proprietés.put("expéditeur", "Steg"); proprietés.put("isFinished",
		 * false); File file = new File("D://cv/cover letter.docx"); List
		 * listePiécesJointes = new ArrayList<>(); listePiécesJointes.add(file);
		 * proprietés.put("listePiécesJointes", listePiécesJointes);
		 * ProcessInstance processInstance =
		 * courriersArrivésImplLocal.créerCourrier(proprietés); <<<<<<< HEAD
		 * System.out.println("BO"+courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("rb"));
		 * courriersArrivésImplLocal.réviser(processInstance.getId(), false);
		 * System.out.println("secrét"+courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("ac"));
		 * 
		 * //System.out.println(courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("fbm")); HistoryService
		 * historyService=((CourriersArrivésImpl)
		 * courriersArrivésImplLocal).getProcessEngine().getHistoryService();
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
		// System.out.println(courriersArrivésImplLocal.getListCourriersArrivées());
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
		 * courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("jm"))
		 * ; courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		 * System.out.println("chef It" +
		 * courriersArrivésImplLocal.getListActiveCourriersArrivésParUser("rb"))
		 * ; proprietés.put("affectedTo", "secrétaireitù");
		 * proprietés.put("isFinished", false);
		 * courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		 * proprietés); System.out.println("siz oactive tasks " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * 
		 * proprietés.replace("isFinished", true);
		 * courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		 * proprietés); System.out.println("siz oactive tasks lev 2 " +
		 * taskService.createTaskQuery().processInstanceId(processInstance.getId
		 * ()).list().size());
		 * System.out.println(runtimeService.createProcessInstanceQuery().active
		 * ().count()); HistoryService historyService =
		 * processEngine.getHistoryService();
		 * System.out.println(courriersArrivésImplLocal.
		 * getListFinishedCourrierArrivéPerUser("ha"));
		 */

		// ena taw 3malt ili finished w process which include mr x
		// courriersArrivésImplLocal.traiterCourrier(processInstance.getId(),
		// proprietés);
		/*
		 * //System.out.println(courriersArrivésImplLocal.
		 * getListCourriersArrivésParUser("fbm")); HistoryService
		 * historyService=((CourriersArrivésImpl)
		 * courriersArrivésImplLocal).getProcessEngine().getHistoryService();
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
		 * System.out.println(courriersArrivésImplLocal.getListCourriersArrivées
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
		 * CourriersArrivésImpl courriersArrivésImplLocal = new
		 * CourriersArrivésImpl(); ProcessEngine processEngine =
		 * courriersArrivésImplLocal.getProcessEngine(); RuntimeService
		 * runtimeService = processEngine.getRuntimeService();
		 * 
		 * // courriersArrivésImplLocal.setSession(session); Map<String, Object>
		 * proprietés = new HashMap<String, Object>(); proprietés.put("date",
		 * "19-5-5"); proprietés.put("départmentId", "ROLE_ADMIN");
		 * proprietés.put("isValidated", false); proprietés.put("expéditeur",
		 * "Steg");
		 * 
		 * proprietés.put("listePiécesJointes", listePiécesJointes);
		 * ProcessInstance processInstance =
		 * courriersArrivésImplLocal.créerCourrier(proprietés);
		 * courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		 * System.out.println(runtimeService.getVariables(processInstance.getId(
		 * )).toString());
		 */

		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().toString());

		// ProcessInstance processInstance1 =
		// runtimeService.startProcessInstanceByKey("myProcess");
		// System.out.println(taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list().toString());

	}
}