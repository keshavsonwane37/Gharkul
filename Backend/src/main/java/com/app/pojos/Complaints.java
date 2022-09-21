package com.app.pojos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "complaints_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Complaints extends BaseEntity {

	@Column(length = 40, name = "complaint_title")
	private String complaintTitle;
	
	@Column(name = "complaint_body")
	private String complaintBody;
	
	@Column(name = "complaint_date")
	private LocalDate complaintDate;
	
	@Column(name = "complaint_reply")
	private String complaintReply;
	
	@Column(name = "complaint_status")
	private boolean complaintStatus; 
	
	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User complaintUser;

	public String getComplaintTitle() {
		return complaintTitle;
	}

	public void setComplaintTitle(String complaintTitle) {
		this.complaintTitle = complaintTitle;
	}

	public String getComplaintBody() {
		return complaintBody;
	}

	public void setComplaintBody(String complaintBody) {
		this.complaintBody = complaintBody;
	}

	public LocalDate getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(LocalDate complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getComplaintReply() {
		return complaintReply;
	}

	public void setComplaintReply(String complaintReply) {
		this.complaintReply = complaintReply;
	}

	public boolean isComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(boolean complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public User getComplaintUser() {
		return complaintUser;
	}

	public void setComplaintUser(User complaintUser) {
		this.complaintUser = complaintUser;
	}

	


}
