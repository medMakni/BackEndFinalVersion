package controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return ss.getNbrFinishedCourrierArrivéPerDirection();
		
	}
	@RequestMapping(value = "/nbreActiveParDir", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Integer> getNbrActiveCourrierArrivéPerDirection() {
		return ss.getNbrActiveCourrierArrivéPerDirection();
		
	}
	
}
