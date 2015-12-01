package gr.documentParser.dao;

import java.util.List;

import gr.documentParser.model.Answer;

public interface AnswerDao extends AbstractDao<Answer> {
	
	List<Answer> getRanking();

}
