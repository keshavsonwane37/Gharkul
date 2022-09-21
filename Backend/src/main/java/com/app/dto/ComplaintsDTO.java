package com.app.dto;

import java.time.LocalDate;

import com.app.pojos.BaseEntity;
import com.app.pojos.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintsDTO extends BaseEntity {

	private String complaintTitle;

	private String complaintBody;

	private LocalDate complaintDate;

	private String complaintReply;

	private boolean complaintStatus;

	private User complaintUser;

}
