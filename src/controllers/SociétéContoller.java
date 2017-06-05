package controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.picosoft.entity.Contacte;
import biz.picosoft.services.Soci�t�Service;
import biz.picosoft.services.Soci�t�ServiceImpl;
@Controller
public class Soci�t�Contoller {
	Soci�t�Service soci�t�Service ;
	@RequestMapping(value = "/insertCompany", method = RequestMethod.POST)
	@ResponseBody
	void insert(  String nom, String email, String t�l�phone, String adress) {
		soci�t�Service.insert(nom, email, t�l�phone, adress);
	}
	@RequestMapping(value = "/updateCompany", method = RequestMethod.GET)
	@ResponseBody
	void update(int id,String nom, String email, String t�l�phone, String adress) {
		soci�t�Service.update(id, nom, email, t�l�phone, adress);
	}
	@RequestMapping(value = "/deleteCompany", method = RequestMethod.POST)
	@ResponseBody
	void delete(@RequestBody Map<String, Object> data) {
		soci�t�Service.delete((int)data.get("idSoci�t�"));
	}
	@RequestMapping(value = "/findCompanyById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(int id) {
		return soci�t�Service.findById(id);
	}
	@RequestMapping(value = "/findAllCompanies", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findAll() {
		
		System.out.println("kkkki"+soci�t�Service.findAll());
		return soci�t�Service.findAll();
	}
	@RequestMapping(value = "/findCompanyByContact", method = RequestMethod.GET)
	@ResponseBody
	public List<Contacte> findAllContacts(int idSoci�t�) {
		return soci�t�Service.findAllContacts(idSoci�t�);
	}

	public Soci�t�Contoller() {
		super();
		this.soci�t�Service=new Soci�t�ServiceImpl();
	}
}
