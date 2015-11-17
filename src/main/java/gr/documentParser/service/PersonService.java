package gr.documentParser.service;

import gr.documentParser.model.Person;

import java.util.Set;

public interface PersonService {

	void persistPerson(Person person);
	
	Person getByInterviewId(String interviewId);
}
