package gr.documentParser.service.implementation;

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
		interviewDao.persist(interview);
	}
}
