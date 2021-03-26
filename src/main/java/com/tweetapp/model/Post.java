package com.tweetapp.model;

import java.sql.Timestamp;

public class Post {

	private String tweet;
	private String firstName;
	private Timestamp timestamp;

	public Post() {
		super();
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	@Override
	public String toString() {
		return "Post [tweet=" + tweet + ", firstName=" + firstName + ", timestamp=" + timestamp + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}