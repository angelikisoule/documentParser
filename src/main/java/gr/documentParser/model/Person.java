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
	private String phone1;
	
	
	@Column(name = "phone2")
	private String phone2;
	
	@Column(name = "addressId")
	private Long addressid;
	
	
	@Column(name = "interviewId")
	private String interviewId;

	@Column(name = "filename")
	private String filename;


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public Long getAddressid() {
		return addressid;
	}


	public void setAddressid(Long addressid) {
		this.addressid = addressid;
	}


	public String getInterviewId() {
		return interviewId;
	}


	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPhone1() {
		return phone1;
	}


	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}


	public String getPhone2() {
		return phone2;
	}


	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	
	

}
