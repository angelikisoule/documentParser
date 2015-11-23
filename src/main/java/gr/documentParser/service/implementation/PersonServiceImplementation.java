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

	@Override
	public List<Person> getByFilename(String filename) {
		return personDao.getByFilename(filename);
	}

	@Override
	public void deleteByFilename(String filename) {
		personDao.deleteByFilename(filename);
	}

	@Override
	public Long countPersons() {
		return personDao.countPersons();
	}

	@Override
	public Person getPerson(Long id) {
		return personDao.getPerson(id);
	}

	@Override
	public List<Person> getPersons(int maxPersons, int offset) {
		return personDao.getPersons(maxPersons, offset);
	}
}
