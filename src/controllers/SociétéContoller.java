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
	void insert(  @RequestBody Map<String, Object> data) {
		System.out.println("trtr"+data);

		soci�t�Service.insert( (String)data.get("nom"), (String)data.get("email"), (String)data.get("telephone"), (String)data.get("adresse"));
	}
	@RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
	@ResponseBody
	void update(@RequestBody Map<String, Object> data) {
		System.out.println(data);
		soci�t�Service.update((int)data.get("idCompany"), (String)data.get("nomCompany"), (String)data.get("emailCompany"), (String)data.get("telephoneCompany"), (String)data.get("adresseCompany"));
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
	public List<String> findAllContacts(int idSoci�t�) {
		return soci�t�Service.findAllContacts(idSoci�t�);
	}

	public Soci�t�Contoller() {
		super();
		this.soci�t�Service=new Soci�t�ServiceImpl();
	}
}
