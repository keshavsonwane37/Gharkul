package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.pojos.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice , Integer> {
	
	//custom query to fetch all active notice
	@Query("select n from Notice n where n.noticeExpiryDate>CURRENT_DATE ")
	List<Notice> findAllActiveNotices();
	
}
