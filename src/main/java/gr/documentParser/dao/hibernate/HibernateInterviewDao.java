package gr.documentParser.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.documentParser.dao.InterviewDao;
import gr.documentParser.model.Interview;

@Repository
public class HibernateInterviewDao extends AbstractHibernateDao<Interview> implements InterviewDao {

	@Override
	public List<Interview> getAll(int maxInterviews) {
		return getAll(maxInterviews, 0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Interview> getAll(int maxInterviews, int offset) {
		Query query = getSession().createQuery("FROM Interview interviews");
		query.setFirstResult(offset);
		query.setMaxResults(maxInterviews);
		return (List<Interview>) query.list();
	}
	
	@Override
	public Interview getInterview(Long id) {
		return get(id);
	}
	
	@Override
	public Interview getByInterviewId(Long interviewId) {
		Query query = getSession().createQuery("SELECT interview FROM Interview interview WHERE interviewId=:interviewId");
		query.setParameter("interviewId", interviewId);
		return (Interview) query.uniqueResult();
	}

	@Override
	public void persistInterview(Interview interview) {
		interviewExists(interview); //Persist
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
		Interview existing = getByAddressId(interview.getAddressId());
		if(existing!=null) {
			System.out.println("Interview with Id "+existing.getId()+" and interviewId "+interview.getInterviewId()+" and addressId "+interview.getAddressId()+" is already exists.");
			return; //TODO It Would Be Better To Merge With The New Data
		}
		else {
			persist(interview);
		}
	}

	@Override
	public Interview getByAddressId(Long addressId) {
		Query query = getSession().createQuery("SELECT interviews FROM Interview interviews WHERE addressId=:addressId");
		query.setParameter("addressId", addressId);
		return (Interview) query.uniqueResult();
	}

	@Override
	public Long countByAddressId(Long addressId) {
		Query query = getSession().createQuery("SELECT COUNT(*) FROM Interview interviews WHERE addressId=:addressId");
		query.setParameter("addressId", addressId);
		return (Long) query.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Interview> getByFilename(String filename) {
		Query query = getSession().createQuery("SELECT interviews FROM Interview interviews WHERE filename=:filename");
		query.setParameter("filename", filename);
		return (List<Interview>) query.list();
	}

	@Override
	public void deleteByFilename(String filename) {
		List<Interview> interviews = getByFilename(filename);
		for (Interview interview : interviews) {
			delete(interview);
		}
	}

	@Override
	public void mergeInterviews(List<Interview> interviews) {
		for (Interview interview : interviews) {
			merge(interview);
		}
	}

	@Override
	public List<Interview> getNullPhones() {
		Query query = getSession().createQuery("SELECT interviews FROM Interview interviews WHERE phone1=null OR phone2=null");
		return (List<Interview>) query.list();
	}
	
}
