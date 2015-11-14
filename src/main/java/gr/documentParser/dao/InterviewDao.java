package gr.documentParser.dao;

import java.util.Set;

import gr.documentParser.model.Interview;

public interface InterviewDao extends AbstractDao<Interview> {

	Interview getByInterviewId(Long interviewId);
	
	void persistInterviews(Set<Interview> interviews);
}
