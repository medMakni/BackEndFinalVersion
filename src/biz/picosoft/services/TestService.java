package biz.picosoft.services;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
@Configuration("activit.cfg.xml")
@Service("TestService")
public class TestService {
	/*ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
	RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
	String deploymentId = repositoryService.createDeployment().addClasspathResource("yz.bpmn").deploy().getId();
	String deploymentId2 = repositoryService.createDeployment().addClasspathResource("yfz.bpmn").deploy().getId();
	ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
	
	// System.out.println(deploymentId);

	RuntimeService runtimeService = processEngine.getRuntimeService();
	ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("myProcess");
	ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey("myProcess2");
	TaskService taskService = processEngine.getTaskService();
	List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list();
	List<Task> taskyfz = taskService.createTaskQuery().processInstanceId(processInstance2.getId()).list();*/
	
	//démarrer un workflow
	public void startWorkflow(){
		// System.out.println(processInstance.getId());
		//boolean b = processEngine.getIdentityService().checkPassword("fbm", "123");
		// boolean b =
		// processEngine.getIdentityService().createGroupQuery().groupName(Group)
		// NativeUserQuery task1 =
		// processEngine.getIdentityService().createNativeUserQuery();
		// runtimeService.startProcessInstanceByKey("simpleProcess");
		
		// taskService.complete(task.getId(), taskVariables);
		// Task task = taskService.createTaskQuery().singleResult();
		// String pro1=processEngine.getTaskService()
		// System.out.println(task1);
		// List<Task> tasks =
		// taskService.createTaskQuery().taskCandidateUser("fbm").list();

		//List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
		//System.out.println(deployments);
		/*
		 * String[] aa=a[1].split(","); String[] aaa1=aa[0].split("="); String[]
		 * aaa2=aa[3].split("="); System.out.println(aaa1[1]);
		 * System.out.println(aaa2[1]); System.out.println(b);
		 */
	
		// tasks.get(0).setAssignee("mwm");
		 
		// tasks.get(0).setOwner("fbm");
		// tasks.get(0).setAssignee("mwm");
		// tasks.get(0).delegate("mwm");
		//taskService.addCandidateGroup(tasks.get(0).getId(), "GROUP_Admin");
		// tasks.get(0).setDelegationState(DelegationState.RESOLVED);
		// aprés les opération il faut faire save pour sauvegarder les
		// changements
		//taskService.saveTask(tasks.get(0));
		//taskService.saveTask(taskyfz.get(0));

		/*List<Task> taskc = taskService.createTaskQuery().taskCandidateGroup("GROUPE_ADMIN").list();
		taskService.saveTask(tasks.get(0));
		taskService.saveTask(taskyfz.get(0));
		//System.out.println(taskc);
		String taskd = taskService.createTaskQuery().taskCandidateGroup("ROLE_ADMIN").list().get(0).getAssignee();
		taskService.saveTask(tasks.get(0));
		System.out.println(taskd);*/

		/*List<Task> taskb = taskService.createTaskQuery().taskCandidateUser("mwm").list();
		System.out.println(taskb);
		taskService.saveTask(tasks.get(0));
		taskService.saveTask(taskyfz.get(0));

		
		List<Task> taske = taskService.createTaskQuery().taskCandidateUser("fbm").list();
		System.out.println(taske);
		taskService.saveTask(tasks.get(0));
		taskService.saveTask(taskyfz.get(0));

		
		
		/*List<Task> taskf = taskService.createTaskQuery().taskCandidateUser("rb").list();
		System.out.println(taskf);
		taskService.saveTask(tasks.get(0));*/
		// List<Task> taska =
		// taskService.createTaskQuery().taskAssignee("mwm").taskDelegationState(DelegationState.RESOLVED).list();
		// System.out.println(taska);*/
		}
	
	//retourner tous les taches 
	/*public List<Task> getWorkflows(){
	      //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Task> taskb = taskService.createTaskQuery().taskCandidateUser("fbm").list();
		System.out.println(taskb);
		taskService.saveTask(tasks.get(0));
		taskService.saveTask(taskyfz.get(0));
		return taskb;
		
	}*/
	
}

