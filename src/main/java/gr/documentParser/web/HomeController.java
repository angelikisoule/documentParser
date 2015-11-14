package gr.documentParser.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;

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
		parseDocFile("/Users/asoule/Downloads/sample1.rtf"); //TODO A Loop Here To Read More Than One Files
		return "home";
	}

	@RequestMapping(value = "/parseXls", method = RequestMethod.GET)
	public String parseXls(Locale locale, Model model) {
		parseXlsFile("/Users/asoule/Downloads/telephones1.xls"); //TODO Implement Method
		return "home";
	}

	/**
	 * Parse A .doc File Given It's Path
	 * @param filePath
	 */
	private void parseDocFile(String filePath) {
		// read rtf from file
	    JEditorPane pane = new JEditorPane();
	    pane.setContentType("text/rtf");
	    EditorKit rtfKit = pane.getEditorKitForContentType("text/rtf");
	    try {
			rtfKit.read(new FileReader(filePath), pane.getDocument(), 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    rtfKit = null;

	    // convert to text
	    EditorKit txtKit = pane.getEditorKitForContentType("text/plain");
	    Writer writer = new StringWriter();
	    try {
			txtKit.write(writer, pane.getDocument(), 0, pane.getDocument().getLength());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String documentText = writer.toString();
	    
	    String paragraphs[] = documentText.split("\n");
	        
	        Set<Interview> parsed = new HashSet<Interview>();
	        Interview interview = null;
	        Set<Answer> interviewAnswers = null;
	        Question question = null;
	        Answer answer = null;
	        StringBuilder answerText = null;
	        
	        /*
	         * The Paragraphs Loop
	         */
	        for(int i = 0; i < paragraphs.length; i++) {
	        	String p = paragraphs[i].trim();
	        	if(p!=null && !p.isEmpty() && p.trim().length()!=0) { //Skip Empty Lines
	        		System.out.println("paragraph : "+p);
		        	if(p.startsWith("Interview")) {
	        			if(interview!=null) {
	        				if(answer!=null) { //Manipulate Previous Interview's Last Question's Answer
		        				answer.setAnswerText(answerText.toString());
		        				interviewAnswers.add(answer);
		        			}
	        				interview.setAnswers(interviewAnswers);
	        	        	parsed.add(interview);
	        			}
	        			//Proceed With The Next Interview
	        			interview = new Interview();
	        			interviewAnswers = new LinkedHashSet<Answer>();
	        			interview.setInterviewId(extractInterviewId(p));
	        			interview.setFilename("filename"); //TODO It Would Be Good To Keep The Filename In The Database	        		
	        			question = null;
	        			answer = null;
		        	}
	        		else if(isQuestion(p)) { //Question
	        			if(answer!=null) { //Manipulate Previous Question's Answer
	        				answer.setAnswerText(answerText.toString());
	        				interviewAnswers.add(answer);
	        			}
	        			//Proceed With The Next Question
	        			question = new Question();
	        			answer = new Answer();
	        			answerText = new StringBuilder();
	        			
	        			String questionCode = getQuestionCode(p);
	        			if(questionCode!=null) { //It Can't Be Null, The Same regex Got You In This if-else Branch
	        				question = questionService.questionExists(questionCode, p);
	        				answer.setQuestion(question);
	        				answer.setInterview(interview);
	        				//answer.setAnswerText(answerText.toString());
	        				//interviewAnswers.add(answer);
	        			}
	        		}
	        		else { //Answer
	        			answerText.append(p); //An Answer May Contain More Than One Paragraph
	        			answerText.append(" "); //A Divider For Answers With More Than One Line
	        		}
	        	}
	        }
	        /*
	         * The Paragraphs Loop Will End Leaving A Last Not Added To The Results Interview
	         */
	        if(interview!=null) {
	        	if(answer!=null) { //Manipulate Previous Interview's Last Question's Answer
    				answer.setAnswerText(answerText.toString());
    				interviewAnswers.add(answer);
    			}
	        	interview.setAnswers(interviewAnswers);
	        	parsed.add(interview);
	        }
	        /*
	         * Persist Document's Interviews
	         */
	        interviewService.persistInterviews(parsed);
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
