package biz.picosoft.dao;

import java.util.List;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;

public interface Soci�t�Dao extends GenericDao<Soci�t�> {
	List<Contacte> findAllContacts(Soci�t� soci�t�);
	Soci�t� getSoci�t�FromNom(String nomSoci�t�);
}
