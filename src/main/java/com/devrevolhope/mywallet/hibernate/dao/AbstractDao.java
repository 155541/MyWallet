package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<ID extends Serializable, T> {
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	
    public T findById(ID id) {
        return (T) getSession().get(persistentClass, id);
    }
    public void persist(T entity) {
        getSession().persist(entity);
    }
 
    public void update(T entity) {
        getSession().update(entity);
    }
 
    public void delete(T entity) {
    	if (entity != null)
    		getSession().delete(entity);
    }
     
    protected CriteriaQuery<T> createCriteria() {
    	return getSession().getCriteriaBuilder().createQuery(persistentClass);
    }
}
