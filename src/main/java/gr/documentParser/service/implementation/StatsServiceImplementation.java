package gr.documentParser.service.implementation;

import java.util.List;

import gr.documentParser.dao.StatsDao;
import gr.documentParser.model.Stats;
import gr.documentParser.service.StatsService;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class StatsServiceImplementation implements StatsService{
	
	@Inject StatsDao statsDao;
	
	@Override
	public void persistStats(Stats stats){
		statsDao.persistStats(stats);
	}

	@Override
	public Stats getByFilename(String filename) {
		return statsDao.getByFilename(filename);
	}

	@Override
	public void deleteAllByType(String type){
		statsDao.deleteAllByType(type);
	}

	@Override
	public List<Stats> getAllByType(String type) {
		return statsDao.getAllByType(type);
	}

	@Override
	public Long countStats() {
		return statsDao.count();
	}

	@Override
	public List<Stats> getAllStats(int maxStats, int offset) {
		return statsDao.getAllStats(maxStats,offset);
	}

	@Override
	public Long countByType(String type) {
		return statsDao.countByType(type);
	}

}
