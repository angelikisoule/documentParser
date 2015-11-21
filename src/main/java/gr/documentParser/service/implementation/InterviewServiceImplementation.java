package gr.documentParser.service.implementation;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import gr.documentParser.dao.InterviewDao;
import gr.documentParser.model.Interview;
import gr.documentParser.service.InterviewService;

@Service
@Transactional
public class InterviewServiceImplementation implements InterviewService {

	@Inject private InterviewDao interviewDao;
	
	@Override
	public void persistInterview(Interview interview) {
		interviewDao.persistInterview(interview);
	}
	
	@Override
	public void persistInterviews(Set<Interview> interviews) {
		interviewDao.persistInterviews(interviews);
	}

	@Override
	public List<Interview> getInterviews(int maxInterviews, int offset) {
		return interviewDao.getAll(maxInterviews, offset);
	}
	
	@Override
	public Interview getInterview(Long id) {
		return interviewDao.getInterview(id);
	}
	
	@Override
	public Interview getByInterviewId(Long interviewId) {
		return interviewDao.getByInterviewId(interviewId);
	}

	@Override
	public Interview getByAddressId(Long addressId) {
		return interviewDao.getByAddressId(addressId);
	}

	@Override
	public Long countInterviews() {
		return interviewDao.count();
	}
	
	@Override
	public Long countByAddressId(Long addressId) {
		return interviewDao.countByAddressId(addressId);
	}

	@Override
	public List<Interview> getByFilename(String filename) {
		return interviewDao.getByFilename(filename);
	}
}