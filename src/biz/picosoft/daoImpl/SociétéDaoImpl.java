package biz.picosoft.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import biz.picosoft.dao.SociétéDao;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;
@Component
public class SociétéDaoImpl extends GenericDaoImp<Société> implements SociétéDao {
	public List<String> findAllContacts(Société société){
		List<String> contactsNameList=new ArrayList<String>();
		for(int i=0;i<société.getContacts().size();i++){
			contactsNameList.add(société.getContacts().get(i).getNom());
		}
		return contactsNameList;
	}
	
public Société getSociétéFromNom(String nomSociété){
 
	CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
	CriteriaQuery<Société> cq = cb.createQuery(Société.class);
	Root<Société> s = cq.from(Société.class);
	 cq.where(cb.equal(s.get("nom"), nomSociété));
	 TypedQuery<Société> q = this.getEm().createQuery(cq);
	 List<Société> results = q.getResultList();
	return results.get(0);
		  
}
}
