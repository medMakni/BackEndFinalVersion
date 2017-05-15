package biz.picosoft.mains;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.picosoft.services.CourriersArrivésImplLocal;

/**
 * Servlet implementation class Tes
 */
@WebServlet("/Tes")
public class Tes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Tes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn")
				.deploy().getId();
		System.out.println("idddddd" + deploymentId);
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// System.out.println(deploymentId);

		/*
		 * RuntimeService runtimeService = processEngine.getRuntimeService();
		 * Map<String, Object> variables = new HashMap<String, Object>();
		 * variables.put("employeeName", "Kermit"); ProcessInstance
		 * processInstance1 =
		 * runtimeService.startProcessInstanceByKey("courriersArrivés",variables
		 * ); System.out.println(runtimeService.getVariables((processInstance1.
		 * getId())).toString()); // FormService
		 * formService=processEngine.getFormService();
		 * 
		 * 
		 * // formService.submitStartFormData(
		 * processInstance1.getProcessDefinitionId(), formProperties ); //
		 * runtimeService.setVariable(processInstance1.getId(), "foo",
		 * "truxwwxe"); // Map<String, Object> formProperties2 =
		 * processInstance1.getProcessVariables(); // Map<String, Object>
		 * formProperties2 = processInstance1.getProcessVariables();
		 * System.out.println(processInstance1.getProcessVariables().size() );
		 * 
		 * // String formKey =
		 * formService.getStartFormData(processInstance1.getProcessDefinitionId(
		 * )).getFormKey();
		 * //response.sendRedirect("http://localhost:8081/BackEndFinalVersion"+
		 * formKey);
		 * 
		 * // String formProperties2 =
		 * formService.getStartFormData(processInstance1.getProcessDefinitionId(
		 * )).getFormProperties().get(0).getName();
		 * 
		 * // System.out.println("proppppp"+formProperties2);
		 * //System.out.println(processInstance1.getId()+"   "+processInstance1.
		 * getActivityId()+"   "+processInstance1.getProcessDefinitionId());
		 * 
		 */

		CourriersArrivésImplLocal courriersArrivésImplLocal = new CourriersArrivésImplLocal();

		Map<String, Object> proprietés = new HashMap<String, Object>();
		proprietés.put("date", "19-5-5");
		proprietés.put("département", "rh");
		proprietés.put("isValidated", false);

		ProcessInstance processInstance = courriersArrivésImplLocal.créerCourrier(proprietés);
		courriersArrivésImplLocal.réviser(processInstance.getId(), true);
		System.out.println(runtimeService.getVariables(processInstance.getId()).toString());

		System.out.println("xbcvbcvbcvbb");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
