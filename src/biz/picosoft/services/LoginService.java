package biz.picosoft.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
@Service
public class LoginService {
	ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");

	ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");

	public List<String> findUserGroupsById(String uid) throws Exception {
		List<String> l = new ArrayList<String>();

		List<Group> j = processEngine.getIdentityService().createGroupQuery().groupMember(uid).list();
		for (Group group : j) {
			System.out.println(group.getName());
			l.add(group.getName());
		}
		return l;
	}
	public Map<String, Object> findUserDirectionAndRoleById(String uid) throws Exception {
		List<String> direction = new ArrayList<String>();
		List<String> role = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String,Object>();
		List<Group> j = processEngine.getIdentityService().createGroupQuery().groupMember(uid).list();
		for (Group group : j) {
			String groupName=group.getName();
			System.out.println("uiuiui"+groupName);
			if (groupName.contains("ROLE_")){
				role.add(groupName.substring(5, groupName.length()));
			}
			else if (groupName.contains("direction")) {
				direction.add(groupName);
			}
			
		}
		
		map.put("roles",role);
		map.put("directions",direction);
		System.out.println("fff"+map);
		return map;
	}

}
