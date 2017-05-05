package biz.picosoft.mains;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration("activit.cfg.xml")
public class TestWorkflow {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
		String deploymentId = repositoryService.createDeployment().addClasspathResource("CourriersArrivés.bpmn").deploy().getId();
		
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		
		// System.out.println(deploymentId);

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("process_pool1");
  
	}

}
