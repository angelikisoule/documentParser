package gr.documentParser.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gr.documentParser.model.Answer;
import gr.documentParser.model.Interview;
import gr.documentParser.model.Question;
import gr.documentParser.service.InterviewService;
import gr.documentParser.service.QuestionService;

@Controller
public class HomeController {
	
	@Inject private InterviewService interviewService;
	@Inject private QuestionService questionService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}

	@RequestMapping(value = "/parseDoc", method = RequestMethod.GET)
	public String parseDoc(Locale locale, Model model) {
		parseDocFile("/home/blixabargeld/Desktop/file.doc"); //TODO A Loop Here To Read More Than One Files
		return "home";
	}

	@RequestMapping(value = "/parseXls", method = RequestMethod.GET)
	public String parseXls(Locale locale, Model model) {
		parseXlsFile(null); //TODO Implement Method
		return "home";
	}

	/**
	 * Parse A .doc File Given It's Path
	 * @param filePath
	 */
	private void parseDocFile(String filePath) {
		try {
			File inputFile = new File(filePath);
			FileInputStream myInputStream = new FileInputStream(inputFile.getAbsolutePath());
	        HWPFDocument wordDocument = new HWPFDocument(myInputStream);
	        Range myRange = wordDocument.getRange();
	        int numOfParagraphs = myRange.numParagraphs();
	        
	        Set<Interview> parsed = new HashSet<Interview>();
	        Interview interview = null;
	        Set<Answer> interviewAnswers = null;
	        Question question = null;
	        Answer answer = null;
	        StringBuilder answerText = null;
	        
	        /*
	         * The Paragraphs Loop
	         */
	        for(int i = 0; i < numOfParagraphs; i++) {
	        	String p = myRange.getParagraph(i).text().trim();
	        	if(p!=null && !p.isEmpty()) { //Skip Empty Lines
		        	if(p.startsWith("Interview")) {
	        			if(interview!=null) {
	        				interview.setAnswers(interviewAnswers);
	        	        	parsed.add(interview);
	        			}
	        			//Proceed With The Next Interview
	        			interview = new Interview();
	        			interviewAnswers = new LinkedHashSet<Answer>();
	        			interview.setInterviewId(extractInterviewId(p));
	        			interview.setFilename("filename"); //TODO It Would Be Good To Keep The Filename In The Database	        		
		        	}
	        		else if(isQuestion(p)) { //Question
	        			//Proceed With The Next Question
	        			question = new Question();
	        			answer = new Answer();
	        			answerText = new StringBuilder();
	        			
	        			String questionCode = getQuestionCode(p);
	        			if(questionCode!=null) { //It Can't Be Null, The Same regex Got You In This if-else Branch
	        				question = questionService.questionExists(questionCode, p);
	        				answer.setQuestion(question);
	        				answer.setInterview(interview);
	        				answer.setAnswerText(answerText.toString());
	        				interviewAnswers.add(answer);
	        			}
	        		}
	        		else { //Answer
	        			answerText.append(p); //An Answer May Contain More Than One Paragraph
	        		}
	        	}
	        }
	        /*
	         * The Paragraphs Loop Will End Leaving A Last Not Added To The Results Interview
	         */
	        if(interview!=null) { 
	        	interview.setAnswers(interviewAnswers);
	        	parsed.add(interview);
	        }
	        /*
	         * Persist Document's Interviews
	         */
	        interviewService.persistInterviews(parsed);
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Parse A .xls File Given It's Path
	 * @param filePath
	 */
	private void parseXlsFile(String filePath) { //TODO Parse And Update Interview Entity's Fields

	}
	
	/**
	 * Get The Interview Id Given A Document Paragraph
	 * @param p
	 * @return
	 */
	private Long extractInterviewId(String p) {
		String result = null;
		Pattern pattern = Pattern.compile("^(Interview N°)(\\d+)\\s+.*$");
	    Matcher matcher = pattern.matcher(p);
		if(matcher.find()) {
			result = matcher.group(2);
		}		
		try {
			return Long.parseLong(result);
		}
		catch(NumberFormatException exception) {
			return (long) -1; //Godspeed
		}
	}

	/**
	 * Check If Document's Paragraph Is A Question
	 * @param p
	 * @return
	 */
	private boolean isQuestion(String p) {
		Pattern pattern = Pattern.compile("^[XCQ]\\d{1,5}\\s+.*$"); //X, C or Q Followed By Numbers
	    Matcher matcher = pattern.matcher(p);
	    if(matcher.matches()) {
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	/**
	 * Get Question's Code Given The Document Paragraph
	 * @param p
	 * @return
	 */
	private String getQuestionCode(String p) {
		String result = null;
		Pattern pattern = Pattern.compile("^([XCQ]\\d{1,5})\\s+.*$"); //You're Repeating Yourself Here
		Matcher matcher = pattern.matcher(p);
	    if(matcher.matches()) {
	    	result = matcher.group(1);
	    }
	    return result;
	}
}
