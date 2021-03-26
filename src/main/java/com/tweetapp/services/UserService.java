package com.tweetapp.services;

import java.util.List;

import com.tweetapp.config.PasswordConfig;
import com.tweetapp.dao.UserDaoSqlImpl;
import com.tweetapp.model.User;
import com.tweetapp.util.DateUtil;
import com.tweetapp.util.EmailValidationUtil;

public class UserService {

	UserDaoSqlImpl repo = new UserDaoSqlImpl();

	public boolean validateUser(String email, String password) throws Exception {
		User existedUser = repo.getUser(email);
		boolean userExist = false;
		if (existedUser != null) {
			userExist = PasswordConfig.decode(password, existedUser.getPassword());
		}
		return userExist;
	}

	public User getUserDetails(String email) throws Exception {
		User existedUser = repo.getUserDetails(email);
		if (existedUser != null) {
			return existedUser;
		} else {
			return null;
		}

	}

	public boolean validateUserWithDob(String email, String dob) throws Exception {
		User existedUser = repo.getUserWithDob(email);
		boolean userExist = false;
		if (existedUser != null && DateUtil.convertToDate(dob).equals(existedUser.getDob())) {
			userExist = true;
		}
		return userExist;
	}

	public boolean validateEmail(String email) throws Exception {
		User existedUser = repo.getUser(email);
		if (existedUser != null) {
			if (existedUser.getEmail().equals(email)) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new Exception("User not found with the email");
		}
	}

	public void saveUser(String firstName, String lastName, String gender, String dob, String email, String pwd,
			Boolean isActive) throws Exception {
		User newUser = new User(firstName, lastName, gender, DateUtil.convertToDate(dob), email,
				PasswordConfig.encode(pwd), isActive);
		if (firstName.isEmpty()) {
			throw new Exception("First Name cannot be null");
		}
		if (gender.isEmpty()) {
			throw new Exception("Gender cannot be null");
		}
		if (email.isEmpty()) {
			throw new Exception("Email cannot be null");
		}
		if (pwd.isEmpty()) {
			throw new Exception("Password cannot be null");
		}
		if (!EmailValidationUtil.isValid(email)) {
			throw new Exception("Email is invalid. please use proper email address");
		}
		repo.saveUser(newUser);
	}

	public void resetPassword(String email, String password) throws Exception {
		repo.resetPassword(email, PasswordConfig.encode(password));
	}

	public void setStatus(Boolean isActive, String email) throws Exception {
		repo.setStatus(isActive, email);
	}

	public List<String> getAllUsers() {
		return repo.getAllUsers();
	}
}