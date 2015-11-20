package gr.documentParser.service.implementation;

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

}
