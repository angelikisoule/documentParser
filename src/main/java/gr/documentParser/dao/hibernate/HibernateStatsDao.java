package gr.documentParser.dao.hibernate;

import gr.documentParser.dao.StatsDao;
import gr.documentParser.model.Stats;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateStatsDao  extends AbstractHibernateDao<Stats> implements StatsDao{
	
	@Override
	public void persistStats(Stats stats) {
		persist(stats);
	}

}
