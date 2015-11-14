package gr.documentParser.service;

import java.util.Set;

import gr.documentParser.model.Interview;

public interface InterviewService {

	void persistInterviews(Set<Interview> interviews);
}
