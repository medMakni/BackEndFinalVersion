package biz.picosoft.services;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoginService {
	ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");

	ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");

	public List<String> findUserById(String uid) throws Exception {
		List<String> l = new ArrayList<String>();

		List<Group> j = processEngine.getIdentityService().createGroupQuery().groupMember(uid).list();
		for (Group user : j) {
			System.out.println(user.getName());
			l.add(user.getName());
		}
		return l;
	}

}
