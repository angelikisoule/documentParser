package gr.documentParser.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.documentParser.dao.QuestionDao;
import gr.documentParser.model.Question;

@Repository
public class HibernateQuestionDao extends AbstractHibernateDao<Question> implements QuestionDao {

	@Override
	public Question getByQuestionCode(String questionCode) {
		Query query = getSession().createQuery("FROM Question questions WHERE LOWER(questionCode)=:questionCode");
		query.setParameter("questionCode", questionCode.toLowerCase());
		return (Question) query.uniqueResult();
	}
	
	@Override
    public Question questionExists(String questionCode, String questionText) {
    	Question question = getByQuestionCode(questionCode);
    	if(question==null) {
    		question = new Question();
    		question.setQuestionCode(questionCode);
    		question.setQuestionText(questionText);
    		persist(question);
    	}
    	return question;
    }
}
