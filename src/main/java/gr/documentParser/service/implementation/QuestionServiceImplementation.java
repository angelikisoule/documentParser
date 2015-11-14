package gr.documentParser.service.implementation;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import gr.documentParser.dao.QuestionDao;
import gr.documentParser.model.Question;
import gr.documentParser.service.QuestionService;

@Service
@Transactional
public class QuestionServiceImplementation implements QuestionService {

	@Inject private QuestionDao questionDao;
	
	@Override
	public Question questionExists(String questionCode, String questionText) {
		return questionDao.questionExists(questionCode, questionText);
	}
}
