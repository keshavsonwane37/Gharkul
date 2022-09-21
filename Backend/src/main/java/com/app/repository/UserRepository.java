package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.pojos.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer >{
	//custom query to login 
	@Query("select u from User u  where u.email=:em and u.password=:pass")
	User login(@Param("em") String email, @Param("pass") String password);
	User findByEmail(String email);
	
	//custom query to fetch all house owners
	@Query("select u.id from User u where u.role='OWNER'")
	List<Integer> getAllIds();
	
	//Fetch User corresponding to given houseNumber
	User findByHouseNumber(String houseNumber);
	
	@Query("select u from User u where u.role='OWNER'")
	List<User> findAllOwners();
	
}