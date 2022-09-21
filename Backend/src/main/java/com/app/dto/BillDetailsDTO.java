package com.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//"billId":"A103",
//"amount":5000,
//"billDate":"2021-02-01",
//"purpose":"maintainance",
//"latePaymentAmount":"500",
//"dueDate":"2021-03-14"

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillDetailsDTO {

	private String billId;
	private double amount;
	private LocalDate billDate;
	private String purpose;
	private double latePaymentAmount;
	private LocalDate dueDate;
}
