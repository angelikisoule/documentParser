package gr.documentParser.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gr.documentParser.service.InterviewService;
import gr.documentParser.utils.Pager;

@Controller@RequestMapping(value = "/interviews")
public class InterviewController {

	@Inject private InterviewService interviewService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
        Long countInterviews = interviewService.countInterviews();
		Pager pager = new Pager(request.getRequestURI(), countInterviews, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countInterviews", countInterviews);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("interviews", interviewService.getInterviews(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "interviews/list";
	}
	
	@RequestMapping(value = "interview/{id}", method = RequestMethod.GET)
	public String interview(@PathVariable("id") Long id, Model model) {
		model.addAttribute("interview", interviewService.getInterview(id));
		return "interviews/view";
	}
}
