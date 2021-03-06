package gr.documentParser.web;

import gr.documentParser.service.InterviewService;
import gr.documentParser.service.PersonService;
import gr.documentParser.service.QuestionService;
import gr.documentParser.service.StatsService;
import gr.documentParser.utils.Pager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller@RequestMapping(value = "/persons")
public class PersonController {

	@Inject private PersonService personService;
	@Inject private InterviewService interviewService;
	@Inject private StatsService statsService;
	@Inject private QuestionService questionService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, 
			Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
        Long countInterviews = personService.countPersons();
		Pager pager = new Pager(request.getRequestURI(), countInterviews, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countPersons", countInterviews);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("persons", personService.getPersons(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "persons/list";
	}
	
	@RequestMapping(value = "person/{id}", method = RequestMethod.GET)
	public String interview(@PathVariable("id") Long id, Model model) {
		model.addAttribute("person", personService.getPerson(id));
		return "persons/person";
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
	
	@ModelAttribute("countQuestions")
	public Long countQuestions(){
		return questionService.countAll();
	}

}
