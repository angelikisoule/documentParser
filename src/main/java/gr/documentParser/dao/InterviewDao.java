package gr.documentParser.dao;

import java.util.List;
import java.util.Set;

import gr.documentParser.model.Interview;

public interface InterviewDao extends AbstractDao<Interview> {

	void persistInterviews(Set<Interview> interviews);
	
	Interview getByInterviewId(Long interviewId);
	
	Interview getByAddressId(Long addressId); 
	
	List<Interview> getByFilename(String filename);
	
	Long countByAddressId(Long addressId);
}
