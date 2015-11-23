package gr.documentParser.dao;

import java.util.List;

import gr.documentParser.model.Stats;

public interface StatsDao  extends AbstractDao<Stats> {

	void persistStats(Stats stats);
	
	Stats getByFilename(String filename);
	
	void deleteAllByType(String type);
	
	public List<Stats> getAllByType(String type);
	
}
