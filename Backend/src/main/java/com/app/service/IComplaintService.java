package com.app.service;

import java.util.List;

import com.app.dto.ComplaintsDTO;
import com.app.pojos.Complaints;

public interface IComplaintService {

	// method to generate user complaint
	Complaints generateComplaint(int id, ComplaintsDTO complaint);

	// method to fetch particular user complaints
	List<Complaints> fetchUserComplaints(int id);

}
