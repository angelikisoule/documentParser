package gr.documentParser.dao.hibernate;

import java.util.Set;

import gr.documentParser.dao.PersonDao;
import gr.documentParser.model.Person;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PersonHibernateDao extends AbstractHibernateDao<Person> implements PersonDao{
	@Override
	public Person getByInterviewId(String interviewId) {
		Query query = getSession().createQuery("FROM Person persons WHERE interviewId=:interviewId");
		query.setParameter("interviewId", interviewId);
		return (Person) query.uniqueResult();
	}

	@Override
	public void persistPerson(Person person) {
		persist(person);
		
	}
	
}
