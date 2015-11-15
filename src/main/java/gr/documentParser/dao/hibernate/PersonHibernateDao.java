package gr.documentParser.dao.hibernate;

import gr.documentParser.dao.PersonDao;
import gr.documentParser.model.Person;

import org.springframework.stereotype.Repository;

@Repository
public class PersonHibernateDao extends AbstractHibernateDao<Person> implements PersonDao{

}
