package com.tweetapp.config;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordConfig {

	public static String encode(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	public static boolean decode(String plainPassword, String hashedPassword) {
		try {
			if (BCrypt.checkpw(plainPassword, hashedPassword))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;

		}
	}

}
