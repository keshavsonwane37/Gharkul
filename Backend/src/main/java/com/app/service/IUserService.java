package com.app.service;

import java.util.List;

import com.app.dto.ChangePasswordDTO;
import com.app.dto.LoginRequestDTO;
import com.app.dto.LoginResponseDTO;
import com.app.dto.RegisterDTO;
import com.app.pojos.Notice;
import com.app.pojos.User;

public interface IUserService {

	// method to login user
	LoginResponseDTO findByEmailAndPassword(String email, String password);

	// method to register owner
	public String registerUser(RegisterDTO user);

	// method to change Password of user
	String changePassword(int id, ChangePasswordDTO req);

	// method for forgot password
	String forgotPassword(LoginRequestDTO req);

	// method to edit-profile
	User editProfile(int id, RegisterDTO registerDto);

	// add a method to fetch all the notices from the database
	List<Notice> fetchAllNotices();

	// method to fetch role using id
	User fetchRole(int id);
	
	//fetchAllUsers;
	List<User> fetchAllUsers();
}
