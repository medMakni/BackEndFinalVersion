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
import biz.picosoft.entity.Société;
import biz.picosoft.services.ContactService;
import biz.picosoft.services.ContacteServiceImpl;
@Controller
public class ContactsController {
	ContactService contacteServiceImpl;
	@RequestMapping(value = "/insertContact", method = RequestMethod.POST)
	@ResponseBody
	void insert(@RequestBody Map<String, Object> data) {
		System.out.println(data.get("nom"));
		contacteServiceImpl.insert((String)data.get("nom"),(String)data.get("email"), (String)data.get("telephone"), (String)data.get("adresse"), (int)data.get("idSociété"));
	}
	@RequestMapping(value = "/updateContact", method = RequestMethod.GET)
	@ResponseBody
	void update(int id, String nom, String mail, String téléphone, String adresse, int idSociété) {
		contacteServiceImpl.update(id, nom, mail, téléphone, adresse, idSociété);
	}
	@RequestMapping(value = "/deleteContact", method = RequestMethod.GET)
	@ResponseBody
	void delete(int id) {
		contacteServiceImpl.delete(id);
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
