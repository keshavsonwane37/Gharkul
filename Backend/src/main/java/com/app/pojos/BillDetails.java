package com.app.pojos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bill_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BillDetails{
	
	@EmbeddedId
	private CompositeKey compositeKey;

	private double amount;
	
	@Column(name = "bill_generation_date")
	private LocalDate billGenerationDate=LocalDate.now();
	
	@Column(name="bill_activation_date")
	private LocalDate billingDate;
	
	//update at the time of payment by owner
	@Column(name = "mode_of_payment")
	private PaymentMode modeOfPayment=null;
	
	//this will set true after payment done by owner
	private boolean status=false;
	
	//date of payment : inserted after payment
	@Column(name = "payment_date")
	private LocalDate paymentDate=null;
	
	public CompositeKey getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(CompositeKey compositeKey) {
		this.compositeKey = compositeKey;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getBillGenerationDate() {
		return billGenerationDate;
	}

	public void setBillGenerationDate(LocalDate billGenerationDate) {
		this.billGenerationDate = billGenerationDate;
	}

	public LocalDate getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(LocalDate billingDate) {
		this.billingDate = billingDate;
	}

	public PaymentMode getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(PaymentMode modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Double getLatePaymentAmount() {
		return latePaymentAmount;
	}

	public void setLatePaymentAmount(Double latePaymentAmount) {
		this.latePaymentAmount = latePaymentAmount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Double getActualPaidAmount() {
		return actualPaidAmount;
	}

	public void setActualPaidAmount(Double actualPaidAmount) {
		this.actualPaidAmount = actualPaidAmount;
	}

	private String purpose;
	
	@Column(name = "late_amount")
	private Double latePaymentAmount;
	
	@Column(name = "due_date")
	private LocalDate dueDate;
	
	@Column(name = "actual_paid_amount")
	private Double actualPaidAmount=0.0;
	
	public BillDetails(CompositeKey compositeKey, double amount, LocalDate billingDate, String purpose,
			double latePaymentAmount, LocalDate dueDate) {
		super();
		this.compositeKey = compositeKey;
		this.amount = amount;
		this.billingDate = billingDate;
		this.purpose = purpose;
		this.latePaymentAmount = latePaymentAmount;
		this.dueDate = dueDate;
	}
}
