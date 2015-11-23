package gr.documentParser.service;

import java.util.List;

import gr.documentParser.model.Person;

public interface PersonService {

	void persistPersons(List<Person> persons);
	
	Person getByAddressId(Long addressId);
	
	Long countAddressId(Long addressId);
	
	void deleteAll();
}
