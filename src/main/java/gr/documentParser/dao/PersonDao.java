package gr.documentParser.dao;

import java.util.Set;

import gr.documentParser.model.Person;

public interface PersonDao extends AbstractDao<Person>{

	Person getByInterviewId(String interviewId);
	
	void persistPerson(Person person);
}
