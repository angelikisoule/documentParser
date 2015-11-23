package gr.documentParser.dao;

import java.util.List;

import gr.documentParser.model.Person;

public interface PersonDao extends AbstractDao<Person>{

	Person getByAddressId(Long addressId);
	
	void persistPersons(List<Person> person);
	
	Long countAddressId(Long addressId);
	
	void deleteAll();
	
	List<Person> getByFilename(String filename);
	
	void deleteByFilename(String filename);
	
	Long countPersons();
	
	Person getPerson(Long id);
	
	List<Person> getPersons(int maxPersons, int offset);
	
}