package biz.picosoft.controllers;

import java.util.ArrayList;
import java.util.List;

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
	System.out.println("nnnn"+uid.getClass());
		List<String> l= new ArrayList<>(); 
		try {
			l=ls.findUserById((String)uid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("work");
		}
				return l;
	}
}
