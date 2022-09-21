package com.app.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ChangePasswordDTO;
import com.app.dto.ComplaintsDTO;
import com.app.dto.LoginRequestDTO;
import com.app.dto.LoginResponseDTO;
import com.app.dto.RegisterDTO;
import com.app.service.IComplaintService;
import com.app.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserRestController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private IComplaintService complaintService;

	public UserRestController() {
		System.out.println("in ctor of " + getClass().getName());
	}

	// login user : {http://localhost:8080/user/login}
	// postman : passed
	@PostMapping("/login")
	public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO request) {
		System.out.println("Hello" + request);

		return userService.findByEmailAndPassword(request.getEmail(), request.getPassword());
	}

	// change password: {http://localhost:8080/user/change_pass/1}
	// postman : passed
	@PostMapping("/change_pass/{id}")
	public String changePassword(@PathVariable int id, @RequestBody ChangePasswordDTO req) {
		return userService.changePassword(id, req);
	}

	// forgot password : {http://localhost:8080/user/forgot_pass}
	// postman : passed for both valid and invalid one
	@PostMapping("/forgot_pass")
	public String forgotPassword(@RequestBody LoginRequestDTO req) {
		return userService.forgotPassword(req);
	}

	// register user : {http://localhost:8080/user/register}
	// postman : passed
	@PostMapping("/register")
	public String registerUser(@RequestBody RegisterDTO request) {
		System.out.println(request.getEmail());
		return userService.registerUser(request);
	}

	// edit my profile: {http://localhost:8080/user/edit-profile/1}
	// postman : passed
	@PutMapping("/edit-profile/{id}")
	public ResponseEntity<?> editProfile(@PathVariable int id, @RequestBody RegisterDTO dto) {
		System.out.println(id + " and " + dto.getEmail());
		System.out.println("in edit profile" + dto);
		return new ResponseEntity<>(userService.editProfile(id, dto), HttpStatus.OK);
	}

	// display-notices: {http://localhost:8080/user/display-notices}
	@PostMapping("/display-notices")
	public ResponseEntity<?> fetchAllNotices() {
		return new ResponseEntity<>(userService.fetchAllNotices(), HttpStatus.OK);
	}

	// generate complaint: {http://localhost:8080/user/complaint/2}
	// postman: passed
	@PostMapping("/complaint/{id}")
	public ResponseEntity<?> makeComplaint(@PathVariable int id, @RequestBody ComplaintsDTO complaint) {
		System.out.println("id :" + id);
		System.out.println("Complaint : " + complaint);
		return new ResponseEntity<>(complaintService.generateComplaint(id, complaint), HttpStatus.OK);
	}
}
