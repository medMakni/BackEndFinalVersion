package biz.picosoft.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T> {
	void insert(T t);

	void update(T t);

	void delete(T t);

	public T findById(Class<T> clazz, String id);

	public List<T> findAll();
}
