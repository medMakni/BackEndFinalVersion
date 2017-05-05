package biz.picosoft.mains;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn").deploy().getId();
		System.out.println("idddddd"+deploymentId);
		 ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		
		// System.out.println(deploymentId);

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("process_pool1");
		
		FormService formService=processEngine.getFormService();
		 String formKey = formService.getStartFormData(processInstance1.getProcessDefinitionId()).getFormKey();
		 response.sendRedirect("http://localhost:8081/BackEndFinalVersion"+formKey);
		//System.out.println(processInstance1.getId()+"   "+processInstance1.getActivityId()+"   "+processInstance1.getProcessDefinitionId()); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
