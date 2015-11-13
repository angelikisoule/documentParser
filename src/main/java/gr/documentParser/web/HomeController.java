package gr.documentParser.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gr.documentParser.model.Interview;
import gr.documentParser.service.InterviewService;

@Controller
public class HomeController {
	
	@Inject private InterviewService interviewService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		try {
			File inputFile = new File("/home/blixabargeld/Desktop/file.doc");
			FileInputStream myInputStream = new FileInputStream(inputFile.getAbsolutePath());
	        HWPFDocument wordDocument = new HWPFDocument(myInputStream);
	        Range myRange = wordDocument.getRange();
	        int numOfParagraphs = myRange.numParagraphs();
	        for(int i = 0; i < numOfParagraphs; i++) {
	        	String p = myRange.getParagraph(i).text();
	        	System.out.println(p);
	        	
	        	if(p!=null && p.trim().startsWith("Interview")) {
	        		Interview interview = new Interview();
	        		interview.setId(getInterviewId(p));
	        		interview.setPersonId("person");
	        		interview.setFilename("filename");
	        		interviewService.persistInterview(interview); //Create Or Update The Existing One
	        	}
	        	
	        }
	        
		}
		catch(Exception exception) {
			System.out.println(exception);
		}
		return "home";
	}
	
	/**
	 * Get The Interview Id Given A Doc Paragraph
	 * @param p
	 * @return
	 */
	private Long getInterviewId(String p) {
		
		return (long) 3454;
	}
	
}
