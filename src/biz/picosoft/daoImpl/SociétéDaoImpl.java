package biz.picosoft.daoImpl;

import java.util.List;

import org.springframework.stereotype.Component;

import biz.picosoft.dao.Soci�t�Dao;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;
@Component
public class Soci�t�DaoImpl extends GenericDaoImp<Soci�t�> implements Soci�t�Dao {
	public List<Contacte> findAllContacts(Soci�t� soci�t�){
		return soci�t�.getContacts();
	}
}
