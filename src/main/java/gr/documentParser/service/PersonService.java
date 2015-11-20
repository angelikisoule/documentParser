package gr.documentParser.service;

import gr.documentParser.model.Person;

public interface PersonService {

	void persistPerson(Person person);
	
	Person getByAddressId(Long addressId);
	
	Long countAddressId(Long addressId);
}
