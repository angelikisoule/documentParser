package gr.documentParser.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gr.documentParser.dao.InterviewDao;
import gr.documentParser.model.Answer;
import gr.documentParser.model.AnswerToken;
import gr.documentParser.model.Interview;
import gr.documentParser.model.Person;
import gr.documentParser.model.Question;
import gr.documentParser.service.InterviewService;
import gr.documentParser.service.PersonService;
import gr.documentParser.service.QuestionService;

@Controller
public class HomeController {
	
	@Inject private InterviewService interviewService;
	@Inject private QuestionService questionService;
	@Inject private PersonService personService;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}

	@RequestMapping(value = "/parseDoc", method = RequestMethod.GET)
	public String parseDoc(Locale locale, Model model) throws UnsupportedEncodingException {
		
		//A Loop Here To Read More Than One Files
		final File folder = new File("/Users/asoule/Documents/dataParser/rtf/");
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	            parseRtfFile(fileEntry);
	        }
	    }
		
		return "home";
	}

	@RequestMapping(value = "/parseXls", method = RequestMethod.GET)
	public String parseXls(Locale locale, Model model) {
		
		//A Loop Here To Read More Than One Files
		final File folder = new File("/Users/asoule/Documents/dataParser/xls-phone-open/");
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
			    parseXlsFile(fileEntry); //TODO Implement Method
			}
		}
		
		return "home";
	}

	/**
	 * Parse A .rtf File Given It's Path
	 * @param file
	 * @throws UnsupportedEncodingException 
	 */
	private void parseRtfFile(File file) throws UnsupportedEncodingException {
		// read rtf from file
	    JEditorPane pane = new JEditorPane();
	    pane.setContentType("text/rtf");
	    EditorKit rtfKit = pane.getEditorKitForContentType("text/rtf");
	    try {
			rtfKit.read(new FileReader(file.toString()), pane.getDocument(), 0);
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
//	    byte[] text =documentText1.getBytes("windows-1253");
//	    String documentText = new String(text,"UTF-8");
	    
	    String paragraphs[] = documentText.split("\n");
	        
	        Set<Interview> parsed = new HashSet<Interview>();
	        Interview interview = null;
	        Set<Answer> interviewAnswers = null;
	        Question question = null;
	        Answer answer = null;
	        Set<AnswerToken> tokens = null;
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
	        					answer.setAnswerTokens(tokens);
	        					interviewAnswers.add(answer);
		        			}
	        				interview.setAnswers(interviewAnswers);
	        	        	parsed.add(interview);
	        			}
	        			//Proceed With The Next Interview
	        			interview = new Interview();
	        			interviewAnswers = new LinkedHashSet<Answer>();
	        			interview.setInterviewId(extractInterviewId(p));
	        			interview.setFilename(file.getName()); //TODO It Would Be Good To Keep The Filename In The Database	        		
	        			question = null;
	        			answer = null;
		        	}
	        		else if(isQuestion(p)) { //Question
	        			if(answer!=null) { //Manipulate Previous Question's Answer
	        				answer.setAnswerTokens(tokens);
	        				interviewAnswers.add(answer);
	        			}
	        			//Proceed With The Next Question
	        			question = new Question();
	        			answer = new Answer();
	        			tokens = new LinkedHashSet<AnswerToken>();
	        			answerText = new StringBuilder();
	        			
	        			String questionCode = getQuestionCode(p);
	        			if(questionCode!=null) { //It Can't Be Null, The Same regex Got You In This if-else Branch
	        				question = questionService.questionExists(questionCode, p);
	        				answer.setQuestion(question);
	        				answer.setInterview(interview);
	        			}
	        		}
	        		else { //Answer
	        			/*
	        			 * στον AnswerTokens μπορείς να προσθέσεις και άλλες στήλες για να σπάσεις π.χ.
	        			 * απαντήσεις σαν την  S5  95.2 ATHENS DEE JAY	4 (4) ή Παλαιότερα ή ΠΟΤΕ 
	        			 * και άρα απλά εδώ να βάλεις ένα if(question=Q60) βάλε τιμές σε αυτές τις στήλες
	        			 * ΑΡΑ θα έχεις ελαφρώς δυσκολότερο query στο view σου, αλλα την βάση πολύ 
	        			 * καλύτερα "κανονικοποιημένη". θα σε βοηθήσει όταν ρωτήσει : πόσοι είπαν στον
	        			 * GALAXY "παλαιότερα ή ποτε" (κατάλαβες τι θέλω να πω)
	        			 */
	        			AnswerToken token = new AnswerToken();
	        			token.setAnswer(answer);
	        			token.setAnswerTokenText(p);
	        			tokens.add(token);
	        		}
	        	}
	        }
	        /*
	         * The Paragraphs Loop Will End Leaving A Last Not Added To The Results Interview
	         */
	        if(interview!=null) {
	        	if(answer!=null) { //Manipulate Previous Interview's Last Question's Answer
	        		answer.setAnswerTokens(tokens);
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
	 * @param file
	 */
	private void parseXlsFile(File file) { //TODO Parse And Update Interview Entity's Fields
		HashSet<Person> persons = new HashSet<Person>();
		try {
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file.toString()));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell1;
		    HSSFCell cell2;
		    HSSFCell cell3;
		    HSSFCell cell4;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    int tmp = 0;

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }
		    
		    //First Row Just Info Tables, No Data
		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		        	cell1 = row.getCell((short)0);
		            cell2 = row.getCell((short)1);
		            cell3 = row.getCell((short)2);
		            cell4 = row.getCell((short)3);

		            //Store data
		            if(cell1!=null && cell2!=null && cell3!=null && cell4!=null ){
		            	
		            	Long interviewId= new Long((long)Double.parseDouble(cell1.toString()));
		            		Person person = new Person();
		            		
		            		//Interview inter = interviewService.getByInterviewId(interviewId);
		            		person.setInterviewId(interviewId.toString());
		            		
		            		Long x1= new Long((long)Double.parseDouble(cell2.toString()));
		            		String addressId= x1.toString();
		            		person.setAddressid(addressId);
		            		//inter.setAddressId(addressId);
		            		
		            		Long x2= new Long((long)Double.parseDouble(cell3.toString()));
		            		String phone1= x2.toString();
		            		person.setPhone1(phone1);
		            		//inter.setPhone1(phone1);
		            		
		            		Long x3= new Long((long)Double.parseDouble(cell4.toString()));
		            		String phone2= x3.toString();
		            		person.setPhone2(phone2);
		            		//inter.setPhone2(phone2);
		            		
		            		person.setFilename(file.getName());
		            		
		            		System.out.print(interviewId+"\t"+addressId+"\t"+phone1+"\t"+phone2);
		            		personService.persistPerson(person);
		            	
		            	
		            	
		            }
		                
		            }
		            System.out.print("\nhello");
		        }
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
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
	
	
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
}
