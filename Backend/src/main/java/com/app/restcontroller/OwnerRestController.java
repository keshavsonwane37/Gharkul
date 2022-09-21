package com.app.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PaymentResponseDTO;
import com.app.dto.ResponseMessage;
import com.app.pojos.BillDetails;
import com.app.pojos.Complaints;
import com.app.pojos.CompositeKey;
import com.app.pojos.Role;
import com.app.service.IComplaintService;
import com.app.service.IOwnerService;
import com.app.service.IUserService;

@RestController
@RequestMapping("/owner")
@CrossOrigin
public class OwnerRestController {

	@Autowired
	private IOwnerService ownerService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IComplaintService complaintService;

	public OwnerRestController() {
		System.out.println("in ctor of " + getClass().getName());
	}

	// Postman:pass
	//list all unpaid bills:  http://localhost:8080/owner/list_all_unpaid/1
	@PostMapping("/list_all_unpaid/{id}")
	public ResponseEntity<?> fetchUnpaidBills(@PathVariable int id) {
		String message = "";
		try {
			System.out.println("User Id:" + id);
			List<BillDetails> bills = ownerService.fetchUnpaidBills(id);
			for (BillDetails bills1 : bills)
				System.out.println(bills1);
			return new ResponseEntity<>(ownerService.fetchUnpaidBills(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable retrieve the unpaid bills.. ";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	// Postman: pass
	// list all paid bills : http://localhost:8080/owner/list_all_paid/1
	@PostMapping("/list_all_paid/{id}")
	public ResponseEntity<?> fetchPaidBills(@PathVariable int id) {
		String message = "";
		try {
			System.out.println("User Id:" + id);
			List<BillDetails> bills = ownerService.fetchPaidBills(id);
			for (BillDetails bills1 : bills)
				System.out.println(bills1);
			return new ResponseEntity<>(ownerService.fetchPaidBills(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable retrieve the paid bills.. ";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	// billid+userid
	// amount + late fee
	//payment ::  {http://localhost:8080/owner/paymentPage/1/A101}
	@PostMapping("/paymentPage/{userId}/{billId}")
	public ResponseEntity<?> validatePayment(@PathVariable String billId, @PathVariable int userId) {
		String message = "";
		try {
			CompositeKey key = new CompositeKey(billId, userId);
			System.out.println("Key: "+key);
			BillDetails bill = ownerService.fetchBillByCompositeKey(key);
			System.out.println("Bill: "+bill);
			System.out.println("");
			if (LocalDate.now().isBefore(bill.getDueDate())) {
				message = "You are paying on time";
				//impr
				bill.setActualPaidAmount(bill.getAmount());
				System.out.println("Actual amount paid(amt) : "+bill.getActualPaidAmount());
				return ResponseEntity.status(HttpStatus.OK).body(new PaymentResponseDTO(bill.getAmount(), message));
			} else {
				message = "Late payment fee is added ";
				bill.setActualPaidAmount(bill.getLatePaymentAmount());
				System.out.println("Actual amount paid (late amt): "+bill.getActualPaidAmount());
				return ResponseEntity.status(HttpStatus.OK)
						.body(new PaymentResponseDTO((bill.getLatePaymentAmount()), message));
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "Error during payment";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	//payment_success ::  {http://localhost:8080/owner/payment/1/A101}
	@PostMapping("/payment/{userId}/{billId}")
	public ResponseEntity<?> confirmPayment(@PathVariable String billId, @PathVariable int userId) {
		String message = "";
		try {
			System.out.println("in controllers payment succees ");
			CompositeKey key = new CompositeKey(billId, userId);
			BillDetails bill = ownerService.fetchBillByCompositeKey(key);
			Role role = userService.fetchRole(userId).getRole();
			System.out.println("Fetch role :" + role);
			if (LocalDate.now().isBefore(bill.getDueDate()))
				bill.setActualPaidAmount(bill.getAmount());
			else 
				bill.setActualPaidAmount(bill.getLatePaymentAmount());
			
			if (role == Role.OWNER) {
				ownerService.updateBillTable(bill);
			}
			message = "Payment Done successfully ";
			ownerService.confirmPaymentMail(userId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error during payment";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	//complaint ::  {http://localhost:8080/complaint/1}
	@PostMapping("/complaint/{id}")
	public ResponseEntity<?> listUserComplaints(@PathVariable int id) {
		String message = "";
		try {
			System.out.println("User Id:" + id);
			List<Complaints> complaints = complaintService.fetchUserComplaints(id);
			for (Complaints complaints1 : complaints)
				System.out.println(complaints1);
			return new ResponseEntity<>(complaintService.fetchUserComplaints(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to fetch user complaints.... ";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

}
