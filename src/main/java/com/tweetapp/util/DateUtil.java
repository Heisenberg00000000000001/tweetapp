package com.tweetapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date convertToDate(String date) throws Exception {
		Date parsedDate = null;
		try {
			SimpleDateFormat dateFormate = new SimpleDateFormat(Constants.DOB_FORMAT);
			dateFormate.setLenient(false);
			parsedDate = dateFormate.parse(date);
		} catch (ParseException parseException) {
			throw new Exception("Invalid Date format. Kindly enter the Date of Birth in the following format "
					+ Constants.DOB_FORMAT);
		}
		return parsedDate;
	}
}