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
	
	
	
}
