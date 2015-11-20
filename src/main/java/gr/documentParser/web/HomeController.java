package gr.documentParser.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gr.documentParser.model.Answer;
import gr.documentParser.model.AnswerToken;
import gr.documentParser.model.Interview;
import gr.documentParser.model.Person;
import gr.documentParser.model.Question;
import gr.documentParser.model.Stats;
import gr.documentParser.service.InterviewService;
import gr.documentParser.service.PersonService;
import gr.documentParser.service.QuestionService;
import gr.documentParser.service.StatsService;

@Controller
public class HomeController {
	
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	@Inject private InterviewService interviewService;
	@Inject private QuestionService questionService;
	@Inject private PersonService personService;
	@Inject private StatsService statsService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}

	@RequestMapping(value = "/parseTxt", method = RequestMethod.GET)
	public String parseTxt(Locale locale, Model model) throws UnsupportedEncodingException {
		final File folder = new File("/home/blixabargeld/Desktop/test/");
		for(final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	            if(!fileEntry.getName().startsWith(".") && fileEntry.getName().endsWith(".txt")){
	            	if(interviewService.getByFilename(fileEntry.getName()).isEmpty()) //Don't Parse Same Files
	            		parseTxtFile(fileEntry);
	            }
	        }
	    }
		return "home";
	}

	@RequestMapping(value = "/parseXls", method = RequestMethod.GET)
	public String parseXls(Locale locale, Model model) {
		final File folder = new File("/home/angeliki/Downloads/documentParser/xls/telephones-renamed/");
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
				parseXlsFile(fileEntry);
			}
		}
		return "home";
	}

	/**
	 * Parse A .rtf File Given It's Path
	 * @param file The File To Parse
	 * @throws UnsupportedEncodingException 
	 */
	private void parseTxtFile(File file) throws UnsupportedEncodingException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.toString()));
			Set<Interview> parsed = new HashSet<Interview>();
	        Interview interview = null;
	        Set<Answer> interviewAnswers = null;
	        Question question = null;
	        Answer answer = null;
	        Set<AnswerToken> tokens = null;
	        StringBuilder answerText = null;
	        Long interviewCounter = 0L;
			
	        Stats stats = new Stats();
			stats.setFilename(file.getName());
			stats.setType("txt");
	        
			String paragraph;
			while ((paragraph = br.readLine()) != null) {
				String p = paragraph.trim();
				if(p!=null && !p.isEmpty() && p.trim().length()!=0) { //Skip Empty Lines
	        		System.out.println(p);
					if(p.startsWith("Interview")) {
	        			if(interview!=null) {
	        				if(answer!=null) { //Manipulate Previous Interview's Last Question's Answer
	        					answer.setAnswerTokens(filterAnswerTokens(tokens));
	        					interviewAnswers.add(answer);
		        			}
	        				interview.setAnswers(interviewAnswers);
	        				interviewCounter++;
	        	        	parsed.add(interview);
	        			}
	        			//Proceed With The Next Interview
	        			interview = new Interview();
	        			interviewAnswers = new LinkedHashSet<Answer>();
	        			interview.setInterviewId(extractInterviewId(p));
	        			interview.setFilename(file.getName());
	        			question = null;
	        			answer = null;
		        	}
	        		else if(isQuestion(p)) { //Question
	        			if(answer!=null) { //Manipulate Previous Question's Answer
	        				answer.setAnswerTokens(filterAnswerTokens(tokens));
	        				interviewAnswers.add(answer);
	        			}
	        			//Proceed With The Next Question
	        			question = new Question();
	        			answer = new Answer();
	        			tokens = new LinkedHashSet<AnswerToken>();
	        			
	        			String questionCode = getQuestionCode(p);
	        			if(questionCode!=null) { //It Can't Be Null, The Same regex Got You In This if-else Branch
	        				question = questionService.questionExists(questionCode, p);
	        				answer.setQuestion(question);
	        				answer.setInterview(interview);
	        			}
	        		}
	        		else if(isLast(p)){
	        			System.out.println(p);
	        			System.out.println(getLast(p,1));
	        			System.out.println(getLast(p,2));
	        			stats.setElements(Long.valueOf(getLast(p,1)));
	        			stats.setTotalElements(Long.valueOf(getLast(p,2)));
	        			
	        		}
	        		else { //Answer
	        			AnswerToken token = new AnswerToken();
	        			token.setAnswer(answer);
	        			token.setAnswerTokenText(p);
	        			if(answer.getQuestion().getQuestionCode().equals("Q60")) {
	        				String[] sub = q60(p);
	        				if(sub!=null) {
	        					token.setSubQuestion(sub[0].trim());
	        					token.setSubAnswer(sub[1]);
	        				}
	        			}
	        			tokens.add(token);
	        			if(token.getAnswer().getQuestion().getQuestionCode().equals("X2")) {
	        				Long x = Long.parseLong(p);
	        				interview.setAddressId(x);
	        			}
	        		}
				}
			}
			/*
	         * The Paragraphs Loop Will End Leaving A Last Not Added To The Results Interview
	         */
	        if(interview!=null) {
	        	if(answer!=null) { //Manipulate Previous Interview's Last Question's Answer
	        		answer.setAnswerTokens(filterAnswerTokens(tokens));
    				interviewAnswers.add(answer);
	        	}
	        	interview.setAnswers(interviewAnswers);
	        	parsed.add(interview);
	        }
	        stats.setCountElements(interviewCounter);
	        statsService.persistStats(stats);
	        interviewService.persistInterviews(parsed);
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parse A .xls File Given It's Path
	 * @param file
	 */
	private void parseXlsFile(File file) { //TODO Parse And Update Interview Entity's Fields
		try {
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file.toString()));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell1;
		    HSSFCell cell2;
		    HSSFCell cell3;
		    HSSFCell cell4;
		    Set<Interview> compliteInterviews = new HashSet<Interview>();
		    Stats stats = new Stats();
			stats.setFilename(file.getName());
			stats.setType("xls");

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
		            	//Persist Person Table
		            	Long interviewId= new Long((long)Double.parseDouble(cell1.toString()));
		            	Person person = new Person();
		            		
		            	person.setInterviewId(interviewId.toString());
		            	
		            	Long x1= new Long((long)Double.parseDouble(cell2.toString()));
		            	Long addressId= x1;
		            	
		            	//Check That AddressId Is Unique
	        			if(personService.countAddressId(addressId)==0)
	        				System.out.println("AddressId : "+addressId+" DOES NOT EXIST");
	        			else
	        				System.out.println("AddressId : "+addressId+" ALREADY EXISTS");

		            	person.setAddressid(addressId);
		            		
		            	Long x2= new Long((long)Double.parseDouble(cell3.toString()));
		            	String phone1= x2.toString();
		            	person.setPhone1(phone1);
		            		
		            	Long x3= new Long((long)Double.parseDouble(cell4.toString()));
		            	String phone2= x3.toString();
		            	person.setPhone2(phone2);

		            	person.setFilename(file.getName());
		            	
		            	System.out.print(interviewId+"\t"+addressId+"\t"+phone1+"\t"+phone2);
		            	personService.persistPerson(person);

		            	//Persist Interviews
		            	if(interviewService.countByAddressId(addressId)==0){
		            		System.out.println("\nAddressId : "+addressId+" is NOT Storred");
		            		//DO Nothing, Wait Other Parser To Store New Interview Files

		            	}
		            	else if(interviewService.countByAddressId(addressId)==1){
		            		//Perist Existing Interview
		            		System.out.println("AddressId : "+addressId+" is Storred");
		            		Interview inter = interviewService.getByAddressId(addressId);
		            		inter.setPhone1(phone1);
		            		inter.setPhone2(phone2);
		            		compliteInterviews.add(inter);
		            	}
		            	else{
		            		//AddressId Is More Than One Time, Cannot Match Interview With Person (phones)
		            		System.out.println("AddressId : "+addressId+" is Storred 2 times");
		            	}
		            }
		        }
		        System.out.print("\n");
		    }
		    interviewService.persistInterviews(compliteInterviews);
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
	
	
	private boolean isLast(String p) {
		Pattern pattern = Pattern.compile("^(\\d{1,3}) interviews displayed out of (\\d{1,4}) interviews.$"); //X, C or Q Followed By Numbers
	    Matcher matcher = pattern.matcher(p);
	    if(matcher.matches()) {
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	
	private String getLast(String p, int i) {
		String result = null;
		Pattern pattern = Pattern.compile("^(\\d{1,3}) interviews displayed out of (\\d{1,4}) interviews.$"); //X, C or Q Followed By Numbers
	    Matcher matcher = pattern.matcher(p);
	    if(matcher.matches() && i<3) {
	    	result = matcher.group(i);
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
	
	/**
	 * @param paragraph
	 * @return
	 */
	private String[] q60(String paragraph) {
		String[] result = null;
		Pattern pattern = Pattern.compile("^(.*)(\\d{1,2}\\s+[(]\\d{1,2}[)].*)$"); 
		Matcher matcher = pattern.matcher(paragraph);
	    if(matcher.matches()) {
	    	result = new String[] { matcher.group(1), matcher.group(2)};
	    }
	    return result;
	}
	
	/*
	 * If An Answer Has Tokens That Start With 'S' We Have To Check If All Answer Tokens Start
	 * With 'S'. Any Token That Does Not Must Be Appended To The Previous' 'S' Token Text Value
	 */
	private Set<AnswerToken> filterAnswerTokens(Set<AnswerToken> initial) {
		int counter = 0;
		boolean specialCase = false;
		String pattern = "^[S]\\d{1,2}.*$";
		Set<AnswerToken> result = new LinkedHashSet<AnswerToken>();
		Stack<AnswerToken> finalTokens = new Stack<AnswerToken>(); //You Need The Push / Pop Functionality
		Iterator<AnswerToken> iterator = initial.iterator(); 
	    while(iterator.hasNext()) {
	    	AnswerToken element = iterator.next();
	    	if(counter==0 && element.getAnswerTokenText().matches(pattern)) { //The First Time Check If The Answers Are Generally Starting With 'S'
	    		specialCase = true;
	    	}
	    	if(specialCase && element.getAnswerTokenText().matches(pattern)) { //AnswerToken Starts With 'S'
	    		finalTokens.push(element);
	    	}
	    	else {
	    		if(specialCase) { //The Answers Start With 'S' But Not The AnswerToken, Append The Text To The Previous One
	    			AnswerToken previous = finalTokens.pop();
	    			String previousText = previous.getAnswerTokenText();
	    			String previousSubAnswer = previous.getSubAnswer();
	    			previous.setAnswerTokenText(previousText + " " + element.getAnswerTokenText());
	    			if(previousSubAnswer!=null && !previousSubAnswer.isEmpty()) { //The Answer May Be Already Split To SubQuestion + SubAnswer 
	    				previous.setSubAnswer(previousSubAnswer + " " + element.getAnswerTokenText());
	    			}
	    			finalTokens.push(previous);
	    		}
	    		else {
	    			finalTokens.push(element);
	    		}
	    	}
	    	counter++; //Just For The First Loop
	    }
		/*
		 * Convert The Stack To LinkedHashSet
		 */
		for(AnswerToken token : finalTokens) {
			result.add(token);
		}
		return result;
	}
}
