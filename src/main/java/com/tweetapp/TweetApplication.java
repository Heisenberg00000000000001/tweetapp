package com.tweetapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tweetapp.services.MainMenuService;

/**
 * Hello world!
 *
 */
public class TweetApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(TweetApplication.class);

	public static void main(String[] args) {
		try {
			MainMenuService mainMenuService = new MainMenuService();
			mainMenuService.menu();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
	}
}
