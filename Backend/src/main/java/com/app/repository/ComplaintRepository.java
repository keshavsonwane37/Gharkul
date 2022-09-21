package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.pojos.Complaints;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaints, Integer> {

	//custom query to list all complaints for a particular user
	@Query("select c from Complaints c where c.complaintUser.id=:id ")
	List<Complaints> getUserComplaints(@Param(value="id") int userId);
	
}
