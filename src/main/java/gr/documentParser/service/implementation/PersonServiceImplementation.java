package gr.documentParser.service.implementation;

import java.util.List;

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
	public Person getByAddressId(Long interviewId) {
		return personDao.getByAddressId(interviewId);
	}

	@Override
	public void persistPersons(List<Person> persons) {
		personDao.persistPersons(persons);
		
	}

	@Override
	public Long countAddressId(Long addressId) {
		return personDao.countAddressId(addressId);
	}

	@Override
	public void deleteAll() {
		personDao.deleteAll();
	}
}
