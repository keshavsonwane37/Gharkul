package com.app.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponseDTO{
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
	private String role;
	private String houseNumber;
//	public LoginResponseDTO(int id, String firstName, String lastName, String email, String mobileNumber, String role,
//			String houseNumber) {
//		super();
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.mobileNumber = mobileNumber;
//		this.role = role;
//		this.houseNumber = houseNumber;
//	}

}
