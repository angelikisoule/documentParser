package gr.documentParser.dao;

import java.util.List;
import java.util.Set;

import gr.documentParser.model.Interview;

public interface InterviewDao extends AbstractDao<Interview> {

	void persistInterview(Interview interview);
	
	void persistInterviews(Set<Interview> interviews);

	List<Interview> getAll(int maxInterviews);
	
	List<Interview> getAll(int maxInterviews, int offset);

	Interview getInterview(Long id);
	
	Interview getByInterviewId(Long interviewId);
	
	Interview getByAddressId(Long addressId); 
	
	List<Interview> getByFilename(String filename);
	
	Long countByAddressId(Long addressId);
}
