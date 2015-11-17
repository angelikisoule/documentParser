package gr.documentParser.service.implementation;

import java.util.Set;

import gr.documentParser.dao.PersonDao;
import gr.documentParser.model.Person;
import gr.documentParser.service.PersonService;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonServiceImplementation implements PersonService{

	@Inject private PersonDao personDao;
	
	@Override
	public Person getByInterviewId(String interviewId) {
		return personDao.getByInterviewId(interviewId);
	}

	@Override
	public void persistPerson(Person person) {
		personDao.persistPerson(person);
		
	}
}
