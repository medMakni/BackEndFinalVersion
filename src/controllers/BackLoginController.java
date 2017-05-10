package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.picosoft.services.LoginService;

@Controller
public class BackLoginController {
	LoginService ls = new LoginService();
	@RequestMapping(value = "/getUserGroup", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<String> getGroups(@RequestParam("uid") Object uid) {
	System.out.println(uid.getClass());
		List<String> l= new ArrayList<>(); 
		try {
			l=ls.findUserGroupsById((String)uid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("work");
		}
				return l;
	}
	
	
	@RequestMapping(value = "/getUserRole", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> getUserRole(@RequestParam("uid") Object uid) {
	System.out.println(uid.getClass());
	Map<String, Object> groups= new HashMap<String, Object>(); 
		try {
			groups=ls.findUserDirectionAndRoleById((String)uid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("work");
		}
				return groups;
	}
}
