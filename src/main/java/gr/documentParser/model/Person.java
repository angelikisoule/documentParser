package gr.documentParser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "phone1")
	private Long phone1;
	
	
	@Column(name = "phone2")
	private Long phone2;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getPhone1() {
		return phone1;
	}


	public void setPhone1(Long phone1) {
		this.phone1 = phone1;
	}


	public Long getPhone2() {
		return phone2;
	}


	public void setPhone2(Long phone2) {
		this.phone2 = phone2;
	}
	
	
	

}
