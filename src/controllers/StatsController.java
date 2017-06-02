package controllers;

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
public int getNumberOfActiveCourrier() {
		
		return ss.getNumberOfActiveCourrier();
	}
	

	@RequestMapping(value = "/nbrefini", method = RequestMethod.GET)
	@ResponseBody
public int getNumberOfFinishedCourrier() {
		
		return ss.getNumberOfFinishedCourrier();
	}
	
}
