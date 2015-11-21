package gr.documentParser.service;

import java.util.List;
import java.util.Set;

import gr.documentParser.model.Interview;

public interface InterviewService {

	void persistInterview(Interview interview);
	
	void persistInterviews(Set<Interview> interviews);
	
	List<Interview> getInterviews(int maxInterviews, int offset);
	
	Interview getInterview(Long id);
	
	Interview getByInterviewId(Long interviewId);
	
	Interview getByAddressId(Long addressId);
	
	Long countInterviews();
	
	Long countByAddressId(Long addressId);
	
	List<Interview> getByFilename(String filename);
}
