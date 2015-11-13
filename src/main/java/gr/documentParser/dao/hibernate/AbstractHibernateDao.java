package gr.documentParser.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.util.ReflectionUtils;

import gr.documentParser.dao.AbstractDao;

/**
 * Generic Class's DAO Implementation
 * @param <T> Generic Class
 */
public abstract class AbstractHibernateDao<T extends Object> implements AbstractDao<T> {

	private static final Logger logger = Logger.getLogger(AbstractHibernateDao.class);
	
	@Inject private SessionFactory sessionFactory;
	private Class<T> domainClass;
	
	/**
	 * Protected Method Allowing Subclasses To Perform Persistent Operations Against The Hibernate Session
	 * @return Hibernate Session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Reflection To Get Actual Domain Class
	 * @return Domain Class
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
		if(domainClass==null) {
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	/**
	 * Reflection To Get Actual Domain Class Name
	 * @return Domain Class Name
	 */
	private String getDomainClassName() {
		return getDomainClass().getName();
	}
	
	/**
	 * Persist Generic Class Object. The Method Sets dateUpdated Property If It Exists
	 * @param t Object To Be Persisted
	 */
	@Override
	public void persist(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(), "setDateUpdated", new Class[] { Calendar.class });
		if(method!=null) {
			try {
				method.invoke(t, Calendar.getInstance());
			}
			catch(Exception exception) {
				logger.error(exception);
			}
		}
		getSession().persist(t);
		getSession().flush(); //Flush To Get Id Of Object Persisted
	}
	
	/**
	 * Merge Generic Class Object. The Method Sets dateUpdated Property If It Exists
	 * @param t Object To Be Merged
	 */
	@Override
	public void merge(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(), "setDateUpdated", new Class[] { Calendar.class });
		if(method!=null) {
			try {
				method.invoke(t, Calendar.getInstance());
			}
			catch(Exception exception) {
				logger.error(exception);
			}
		}		
		
		getSession().merge(t);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(getDomainClass(), id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(String property, String order) {
		Criteria criteria = getSession().createCriteria(getDomainClass());
		if(order!=null && order.equals("DESC")) {
			criteria.addOrder(Order.desc(property).nulls(NullPrecedence.FIRST));
		}
		else if(property!=null) {
			criteria.addOrder(Order.asc(property).nulls(NullPrecedence.FIRST));
		}
		return (List<T>) criteria.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(String property, String order, int first, int size) {
		Criteria criteria = getSession().createCriteria(getDomainClass());
		if(order!=null && order.equals("DESC")) {
			criteria.addOrder(Order.desc(property).nulls(NullPrecedence.FIRST));
		}
		else if(property!=null) {
			criteria.addOrder(Order.asc(property).nulls(NullPrecedence.FIRST));
		}
		criteria.setFirstResult(first);
		criteria.setMaxResults(size);
		return (List<T>) criteria.list();
	}
	
	@Override
	public void delete(T t) {
		getSession().delete(t);
	}
	
	@Override
	public void deleteById(Serializable id) {
		delete(load(id));
	}
	
	@Override
	public Long count() {
		return (Long) getSession().createQuery("SELECT COUNT(*) FROM " + getDomainClassName()).uniqueResult();
	}
}