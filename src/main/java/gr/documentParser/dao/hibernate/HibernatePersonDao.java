package gr.documentParser.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.documentParser.dao.PersonDao;
import gr.documentParser.model.Person;

@Repository
public class HibernatePersonDao extends AbstractHibernateDao<Person> implements PersonDao {

}
