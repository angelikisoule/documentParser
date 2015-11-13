package gr.documentParser.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.documentParser.dao.InterviewDao;
import gr.documentParser.model.Interview;

@Repository
public class HibernateInterviewDao extends AbstractHibernateDao<Interview> implements InterviewDao {

	@Override
	public void persistInterview(Interview interview) {
		Interview exists = get(interview.getId());
		if(exists==null) {
			persist(interview);
		}
		else {
			//TODO Nothing For Now
		}
	}
}
