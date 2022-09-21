package com.app.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.ComplaintsDTO;
import com.app.pojos.Complaints;
import com.app.pojos.User;
import com.app.repository.ComplaintRepository;
import com.app.repository.UserRepository;

@Transactional
@Service
public class ComplaintServiceImpl implements IComplaintService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ComplaintRepository complaintRepo;
	
	@Override
	public Complaints generateComplaint(int id, ComplaintsDTO complaint) {
		User user = userRepository.findById(id).get();
		Complaints com = new Complaints(complaint.getComplaintTitle(), complaint.getComplaintBody(),LocalDate.now(),complaint.getComplaintReply()
				,true, user);
		user.addComplaint(com);
		return complaintRepo.save(com);
	}


	@Override
	public List<Complaints> fetchUserComplaints(int id) {
		System.out.println(id);
		return complaintRepo.getUserComplaints(id);
	}

}
