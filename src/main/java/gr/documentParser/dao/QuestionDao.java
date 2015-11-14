package gr.documentParser.dao;

import gr.documentParser.model.Question;

public interface QuestionDao extends AbstractDao<Question> {

	Question getByQuestionCode(String questionCode);
	
	Question questionExists(String questionCode, String questionText);
}
