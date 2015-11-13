package gr.documentParser.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.documentParser.dao.QuestionDao;
import gr.documentParser.model.Question;

@Repository
public class HibernateQuestionDao extends AbstractHibernateDao<Question> implements QuestionDao {

}
