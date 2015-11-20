package gr.documentParser.dao;

import gr.documentParser.model.Stats;

public interface StatsDao  extends AbstractDao<Stats> {

	void persistStats(Stats stats);
	
}
