package gr.documentParser.model;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "answer")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "interviewId", nullable = false)
	private Interview interview;
	
	@ManyToOne
	@JoinColumn(name = "questionId", nullable = false)
	private Question question;
	
	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
	private Set<AnswerToken> answerTokens = new LinkedHashSet<AnswerToken>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "dateUpdated")
	private Calendar dateUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Interview getInterview() {
		return interview;
	}
	
	public void setInterview(Interview interview) {
		this.interview = interview;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public Set<AnswerToken> getAnswerTokens() {
		return answerTokens;
	}
	
	public void setAnswerTokens(Set<AnswerToken> answerTokens) {
		this.answerTokens = answerTokens;
	}
	
	public Calendar getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Calendar dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
}
