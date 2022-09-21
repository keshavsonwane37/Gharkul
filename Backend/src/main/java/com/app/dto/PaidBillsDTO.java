package com.app.dto;

import com.app.pojos.BillDetails;
import com.app.pojos.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaidBillsDTO {
	private BillDetails bill;
	private User u;
}
