package gr.documentParser.service.implementation;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import gr.documentParser.dao.AnswerDao;
import gr.documentParser.model.Answer;
import gr.documentParser.service.AnswerService;

@Service
@Transactional
public class AnswerServiceImplementation implements AnswerService {
	
	@Inject private AnswerDao answerDao;
	
	@Override
	public List<Answer> getRanking(){
		return answerDao.getRanking();
	}

}
