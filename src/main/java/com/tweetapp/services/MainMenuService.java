package com.tweetapp.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class MainMenuService {
	public void menu() throws Exception {
		boolean appRunning = true;
		boolean isLoggedIn = false;
		while (appRunning) {
			System.out.println("\nMENU \n---- ");
			if (!isLoggedIn) {
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Forgot Password");
				System.out.println("\nSelect your choice : ");
			} else {
				System.out.println("4. Post a tweet");
				System.out.println("5. View My Tweets");
				System.out.println("6. View All Tweets");
				System.out.println("7. View All Users");
				System.out.println("8. Reset Password");
				System.out.println("9. Logout");
				System.out.println("Select your choice from above or Press any other key to close the TweetApp.");
			}
			 Scanner scanner = new Scanner(System.in); 
			    System.out.println("Enter username");

			    String userName = myObj.nextLine();  // 
			
		}
		// UserService userService = new UserService();
		// TweetService tweetService = new TweetService();

		String username = null;
		String email = null;

		while (isexit) {
			System.out.println("\nMENU \n---- ");
			if (!islogin) {
				System.out.println("1. Login");
				System.out.println("2. Register");
				System.out.println("3. Forgot Password");
			} else {
				System.out.println("4. Post a tweet");
				System.out.println("5. View My Tweets");
				System.out.println("6. View All Tweets");
				System.out.println("7. View All Users");
				System.out.println("8. Reset Password");
				System.out.println("9. Logout");
				System.out.println("Press any other key to close the TweetApp.");
			}
			BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
			String choice;
			try {
				choice = sc.readLine().trim();
				switch (choice) {
				case "1": {
					if (islogin) {
						System.err.println("User already Logged in...");
					} else if (!islogin) {
						System.out.println("Enter email");
						email = sc.readLine().trim();
						if (EmailUtil.emailValidate(email)) {

							System.out.println("Enter password");
							String password = sc.readLine();

							try {
								if (userService.validateUser(email, password)) {
									username = email;
									islogin = true;
									userService.setStatus(islogin, email);
									System.out.println("login successfull");
								} else {
									System.err.println("incorrect credentials");
								}
							} catch (Exception e) {
								System.err.println("User not found ! Kindly Register before login");
							}
						} else {
							System.err.println("Incorrect Email Format");
						}
					}

					break;
				}

				case "2": {
					if (!islogin) {
						System.out.println("New User Registration form...");
						System.out.println("Enter firstname");
						String fname = sc.readLine().trim();
						System.out.println("Enter lastname");
						String lname = sc.readLine().trim();
						System.out.println("Enter gender (Male/Female)");
						String gender = sc.readLine().trim();
						System.out.println("Enter DateOfBirth " + BatchConstants.DOB_FORMAT);
						String dob = sc.readLine().trim();
						System.out.println("Enter email");
						email = sc.readLine().trim();
						System.out.println("Enter password");
						String pwd = sc.readLine();
						try {
							userService.saveUser(fname, lname, gender, dob, email, pwd, false);
							System.out.println("User details Saved Successfully");
						} catch (Exception e) {
							System.err.println("email already exists.. select choice for login");
						}
					} else if (islogin) {
						System.err.println("user already logged in...");
					}
					break;
				}

				case "3": {
					if (!islogin) {
						System.out.println("Enter email to change the password");
						email = sc.readLine().trim();
						try {
							if (userService.validateEmail(email)) {
								System.out.println("Email is validated");
								System.out.println("Enter password");
								String password = sc.readLine();
								userService.resetPassword(email, password);
								System.out.println("Password Updated");
							}
						} catch (Exception e) {
							System.err.println("Enter a valid email");
						}
					} else if (islogin) {
						System.err.println("Logout to change password");
					}
					break;
				}
				case "4": {
					if (!islogin) {
						System.err.println("Please login first to post a tweet");
					} else if (islogin) {
						System.out.println("Enter tweet message...!");
						try {
							String tweetMessage = sc.readLine().trim();
							tweetService.saveTweet(tweetMessage, username);
							System.out.println("Message saved successfully.....!");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				}

				case "5": {
					if (!islogin) {
						System.err.println("Please login first to get your Tweets");
					} else if (islogin) {
						try {
							List<Post> userTweets = tweetService.getUsertweets(username);
							System.out.println("Your tweets are : ");
							userTweets.forEach(tweet -> {
								System.out.println(tweet.getTweet());
							});
						} catch (Exception e) {
							System.err.println("No tweets found under " + username + "....!");
						}
					}
					break;
				}

				case "6": {
					List<Post> userTweets = tweetService.getAlltweets();
					System.out.println(" Tweets : ");
					userTweets.forEach(tweet -> {
						System.out.println(tweet.getTweet());
					});
					break;
				}

				case "7": {
					System.out.println("Users List: ");
					List<String> userList = userService.getAllUsers();
					userList.forEach(name -> {
						System.out.println(name);
					});
					break;
				}

				case "8": {
					System.out.println("Enter the password");
					String password = sc.readLine();
					try {
						if (!userService.validateUser(username, password)) {
							userService.resetPassword(username, password);
							System.out.println("Password Updated");
						} else {
							System.err.println("New Password cannot be same as old password");
						}
					} catch (Exception e) {
						System.err.println("Exception!");
					}

					break;
				}

				case "9": {
					if (!islogin) {
						System.err.println("Please login first...");
					} else if (islogin) {
						username = email;
						islogin = false;
						userService.setStatus(islogin, username);
						System.out.println("Logged out successfully");
					}
					break;
				}

				default: {
					isexit = false;
					sc.close();
					break;
				}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
