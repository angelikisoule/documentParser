package gr.documentParser.dao;

import java.util.Set;

import gr.documentParser.model.Person;

public interface PersonDao extends AbstractDao<Person>{

	Person getByAddressId(Long addressId);
	
	void persistPerson(Person person);
	
	Long countAddressId(Long addressId);
}
