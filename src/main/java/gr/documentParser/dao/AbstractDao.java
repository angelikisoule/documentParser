package gr.documentParser.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Class's DAO Interface
 * @param <T> Generic Class
 */
public interface AbstractDao<T extends Object> {

	/**
	 * Persist Generic Class Object
	 * @param t Object To Be Persisted
	 */
	void persist(T t);
	
	/**
	 * Merge Generic Class Object
	 * @param t Object To Be Merged
	 */
	void merge(T t);

	/**
	 * Get Generic Class Object
	 * @param id Object's Identifier
	 * @return Object or null If It's Not Found
	 */
	T get(Serializable id);
	
	/**
	 * Load Generic Class Object
	 * @param id Object's Identifier
	 * @return Object
	 */
	T load(Serializable id);

	/**
	 * Get All Generic Class Objects
	 * @param property Property To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Objects
	 */
	List<T> getAll(String property, String order);
	
	/**
	 * Get All Generic Class Objects With Pagination
	 * @param property Property To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return List Of Objects
	 */
	List<T> getAll(String property, String order, int first, int size);
		
	/**
	 * Delete Generic Class Object
	 * @param t Object To Be Deleted
	 */
	void delete(T t);
	
	/**
	 * Delete Generic Class Objects Given It's Id
	 * @param id Object's Id
	 */
	void deleteById(Serializable id);

	/**
	 * Count All Generic Class Objects
	 * @return Count Of Objects
	 */
	Long count();
}