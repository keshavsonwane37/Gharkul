package com.app.service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.BillDetailsDTO;
import com.app.pojos.BillDetails;
import com.app.pojos.CompositeKey;
import com.app.pojos.Notice;
import com.app.pojos.PaymentMode;
import com.app.pojos.User;
import com.app.repository.BillRepository;
import com.app.repository.NoticeRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private NoticeRepository noticeRepo;

	@Autowired
	private BillRepository billRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public Notice storeNotice(MultipartFile file, String expirydate, String title) throws IOException {
		System.out.println("in storeNotice method:" + " " + expirydate + " " + title);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		System.out.println("fileName is:" + fileName);
		Notice notice = new Notice();
		notice.setTitle(title);
		// String date=expirydate.substring(1, expirydate.length()-1);
		notice.setNoticeExpiryDate(LocalDate.parse(expirydate));
		notice.setType(file.getContentType());
		notice.setData(file.getBytes());
		notice.setFileName(fileName);
		return noticeRepo.save(notice);
	}

	@Override
	public void generateBill(BillDetailsDTO newBill) {

		List<BillDetails> bills = new ArrayList<>();
		BillDetails b1 = null;

		List<Integer> u = userRepo.getAllIds();
		for (Integer u1 : u) {

			b1 = new BillDetails(new CompositeKey(newBill.getBillId(), u1), newBill.getAmount(), newBill.getBillDate(),
					newBill.getPurpose(), newBill.getLatePaymentAmount(), newBill.getDueDate());
			bills.add(b1);
		}
		billRepo.saveAll(bills);
	}

	@Override
	public List<User> listAllHouserOwners() {
		return userRepo.findAllOwners();
	}

	@Override
	public List<BillDetails> getAllBills() {
		List<BillDetails>bills=new ArrayList<>();
		BillDetails bill=null;
		for(Object[] b:billRepo.fetchAllBills()){
			//converting SQL date to Local Date
			LocalDate billGenerationDate=((Date)b[2]).toLocalDate();
			LocalDate billActivationDate=((Date)b[6]).toLocalDate();
			LocalDate dueDate=((Date)b[5]).toLocalDate();
			bill=new BillDetails(new CompositeKey((String)b[0],0),
					(double)b[1],billGenerationDate,billActivationDate, null, false, null,(String)b[3],(double)b[4],dueDate,
					null);
			bills.add(bill);
		};
		System.out.println(bills);
		return bills;
	}

	@Override
	public List<BillDetails> getAllUnpaidBillsForDate(LocalDate date) {
		System.out.println("date: " + date);
		return billRepo.fetchAllUnpaidBillsForDate(date);
	}

	@Override
	public List<BillDetails> getAllPaidBillsForDate(LocalDate date) {
		System.out.println("date: " + date);
		return billRepo.fetchAllPaidBillsForDate(date);
	}

	@Override
	public void deleteNotice(int id) {
		noticeRepo.deleteById(id);
	}

	@Override
	public List<Notice> fetchAllActiveNotices() {
		List<Notice> notice = noticeRepo.findAllActiveNotices();

		Collections.sort(notice, new Comparator<Notice>() {

			public int compare(Notice n1, Notice n2) {
				return n1.getUploadTime().compareTo(n2.getUploadTime());
			}

		});

		return notice;
	}

	// method to fetch the BillDetails object using Composite key
	@Override
	public BillDetails fetchBillByCompositeKey(CompositeKey key) {

		return billRepo.findById(key).get();
	}

	// method to update the status of payment success
	@Override
	public void updateBillTable(BillDetails bill) {
		bill.setModeOfPayment(PaymentMode.OFFLINE);
		bill.setStatus(true);
		bill.setPaymentDate(LocalDate.now());
		billRepo.save(bill);
	}

	@Override
	public String deleteBillByBillId(String billId) {
		System.out.println("billId: " + billId);
		billRepo.deleteByBillId(billId);
		/*
		 * CompositeKey key = null; List<Integer> u = userRepo.getAllIds();
		 * 
		 * for (Integer u1 : u) { System.out.println(u1); key = new CompositeKey(billId,
		 * u1); billRepo.deleteById(key); }
		 */
		return "Bill deleted successfully...!!";
	}

}
