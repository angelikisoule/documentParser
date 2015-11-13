package gr.documentParser.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.documentParser.dao.AnswerDao;
import gr.documentParser.model.Answer;

@Repository
public class HibernateAnswerDao extends AbstractHibernateDao<Answer> implements AnswerDao {

}
