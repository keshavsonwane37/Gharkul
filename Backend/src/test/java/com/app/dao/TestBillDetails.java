package com.app.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.pojos.BillDetails;
import com.app.pojos.CompositeKey;
import com.app.pojos.PaymentMode;
import com.app.repository.BillRepository;
import com.app.repository.UserRepository;

@SpringBootTest
class TestBillDetails {
	
	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Test
	void testInsertRecord() {	
		
		List<BillDetails> bills=new ArrayList<>();
		String billid="A102";
		double amount=3000;
		String billDate="01-23-2021";
		String purpose="maintainance";
		
		BillDetails b1=null;
		CompositeKey key=null;
		
		List<Integer> u=userRepo.getAllIds();
		
		for(Integer u1:u) {
			
			System.out.println(u1);
		     key=new CompositeKey("A101",u1);
		     billRepo.deleteById(key);
			
		/*
		 * b1=new BillDetails(new CompositeKey(billid,u1),
		 * 3000,LocalDate.parse("1998-01-21"),LocalDate.parse("2021-08-27"),
		 * PaymentMode.ONLINE, false,LocalDate.parse("2020-09-29"), "maintainance",
		 * 500,LocalDate.parse("2022-05-04")); bills.add(b1);
		 */
		}	}
	
}
