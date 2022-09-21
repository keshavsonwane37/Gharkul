package com.app.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.pojos.BillDetails;
import com.app.pojos.CompositeKey;
import com.app.pojos.PaymentMode;
import com.app.pojos.User;
import com.app.repository.BillRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class OwnerServiceImpl implements IOwnerService{
	
	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JavaMailSender mailSend;

	@Override
	public List<BillDetails> fetchUnpaidBills(int id) {
		
		return billRepo.fetchAllUnpaidBills(id);
	}

	@Override
	public List<BillDetails> fetchPaidBills(int id) {
		
		return billRepo.fetchAllPaidBills(id);
	}
	
	@Override
	public BillDetails fetchBillByCompositeKey(CompositeKey key) {

	 return billRepo.findById(key).get();
	}

	@Override
	public void updateBillTable(BillDetails bill) {
		System.out.println("in service layers payment succees ");
		bill.setModeOfPayment(PaymentMode.ONLINE);
		bill.setStatus(true);
		bill.setPaymentDate(LocalDate.now());
	//	bill.setActualPaidAmount();
		billRepo.save(bill);		
		
	}
	
	@Override
	public void confirmPaymentMail(int userId) {
		User u = userRepo.findById(userId).get();
		System.out.println(u.getEmail());
		SimpleMailMessage msg = new SimpleMailMessage();
		if (u != null) {
			msg.setTo(u.getEmail());
			msg.setSubject("Payment Confirmation");
			msg.setText("Hey " + u.getFirstName() + ". Your bill payment is successful!!!\nThank You");
			mailSend.send(msg);
		}
	}


}
