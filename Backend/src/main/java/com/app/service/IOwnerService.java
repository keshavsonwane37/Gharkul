package com.app.service;

import java.util.List;

import com.app.pojos.BillDetails;
import com.app.pojos.CompositeKey;

public interface IOwnerService {

	//add a method to retrieve all unpaid bills
	List<BillDetails> fetchUnpaidBills(int id);
	
	//add a method to retrieve all paid bills
	List<BillDetails> fetchPaidBills(int id);
	
	//method to fetch bill by its composite key
	BillDetails fetchBillByCompositeKey(CompositeKey key);

	//method to update bill table
	void updateBillTable(BillDetails bill);
	
	//add a method to send payment confirmation mail
	void confirmPaymentMail(int userId);
	
}
