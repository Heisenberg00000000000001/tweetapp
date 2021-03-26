package com.tweetapp.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.tweetapp.model.Post;
import com.tweetapp.model.User;
import com.tweetapp.util.Constants;
import com.tweetapp.util.EmailValidationUtil;

public class MainMenuService {
	int count = 1;

	public int getcount() {
		return count++;
	}

	public void menu() throws Exception {
		UserService userService = new UserService();
		TweetService tweetService = new TweetService();
		boolean isLoggedIn = false;
		boolean isActive = true;
		String username = null;
		String email = null;
		User user = new User();
		while (isActive) {
			if (!isLoggedIn) {
				System.out.println("\nMENU \n----");
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Forgot Password");
				System.out.print("\nSelect your option : ");
			} else {
				System.out.println("\nMENU \n----");
				System.out.println("1. Post a tweet");
				System.out.println("2. View My Tweets");
				System.out.println("3. View All Tweets");
				System.out.println("4. View All Users");
				System.out.println("5. Reset Password");
				System.out.println("6. Logout");
				System.out.print("\nSelect your option :  ");
			}
			try {

				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String option = reader.readLine().trim();
				if (!isLoggedIn) {
					switch (option) {
					case "1": {
						boolean isEmailValid = false;
						System.out.println("\n\n\n\nSign Up \n-------");
						System.out.print("Enter firstname : ");
						String fname = reader.readLine().trim();
						System.out.print("Enter lastname  : ");
						String lname = reader.readLine().trim();
						System.out.print("Enter gender    : ");
						String gender = reader.readLine().trim();
						System.out.print("Enter DateOfBirth (" + Constants.DOB_FORMAT + " ) ");
						String dob = reader.readLine().trim();
						System.out.print("Enter email : ");
						email = reader.readLine().trim();
						System.out.print("Enter password : ");
						String pwd = reader.readLine();
						try {
							userService.saveUser(fname, lname, gender, dob, email, pwd, true);
							System.out.println("\nUser details Saved Successfully");
						} catch (Exception e) {
							if (e.getMessage().contains("Duplicate entry")) {
								System.out.println("\nEmail already taken. please use another email");
							} else {
								System.out.println("\n" + e.getMessage());
							}
						}
						break;
					}

					case "2": {
						System.out.print("\n\nEnter email : ");
						email = reader.readLine().trim();
						if (EmailValidationUtil.isValid(email)) {
							System.out.print("Enter password : ");
							String password = reader.readLine();
							try {
								if (userService.validateUser(email, password)) {
									username = email;
									isLoggedIn = true;
									userService.setStatus(isLoggedIn, email);
									System.out.println("\nLogin Successfull");
									user = userService.getUserDetails(email);

								} else {
									System.out.println(
											"\nIncorrect Cretentials. please verify username, password and try again");
								}
							} catch (Exception e) {
								System.out.println("User not found ! Kindly Register before login");
							}
						} else {
							System.out.println("\nIncorrect Email Format. Check email and please try again");
						}

						break;
					}

					case "3": {
						System.out.print("\nEnter email to change the password : ");
						email = reader.readLine().trim();
						if (EmailValidationUtil.isValid(email)) {
							try {
								if (userService.validateEmail(email)) {
									System.out.print("Enter Date of Birth " + Constants.DOB_FORMAT + " : ");
									String dob = reader.readLine().trim();
									if (userService.validateUserWithDob(email, dob)) {
										System.out.print("Enter password : ");
										String password = reader.readLine();
										userService.resetPassword(email, password);
										System.out.println("\nPassword Updated");
									} else {
										System.out.println("\nIncorrect Date of Birth");
									}

								}
							} catch (Exception e) {
								System.out.println("\n" + e.getMessage());
							}
						} else {
							System.out.println("\nIncorrect Email Format. Check email and please try again");
						}

						break;
					}

					default: {
						System.out.println("\nInvalid Option. Please select form the following");
						break;
					}
					}
				} else {
					switch (option) {
					case "1": {
						System.out.print("\nEnter your tweet : ");
						try {
							String tweetMessage = reader.readLine().trim();
							tweetService.saveTweet(tweetMessage, username, user.getFirstName());
							System.out.println("\nTweet posted successfully");
						} catch (Exception e) {
							System.out.println(e.getMessage());

						}
						break;
					}

					case "2": {
						try {
							List<Post> userTweets = tweetService.getUsertweets(username);
							System.out.println("\nYour tweets\n-----------\n");
							userTweets.forEach(tweet -> {
								System.out.println(
										tweet.getFirstName() + "[" + tweet.getTimestamp() + "]\t: " + tweet.getTweet());
							});
						} catch (Exception e) {
							System.out.println("No tweets found under " + username + "....!");
						}

						break;
					}

					case "3": {
						List<Post> userTweets = tweetService.getAlltweets();
						System.out.println("\nAll Tweets\n----------\n");
						userTweets.forEach(tweet -> {
							System.out.println(
									tweet.getFirstName() + "[" + tweet.getTimestamp() + "]\t: " + tweet.getTweet());
						});
						break;
					}

					case "4": {
						System.out.println("\nList of Users\n-------------\n");

						List<String> userList = userService.getAllUsers();
						userList.forEach(name -> {
							System.out.println(getcount() + ". " + name);
						});
						count = 1;
						break;
					}

					case "5": {
						System.out.print("\nEnter the password : ");
						String password = reader.readLine();
						try {
							if (!userService.validateUser(username, password)) {
								userService.resetPassword(username, password);
								System.out.println("\nPassword Updated. please login to continue");
								isLoggedIn = false;
								user = null;
								userService.setStatus(isLoggedIn, username);
							} else {
								System.out.println("\nNew Password cannot be same as old  password");
							}
						} catch (Exception e) {
							System.out.println("Exception!");
						}

						break;
					}

					case "6": {
						username = email;
						isLoggedIn = false;
						user = null;
						userService.setStatus(isLoggedIn, username);
						System.out.println("\nLogged out successfully");

						break;
					}

					default: {
						System.out.println("\nPlease select a valid option from below");
						break;
					}
					}
				}
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}
}
