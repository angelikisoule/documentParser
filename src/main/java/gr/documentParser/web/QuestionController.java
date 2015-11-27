package gr.documentParser.web;

import gr.documentParser.service.InterviewService;
import gr.documentParser.service.PersonService;
import gr.documentParser.service.QuestionService;
import gr.documentParser.service.StatsService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/questions")
public class QuestionController {
	
	@Inject private QuestionService questionService;
	@Inject private InterviewService interviewService;
	@Inject private PersonService personService;
	@Inject private StatsService statsService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute("questions", questionService.getAll());
		return "questions/list";
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
