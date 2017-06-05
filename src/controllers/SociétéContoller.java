package controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.picosoft.entity.Contacte;
import biz.picosoft.services.SociétéService;
import biz.picosoft.services.SociétéServiceImpl;
@Controller
public class SociétéContoller {
	SociétéService sociétéService ;
	@RequestMapping(value = "/insertCompany", method = RequestMethod.POST)
	@ResponseBody
	void insert(  String nom, String email, String télèphone, String adress) {
		sociétéService.insert(nom, email, télèphone, adress);
	}
	@RequestMapping(value = "/updateCompany", method = RequestMethod.GET)
	@ResponseBody
	void update(int id,String nom, String email, String télèphone, String adress) {
		sociétéService.update(id, nom, email, télèphone, adress);
	}
	@RequestMapping(value = "/deleteCompany", method = RequestMethod.POST)
	@ResponseBody
	void delete(@RequestBody Map<String, Object> data) {
		sociétéService.delete((int)data.get("idSociété"));
	}
	@RequestMapping(value = "/findCompanyById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(int id) {
		return sociétéService.findById(id);
	}
	@RequestMapping(value = "/findAllCompanies", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findAll() {
		
		System.out.println("kkkki"+sociétéService.findAll());
		return sociétéService.findAll();
	}
	@RequestMapping(value = "/findCompanyByContact", method = RequestMethod.GET)
	@ResponseBody
	public List<Contacte> findAllContacts(int idSociété) {
		return sociétéService.findAllContacts(idSociété);
	}

	public SociétéContoller() {
		super();
		this.sociétéService=new SociétéServiceImpl();
	}
}
