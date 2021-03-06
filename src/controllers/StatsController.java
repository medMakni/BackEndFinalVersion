package controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.picosoft.services.LdapService;
import biz.picosoft.services.LdapServiceImpl;
import biz.picosoft.services.StatisticServiceImpl;
import biz.picosoft.services.StatisticsService;
@Controller
public class StatsController {
	StatisticsService ss=new StatisticServiceImpl();
	@RequestMapping(value = "/nbreactif", method = RequestMethod.GET)
	@ResponseBody
Map<String,Integer> getNumberOfActiveCourrier() {
		
		return ss.getNumberOfActiveCourrier();
	}
	

	@RequestMapping(value = "/nbrefini", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Integer> getNumberOfFinishedCourrier() {
		
		return ss.getNumberOfFinishedCourrier();
	}
	
	@RequestMapping(value = "/nbreFiniParDir", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Integer> getNbrFinishedCourrierArrivéPerDirection() {
		System.out.println("eee"+ss.getNbrFinishedCourrierArrivéPerDirection());
		return ss.getNbrFinishedCourrierArrivéPerDirection();
		
	}
	@RequestMapping(value = "/nbreActiveParDir", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Integer> getNbrActiveCourrierArrivéPerDirection() {
		System.out.println("zzz"+ss.getNbrActiveCourrierArrivéPerDirection());
		return ss.getNbrActiveCourrierArrivéPerDirection();
		
	}


}
