package gr.documentParser.service;

import gr.documentParser.model.Question;

public interface QuestionService {

	Question questionExists(String questionCode, String questionText);
}
