package gr.documentParser.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.documentParser.dao.AnswerDao;
import gr.documentParser.model.Answer;

@Repository
public class HibernateAnswerDao extends AbstractHibernateDao<Answer> implements AnswerDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Answer> getRanking() {
		Query query = getSession().createQuery("SELECT a FROM Answer a " +
				"JOIN a.question q " +
				"WHERE a.question=q.id " +
				"AND q.questionCode = 'Q60'");
		query.setMaxResults(10);
		return (List<Answer>) query.list();
	}

}
