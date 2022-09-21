package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmPassword;
	private String mobileNumber;
	private String houseNumber;
//	public RegisterDTO(String firstName, String lastName, String email, String password, String confirmPassword,
//			String mobileNumber, String houseNumber) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.confirmPassword = confirmPassword;
//		this.mobileNumber = mobileNumber;
//		this.houseNumber = houseNumber;
//	}
	
	
	
}
