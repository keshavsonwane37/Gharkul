package com.app.service;



import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.dto.ChangePasswordDTO;
import com.app.dto.LoginRequestDTO;
import com.app.dto.LoginResponseDTO;
import com.app.dto.RegisterDTO;
import com.app.pojos.Notice;
import com.app.pojos.Role;
import com.app.pojos.User;
import com.app.repository.NoticeRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired(required = true)
	private JavaMailSender mailSend;
	
	@Autowired
	private NoticeRepository noticeRepo;

	@Override
	public LoginResponseDTO findByEmailAndPassword(String email, String password) {
		User u = userRepository.login(email, password);
		System.out.println(u.getRole());
	
		LoginResponseDTO lu = new LoginResponseDTO(u.getId(),u.getFirstName(),u.getLastName(),u.getEmail()
			,u.getMobileNumber(),u.getRole().name(),u.getHouseNumber());
		System.out.println(lu.toString());
		return lu;
	}

	@Override
	public String changePassword(int id, ChangePasswordDTO req) {
		User user = userRepository.findById(id).get();
		if (user.getPassword().equals(req.getOldPassword())) {
			user.setPassword(req.getNewPassword());
			userRepository.save(user);
			return "Password Updated successfully";

		}
		return "Wrong  password : Confirm old password";
	}

	@Override
	public String forgotPassword(LoginRequestDTO req) {
		User u = userRepository.findByEmail(req.getEmail());
		SimpleMailMessage msg = new SimpleMailMessage();
		System.out.println(req.getEmail());
		Random random = new Random();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}
		String randomString = sb.toString();
		String pass = randomString + "#$" + random.nextInt(1000);
		System.out.println(pass);
		if (u != null) {

			msg.setTo(u.getEmail());
			msg.setSubject("Password forgot mail");
			msg.setText("Hey " + u.getFirstName() + " your password is " + pass + " set successfully");
			u.setPassword(pass);
			userRepository.save(u);
			mailSend.send(msg);
			return "password send to ur mail "+pass;
		}
		return "Sorry,Your Email Id is Not Exist!!..";
	}

	@Override
	public String registerUser(RegisterDTO user) {
		System.out.println("in service " + user);
		String message = "";
		User u = userRepository.findByHouseNumber(user.getHouseNumber());
		if (u == null) {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(user.getEmail());
			msg.setSubject("Subject to Gharkul Registration ");
			msg.setText("Hey ," + user.getFirstName() + " Welcome To Gharkul ... Your username :-" + user.getEmail()
					+ " password :- " + user.getPassword() + " .........Thanks for visting Gharkul site ");
			User puser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
					user.getConfirmPassword(), user.getMobileNumber(), Role.OWNER, user.getHouseNumber());
			userRepository.save(puser);
			mailSend.send(msg);
			message = "Owner " + user.getFirstName() + " with House Number " + user.getHouseNumber()
					+ " registered successfully";
		} else
			message = "Registration Failed!! House Number "+user.getHouseNumber()+" already registered";
		return message;
	}

	@Override
	public User editProfile(int id, RegisterDTO registerDto) {
		User user = userRepository.findById(id).get();
		System.out.println("user " + user);
		user.setFirstName(registerDto.getFirstName());
		user.setLastName(registerDto.getLastName());
		user.setEmail(registerDto.getEmail());
		user.setMobileNumber(registerDto.getMobileNumber());
		User result =userRepository.save(user);
		System.out.println("updated data " + result);
		return user;
	}
	
	@Override
	public List<Notice> fetchAllNotices() {
     List<Notice> notice=noticeRepo.findAll();
		Collections.sort(notice, new Comparator<Notice>(){
			public int compare(Notice n1,Notice n2)
			{
				return n2.getUploadTime().compareTo(n1.getUploadTime());
			}	
		});
		notice.forEach(n->{
			if(n.getNoticeExpiryDate().isAfter(LocalDate.now())) {
				n.setFlag(true);
			}
		});
		return notice;
	}

	@Override
	public User fetchRole(int id) {
		return userRepository.findById(id).get();//findby id method returns optional ...get method to return value
				//	userRepository.getById(id): //it will give you lazy init exception 
	}
	
	
	@Override
	public List<User> fetchAllUsers() {
		return userRepository.findAll();
	}
	
}
