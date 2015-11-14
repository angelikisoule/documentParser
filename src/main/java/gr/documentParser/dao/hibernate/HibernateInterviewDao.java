package gr.documentParser.dao.hibernate;

import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.documentParser.dao.InterviewDao;
import gr.documentParser.model.Interview;

@Repository
public class HibernateInterviewDao extends AbstractHibernateDao<Interview> implements InterviewDao {

	@Override
	public Interview getByInterviewId(Long interviewId) {
		Query query = getSession().createQuery("FROM Interview interviews WHERE interviewId=:interviewId");
		query.setParameter("interviewId", interviewId);
		return (Interview) query.uniqueResult();
	}

	@Override
	public void persistInterviews(Set<Interview> interviews) {
		for(Interview interview : interviews) {
			interviewExists(interview); //Persist
		}
	}

	/*
	 * An Interview May By False Be Contained In More Than One Files Or We May Read The Same File More Than Once
	 */
	protected void interviewExists(Interview interview) {
		Interview existing = getByInterviewId(interview.getInterviewId());
		if(existing!=null) {
			return; //TODO It Would Be Better To Merge With The New Data
		}
		else {
			persist(interview);
		}
	}
}
