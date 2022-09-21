package com.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.dto.BillDetailsDTO;
import com.app.pojos.BillDetails;
import com.app.pojos.CompositeKey;

@Repository
public interface BillRepository extends JpaRepository<BillDetails, CompositeKey>{
	
	//custom query method to fetch all unpaid bills
	@Query("select b from BillDetails b where b.status=false and b.compositeKey.userId=:id order by b.billGenerationDate desc")
	List<BillDetails> fetchAllUnpaidBills(@Param(value = "id") int userId);
	
	//custom query method to fetch all unpaid bills
	@Query("select b from BillDetails b where b.status=true and b.compositeKey.userId=:id order by b.paymentDate desc")
	List<BillDetails> fetchAllPaidBills(@Param(value="id") int userId);

	//custom query method to fetch all bills with descending order of their billGenerationDate
	@Query(value="select distinct b.bill_id,b.amount,b.bill_generation_date,b.purpose,b.late_amount,b.due_date,b.bill_activation_date from bill_details b order by b.bill_generation_date desc",
				nativeQuery=true)
	List<Object[]> fetchAllBills();
	
	//custom query method to Fetch all unpaid bill for particular billingMonth
	@Query("select b from BillDetails b where b.status=false and b.billingDate=:date")
	List<BillDetails> fetchAllUnpaidBillsForDate(@Param(value = "date") LocalDate billingDate);
		
	//custom query method to Fetch all paid bill for particular billingMonth
	@Query("select b from BillDetails b where b.status=true and b.billingDate=:date")
	List<BillDetails> fetchAllPaidBillsForDate(@Param(value = "date") LocalDate billingDate);
	
		@Modifying
		@Query("delete from BillDetails b where b.compositeKey.billId=:id")
		void deleteByBillId(@Param(value = "id") String billId);
		
}
