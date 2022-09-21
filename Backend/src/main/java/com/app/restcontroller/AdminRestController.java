package com.app.restcontroller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.BillDetailsDTO;
import com.app.dto.PaidBillsDTO;
import com.app.dto.PaymentResponseDTO;
import com.app.dto.ResponseMessage;
import com.app.pojos.BillDetails;
import com.app.pojos.CompositeKey;
import com.app.pojos.User;
import com.app.service.IAdminService;
import com.app.service.IUserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminRestController {

	@Autowired(required = true)
	private IAdminService adminService;

	@Autowired
	private IUserService userService;

	//@Autowired
	//private IComplaintService complaintService;
	
	//@Autowired
	//private IOwnerService ownerService;
	

	public AdminRestController() {
		System.out.println("in ctor of " + getClass().getName());
	}

	//upload notice: {http://localhost:8080/admin/upload}
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam String expirydate, @RequestParam String title) throws IOException {
		String message = "";
			System.out.println("size of the file is"+file.getSize());
			adminService.storeNotice(file, expirydate, title);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

	}

	// generate Bill
	// generate bill : {http://localhost:8080/admin/generate-bill}
	@PostMapping("/generate-bill")
	public ResponseEntity<ResponseMessage> generateBill(@RequestBody BillDetailsDTO newBill) {
		String message = "";
		try {
			System.out.println("Bill is:" + newBill);
			adminService.generateBill(newBill);
			message = "Bill generated successfully: ";// write a purpose here
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			e.printStackTrace();
			message = "Could not generate the bill: ";// write purpose
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}

	}
	

	// list all users : {http://localhost:8080/admin/list_all_users}
	// postman : passed
	@GetMapping("/list_all_users")
	public ResponseEntity<?> showAllOwners() {
		return new ResponseEntity<>(adminService.listAllHouserOwners(), HttpStatus.OK);
	}

	// Postman: pass
	// fetch all bills :::: http://localhost:8080/admin/list_all_bills
	@PostMapping("/list_all_bills")
	public ResponseEntity<?> fetchAllBills() {
		String message = "";
		try {
			List<BillDetails> bills = adminService.getAllBills();
			for (BillDetails bills1 : bills)
				System.out.println(bills1);
			return new ResponseEntity<>(adminService.getAllBills(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to retrieve bills.. ";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	//change
	// fetch paid bills for date :
		// {http://localhost:8080/admin/list_paidbills_for_date/{date}}
		//@DateTimeFormat(pattern="yyyy-MM-dd")
	@PostMapping("/list_unpaidbills_for_date/{date}")
	public ResponseEntity<?> fetchAllUnpaidBillsForDate(@PathVariable String date) {
		String message = "";
		try {
			LocalDate date1=LocalDate.parse(date);
			List<PaidBillsDTO> paidBills=new ArrayList<>();
			PaidBillsDTO pbd;
			List<BillDetails> bills = adminService.getAllUnpaidBillsForDate(date1);
			List<User> users=userService.fetchAllUsers();
			for (BillDetails bills1 : bills)
			{
				for(User u1:users) {
				System.out.println(bills1);
				if(bills1.getCompositeKey().getUserId()==u1.getId())
				{
					pbd=new PaidBillsDTO(bills1, u1);
					paidBills.add(pbd);
					break;
				}
			}
			}
			return ResponseEntity.ok(paidBills);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to retrieve bills.. ";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}


	// fetch paid bills for date :
			// {http://localhost:8080/admin/list_paidbills_for_date/{date}}
			//@DateTimeFormat(pattern="yyyy-MM-dd")
			@PostMapping("/list_paidbills_for_date/{date}")
			public ResponseEntity<?> fetchAllPaidBillsForDate(@PathVariable String date) {
				String message = "";
				try {
					LocalDate date1=LocalDate.parse(date);
					List<PaidBillsDTO> paidBills=new ArrayList<>();
					PaidBillsDTO pbd;
					List<BillDetails> bills = adminService.getAllPaidBillsForDate(date1);
					List<User> users=userService.fetchAllUsers();
					for (BillDetails bills1 : bills)
					{
						for(User u1:users) {
						System.out.println(bills1);
						if(bills1.getCompositeKey().getUserId()==u1.getId())
						{
							pbd=new PaidBillsDTO(bills1, u1);
							paidBills.add(pbd);
							break;
						}
					}
					}
					return ResponseEntity.ok(paidBills);
				} catch (Exception e) {
					e.printStackTrace();
					message = "Unable to retrieve bills.. ";
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
				}
			}


	//delete bill by billId : {http://localhost:8080/admin/delete_bill/2}
	@DeleteMapping("/delete_bill/{id}")
	public ResponseEntity<?> deleteBillById(@PathVariable String id) {
		
		String message = "";
		try {
//			List<BillDetails> bills = adminService.getAllBills();
//			for (BillDetails bills1 : bills)
//				System.out.println(bills1);
			return new ResponseEntity<>(adminService.deleteBillByBillId(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to delete bill.. ";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
	
	//delete_notice by id :: {http://localhost:8080/admin/delete_notice/1}
		@DeleteMapping("/delete_notice/{id}")
		  public ResponseEntity<ResponseMessage> deleteNotice(@PathVariable int id) {
		    String message = "";
		    try {
		      adminService.deleteNotice(id);
		      message = "Notice deleted successfully!! ";
		      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		    } catch (Exception e) {
		    e.printStackTrace();
		      message = "Could not delete the notice!! Please try again later..!";
		      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		    }
		  }
		
		@PostMapping("/display-active-notices")
		public ResponseEntity<?> fetchAllActiveNotices()
		{
			return new ResponseEntity<>(adminService.fetchAllActiveNotices(),HttpStatus.OK);
		}
		

		//payment_success:: {http://localhost:8080/admin//payment_success/1/A101}
		@PostMapping("/payment_success/{userId}/{billId}")
		public ResponseEntity<?> confirmPayment(@PathVariable String billId, @PathVariable int userId) {
			String message = "";
			try {

				CompositeKey key = new CompositeKey(billId, userId);
				BillDetails bill = adminService.fetchBillByCompositeKey(key);
				System.out.println("Bill " + bill);
				if (LocalDate.now().isBefore(bill.getDueDate()))
					// impr
					bill.setActualPaidAmount(bill.getAmount());
				else
					bill.setActualPaidAmount(bill.getLatePaymentAmount());

				adminService.updateBillTable(bill);
				message = "Payment Status Updated successfully ";
				// ownerService.confirmPaymentMail(userId);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				message = "Error during payment";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
		
}





 