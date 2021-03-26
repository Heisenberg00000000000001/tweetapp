package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tweetapp.config.DbConfig;
import com.tweetapp.model.User;
import com.tweetapp.util.Constants;

public class UserDaoSqlImpl {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public User getUser(String username) throws Exception {
		connection = DbConfig.getConnection();
		User user = new User();
		try {
			preparedStatement = connection.prepareStatement(Constants.GET_USER);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String email = resultSet.getString("us_email");
				String password = resultSet.getString("us_password");
				user.setEmail(email);
				user.setPassword(password);
			} else {
				throw new Exception("Invalid User, kindly register to login");
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Invalid User, kindly register to login");
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
		return user;

	}

	public User getUserDetails(String username) throws Exception {
		connection = DbConfig.getConnection();
		User user = new User();
		try {
			preparedStatement = connection.prepareStatement(Constants.GET_USER_DETAILS);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String email = resultSet.getString("us_email");
				Boolean isAcive = resultSet.getBoolean("us_isactive");
				String firstName = resultSet.getString("us_first_name");
				String lastName = resultSet.getString("us_last_name");
				Date dob = resultSet.getDate("us_dob");
				String gender = resultSet.getString("us_gender");
				user.setEmail(email);
				user.setIsActive(isAcive);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setDob(dob);
				user.setGender(gender);
			} else {
				throw new Exception("No Result found");
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			throw new Exception("No Result found");
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
		return user;

	}

	public User getUserWithDob(String username) throws Exception {
		connection = DbConfig.getConnection();
		User user = new User();
		try {
			preparedStatement = connection.prepareStatement(Constants.GET_USER_WITH_DOB);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String email = resultSet.getString("us_email");
				Date dob = resultSet.getDate("us_dob");
				user.setEmail(email);
				user.setDob(dob);
			} else {
				throw new Exception("Invalid User, kindly register to login");
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Invalid User, kindly register to login");
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
		return user;

	}

	public void saveUser(User user) throws Exception {
		connection = DbConfig.getConnection();
		try {
			preparedStatement = connection.prepareStatement(Constants.SAVE_USER);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getGender());
			preparedStatement.setDate(4, new Date(user.getDob().getTime()));
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getPassword());
			preparedStatement.setBoolean(7, false);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
	}

	public void resetPassword(String email, String password) throws Exception {
		connection = DbConfig.getConnection();
		try {
			preparedStatement = connection.prepareStatement(Constants.RESET_PASSWORD);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, email);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
	}

	public void setStatus(Boolean isActive, String email) throws Exception {
		connection = DbConfig.getConnection();
		try {
			preparedStatement = connection.prepareStatement(Constants.SET_STATUS);
			preparedStatement.setBoolean(1, isActive);
			preparedStatement.setString(2, email);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
	}

	public List<String> getAllUsers() {
		connection = DbConfig.getConnection();
		List<String> names = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(Constants.GET_ALL_USERS);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					String firstname = resultSet.getString("us_first_name");
					names.add(firstname);
				} while (resultSet.next());
			} else {
				System.err.println("There are no users, Register to add users");
			}
			return names;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
		return names;
	}

	public void closedb() throws SQLException {

		if (resultSet != null) {
			resultSet.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}