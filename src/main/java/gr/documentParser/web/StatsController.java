package gr.documentParser.web;

import gr.documentParser.service.InterviewService;
import gr.documentParser.service.PersonService;
import gr.documentParser.service.StatsService;
import gr.documentParser.utils.Pager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller@RequestMapping(value = "/stats")
public class StatsController {
	
	@Inject private StatsService statsService;
	@Inject private PersonService personService;
	@Inject private InterviewService interviewService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, 
			Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
        Long countStats = statsService.countStats();
		Pager pager = new Pager(request.getRequestURI(), countStats, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countStats", countStats);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("allStats", statsService.getAllStats(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "stats/list";
	}

	
	@ModelAttribute("countInterviews")
	public Long countInterviews(){
		return interviewService.countInterviews();
	}
	
	@ModelAttribute("countPersons")
	public Long countPersons(){
		return personService.countPersons();
	}
	
	@ModelAttribute("countFiles")
	public Long countFiles(){
		return statsService.countStats();
	}
	
	@ModelAttribute("countTxt")
	public Long countTxtFiles(){
		return statsService.countByType("txt");
	}

	@ModelAttribute("countXls")
	public Long countXlsFiles(){
		return statsService.countByType("xls");
	}
}
