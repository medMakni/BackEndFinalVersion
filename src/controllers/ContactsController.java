package controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;
import biz.picosoft.services.ContactService;
import biz.picosoft.services.ContacteServiceImpl;
@Controller
public class ContactsController {
	ContactService contacteServiceImpl;
	@RequestMapping(value = "/insertContact", method = RequestMethod.POST)
	@ResponseBody
	void insert(@RequestBody Map<String, Object> data) {
		System.out.println(data.get("nom"));
		contacteServiceImpl.insert((String)data.get("nom"),(String)data.get("email"), (String)data.get("telephone"), (String)data.get("adresse"), (int)data.get("idSoci�t�"));
	}
	@RequestMapping(value = "/updateContact", method = RequestMethod.GET)
	@ResponseBody
	void update(int id, String nom, String mail, String t�l�phone, String adresse, Soci�t� soci�t�) {
		contacteServiceImpl.update(id, nom, mail, t�l�phone, adresse, soci�t�);
	}
	@RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
	@ResponseBody
	void delete(@RequestBody Map<String, Object> data) {
		System.out.println("zzz"+(int)data.get("idContact"));
		contacteServiceImpl.delete((int)data.get("idContact"));
	}
	@RequestMapping(value = "/findContactById", method = RequestMethod.GET)
	@ResponseBody
	public Contacte findById(int id) {
		return contacteServiceImpl.findById(id);
	}
	@RequestMapping(value = "/findAllContacts", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findAll() {
		return contacteServiceImpl.findAll();
	}

	public ContactsController() {
		super();
		this.contacteServiceImpl = new ContacteServiceImpl();
	}

}
