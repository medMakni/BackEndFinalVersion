package biz.picosoft.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import biz.picosoft.dao.Soci�t�Dao;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Soci�t�;
@Component
public class Soci�t�DaoImpl extends GenericDaoImp<Soci�t�> implements Soci�t�Dao {
	public List<Contacte> findAllContacts(Soci�t� soci�t�){
		return soci�t�.getContacts();
	}
	
public Soci�t� getSoci�t�FromNom(String nomSoci�t�){
 
	CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
	CriteriaQuery<Soci�t�> cq = cb.createQuery(Soci�t�.class);
	Root<Soci�t�> s = cq.from(Soci�t�.class);
	 cq.where(cb.equal(s.get("nom"), nomSoci�t�));
	 TypedQuery<Soci�t�> q = this.getEm().createQuery(cq);
	 List<Soci�t�> results = q.getResultList();
	return results.get(0);
		  
}
}
