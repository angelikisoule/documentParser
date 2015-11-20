package gr.documentParser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "stats")
public class Stats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "filename")
	@Length(min = 1, max = 200)
	private String filename;
	
	@Column(name = "type")
	@Length(min = 1, max = 10)
	private String type;
	
	@Column(name = "storedElements")
	private Long elements;
	
	@Column(name = "totalElements")
	private Long totalElements;
	
	@Column(name = "countElements")
	private Long countElements;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getElements() {
		return elements;
	}

	public void setElements(Long elements) {
		this.elements = elements;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Long getCountElements() {
		return countElements;
	}

	public void setCountElements(Long countElements) {
		this.countElements = countElements;
	}

}
