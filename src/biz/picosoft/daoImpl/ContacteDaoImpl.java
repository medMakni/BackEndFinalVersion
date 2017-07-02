package biz.picosoft.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import biz.picosoft.dao.ContacteDao;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;
 
@Component
public class ContacteDaoImpl extends GenericDaoImp<Contacte> implements ContacteDao {

	@Override
	public Contacte getContactFromNom(String nom) {

		CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
		CriteriaQuery<Contacte> cq = cb.createQuery(Contacte.class);
		Root<Société> s = cq.from(Société.class);
		 cq.where(cb.equal(s.get("nom"), nom));
		 TypedQuery<Contacte> q = this.getEm().createQuery(cq);
		 List<Contacte> results = q.getResultList();
		return results.get(0);
	}

}
