package gr.documentParser.dao.hibernate;

import java.util.List;

import gr.documentParser.dao.PersonDao;
import gr.documentParser.model.Person;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HibernatePersonDao extends AbstractHibernateDao<Person> implements PersonDao{
	@Override
	public Person getByAddressId(Long addressId) {
		Query query = getSession().createQuery("FROM Person persons WHERE addressId=:addressId");
		query.setParameter("addressId", addressId);
		return (Person) query.uniqueResult();
	}

	@Override
	public void persistPersons(List<Person> persons) {
		for (Person person : persons) {
			persist(person);
		}
	}

	@Override
	public Long countAddressId(Long addressId) {
		Query query = getSession().createQuery("SELECT COUNT(*) FROM Person persons WHERE addressId=:addressId");
		query.setParameter("addressId", addressId);
		return (Long) query.uniqueResult();
	}

	@Override
	public void deleteAll() {
		List<Person> persons =  getAll(null,null);
		for (Person person : persons) {
			delete(person);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getByFilename(String filename) {
		Query query = getSession().createQuery("SELECT persons FROM Person persons WHERE filename=:filename");
		query.setParameter("filename", filename);
		return (List<Person>) query.list();
	}

	@Override
	public void deleteByFilename(String filename) {
		List<Person> persons = getByFilename(filename);
		for (Person person : persons) {
			delete(person);
		}
	}

	@Override
	public Long countPersons() {
		Query query = getSession().createQuery("SELECT COUNT(*) FROM Person persons");
		return (Long) query.uniqueResult();
	}

	@Override
	public Person getPerson(Long id) {
		return get(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getPersons(int maxPersons, int offset) {
		Query query = getSession().createQuery("FROM Person persons");
		query.setFirstResult(offset);
		query.setMaxResults(maxPersons);
		return (List<Person>) query.list();
	}
}
