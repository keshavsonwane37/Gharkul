package com.app.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.BillDetailsDTO;
import com.app.pojos.BillDetails;
import com.app.pojos.CompositeKey;
import com.app.pojos.Notice;
import com.app.pojos.User;

public interface IAdminService {

	// Add a method to upload the notice in the database
	Notice storeNotice(MultipartFile file, String expirydate, String title) throws IOException;

	// Add a method to upload a new bill by the owner
	void generateBill(BillDetailsDTO newBill);

	// method to list all house Owners
	List<User> listAllHouserOwners();

	// method to fetch all distinct bill_details object sorted in descending order
	// of bill_generation_date
	List<BillDetails> getAllBills();

	// method to Fetch all unpaid bill for particular billingMonth
	List<BillDetails> getAllUnpaidBillsForDate(LocalDate date);

	// method to Fetch all paid bill for particular billingMonth
	List<BillDetails> getAllPaidBillsForDate(LocalDate date);

	// //method to Delete by bill_id (delete all the entries from bill_details table
	// for the given billId)
	String deleteBillByBillId(String billId);

	// add a method to delete notices by id
	void deleteNotice(int id);

	// add a method to fetch all the active notices from the database
	List<Notice> fetchAllActiveNotices();

	// method to fetch bill by composite key
	BillDetails fetchBillByCompositeKey(CompositeKey key);

	// method to update bill table
	void updateBillTable(BillDetails bill);

}
