package gr.documentParser.service;

import java.util.List;

import gr.documentParser.model.Person;

public interface PersonService {

	void persistPersons(List<Person> persons);
	
	Person getByAddressId(Long addressId);
	
	Long countAddressId(Long addressId);
	
	void deleteAll();
	
	List<Person> getByFilename(String filename);
	
	void deleteByFilename(String filename);
	
	Long countPersons();
	
	Person getPerson(Long id);
	
	List<Person> getPersons(int maxPersons, int offset);
}
