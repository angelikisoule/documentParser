package gr.documentParser.dao.hibernate;

import java.util.List;

import gr.documentParser.dao.StatsDao;
import gr.documentParser.model.Person;
import gr.documentParser.model.Stats;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateStatsDao  extends AbstractHibernateDao<Stats> implements StatsDao{
	
	@Override
	public void persistStats(Stats stats) {
		persist(stats);
	}

	@Override
	public Stats getByFilename(String filename) {
		Query query = getSession().createQuery("SELECT stats FROM Stats stats WHERE filename=:filename");
		query.setParameter("filename", filename);
		return (Stats) query.uniqueResult();
	}
	
	@Override
	public List<Stats> getAllByType(String type){
		Query query = getSession().createQuery("SELECT stats FROM Stats stats WHERE type=:type");
		query.setParameter("type", type);
		return (List<Stats>) query.list();
	}

	@Override
	public void deleteAllByType(String type) {
		List<Stats> allStats = getAllByType(type);
		for (Stats stats : allStats) {
			delete(stats);
		}
	}

	@Override
	public Long countByType(String type) {
		Query query = getSession().createQuery("SELECT COUNT(stats) FROM Stats stats WHERE type=:type");
		query.setParameter("type", type);
		return (Long) query.uniqueResult();
	}

	@Override
	public List<Stats> getAllStats(int maxStats, int offset) {
		Query query = getSession().createQuery("FROM Stats stats");
		query.setFirstResult(offset);
		query.setMaxResults(maxStats);
		return (List<Stats>) query.list();
	}

}
