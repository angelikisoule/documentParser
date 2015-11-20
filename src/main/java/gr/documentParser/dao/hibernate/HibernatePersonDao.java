package gr.documentParser.dao.hibernate;

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
	public void persistPerson(Person person) {
		persist(person);
		
	}

	@Override
	public Long countAddressId(Long addressId) {
		Query query = getSession().createQuery("SELECT COUNT(*) FROM Person persons WHERE addressId=:addressId");
		query.setParameter("addressId", addressId);
		return (Long) query.uniqueResult();
	}
	
}
