package gr.documentParser.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "answerToken")
public class AnswerToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "answerId", nullable = false)
	private Answer answer;
	
	@Lob
	@NotNull
	@Column(name = "answerTokenText")
	private String answerTokenText;
	
	@Length(min = 1, max = 1000)
	@Column(name = "subQuestion")
	private String subQuestion;
	
	@Length(min = 1, max = 1000)
	@Column(name = "subAnswer")
	private String subAnswer;
	
	public String getSubQuestion() {
		return subQuestion;
	}

	public void setSubQuestion(String subQuestion) {
		this.subQuestion = subQuestion;
	}

	public String getSubAnswer() {
		return subAnswer;
	}

	public void setSubAnswer(String subAnswer) {
		this.subAnswer = subAnswer;
	}

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
	
	public Answer getAnswer() {
		return answer;
	}
	
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	public String getAnswerTokenText() {
		return answerTokenText;
	}
	
	public void setAnswerTokenText(String answerTokenText) {
		this.answerTokenText = answerTokenText;
	}
	
	public Calendar getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Calendar dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
}
