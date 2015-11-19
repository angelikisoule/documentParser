package gr.documentParser.service;

import java.util.List;
import java.util.Set;

import gr.documentParser.model.Interview;

public interface InterviewService {

	void persistInterviews(Set<Interview> interviews);
	
	Interview getByInterviewId(Long interviewId);
	
	Interview getByAddressId(Long addressId);
	
	Long countByAddressId(Long addressId);
	
	List<Interview> getByFilename(String filename);
}
