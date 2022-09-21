package com.app.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.pojos.BillDetails;
import com.app.repository.BillRepository;

@SpringBootTest
class TestFetchBills {
	
	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private IOwnerService ownerService;
	
	 @Test 
	  void testFetchBills() {
	  
	  List<BillDetails> bills=ownerService.fetchUnpaidBills(1); 
	  for(BillDetails bills1:bills) System.out.println(bills1);
	  
	  }
	 

}
