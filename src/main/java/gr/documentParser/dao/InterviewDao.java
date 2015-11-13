package gr.documentParser.dao;

import gr.documentParser.model.Interview;

public interface InterviewDao extends AbstractDao<Interview> {

	void persistInterview(Interview interview);
}
