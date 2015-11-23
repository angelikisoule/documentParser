package gr.documentParser.service;

import java.util.List;

import gr.documentParser.model.Stats;

public interface StatsService {
	
	void persistStats(Stats stats);
	
	Stats getByFilename(String filename);
	
	void deleteAllByType(String type);
	
	public List<Stats> getAllByType(String type);

}
