package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePasswordDTO {

	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
}
