package gr.documentParser.service;

import java.util.List;

import gr.documentParser.model.Question;

public interface QuestionService {

	Question questionExists(String questionCode, String questionText);
	
	List<Question>getAll();
	
	Long countAll();
}
