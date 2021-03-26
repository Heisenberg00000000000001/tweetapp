package com.tweetapp.util;

public class Constants {

	public static final String DOB_FORMAT = "yyyy/MM/dd";
	public static final String GET_ALL_TWEETS = "SELECT message,us_firstname,date_of_post FROM post ";
	public static final String GET_USER_TWEETS = "SELECT message,us_firstname,date_of_post FROM post WHERE us_email = ? ";
	public static final String SAVE_TWEET = "INSERT INTO post(message,us_email,us_firstname)values(?,?,?)";
	public static final String GET_ALL_USERS = "SELECT us_first_name FROM user ";
	public static final String SAVE_USER = "INSERT INTO user (`us_first_name`, `us_last_name`, `us_gender`, `us_dob`, `us_email`, `us_password`, `us_isactive`)  VALUES (?,?,?,?,?,?,?)";
	public static final String GET_USER = "SELECT us_email, us_password from user WHERE us_email = ?";
	public static final String GET_USER_WITH_DOB = "SELECT us_email, us_dob from user WHERE us_email = ?";
	public static final String GET_USER_DETAILS = "SELECT us_first_name,us_last_name,us_gender,us_dob,us_email,us_isactive from user WHERE us_email = ?";
	public static final String GET_STATUS = "SELECT us_isactive from user WHERE us_email = ?";
	public static final String SET_STATUS = "UPDATE user SET us_isactive = ? WHERE us_email = ?";
	public static final String RESET_PASSWORD = "UPDATE user SET us_password = ? WHERE us_email = ?";

}
