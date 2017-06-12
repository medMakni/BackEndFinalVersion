package biz.picosoft.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import biz.picosoft.dao.GenericDao;

 

@Transactional(readOnly = false)
public class GenericDaoImp<T> implements GenericDao<T> {
	@PersistenceContext
	private EntityManager em;

	protected Class<T> daoType;

	public GenericDaoImp() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		daoType = (Class) pt.getActualTypeArguments()[0];
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Class<T> getDaoType() {
		return daoType;
	}

	public void setDaoType(Class<T> daoType) {
		this.daoType = daoType;
	}

	public void insert(T t) {
		// TODO Auto-generated method stub
		em.persist(t);

	}

	public void update(T t) {
		// TODO Auto-generated method stub
		em.merge(t);
	}

	public void delete(T t) {
		// TODO Auto-generated method stub
		Object managed = em.merge(t);
		em.remove(managed);
		
		 
	 
	}

	public T findById(Class<T> t, int id) {
		// TODO Auto-generated method stub
		return em.find(daoType, id);
	}

	public List<T> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT e FROM " + daoType.getName() + " e");
		return (List<T>) query.getResultList();
	}

}