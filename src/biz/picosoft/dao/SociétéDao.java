package biz.picosoft.dao;

import java.util.List;

import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;

public interface SociétéDao extends GenericDao<Société> {
	List<Contacte> findAllContacts(Société société);
	Société getSociétéFromNom(String nomSociété);
}
