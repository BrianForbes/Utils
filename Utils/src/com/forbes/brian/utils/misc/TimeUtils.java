package com.forbes.brian.utils.misc;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {
	
	public static int MIN_YEAR = 1970;
	public static int MAX_YEAR = 2050;
	
	public static Timestamp getTimestamp(int year,int month,int day) throws Exception{
		if(year < MIN_YEAR || year > MAX_YEAR)
			throw new IllegalArgumentException("Year out of range");
		if(month < 1 || month > 12)
			throw new IllegalArgumentException("Month out of range");
		if(day < 1 || day > 31)
			throw new IllegalArgumentException("Day out of range");
		String sDay = Integer.toString(day);
		if(sDay.length() == 1)
			sDay = "0" + sDay;
		String sDate = Integer.toString(year) + "- " + Integer.toString(month) + "- " +sDay;
		return getTimestamp(sDate);
	}
	
	public final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

	public static int dayDiff(Date from, Date to) {
	    return (int)((to.getTime() - from.getTime()) / MILLISECONDS_IN_DAY);
	}

	
	public static Timestamp fromYYYYmmDD(String dateStr) throws ParseException{
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyyMMdd");
		java.util.Date date0 = formatter.parse(dateStr);
		return new Timestamp((new java.sql.Date(date0.getTime())).getTime());
	}
	
	
	public static Timestamp getTimestamp(String dateStr) throws ParseException{
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date0 = formatter.parse(dateStr);
		return new Timestamp((new java.sql.Date(date0.getTime())).getTime());
	}
	
	public static int getMonth(Timestamp ts){
		return getAttribute(ts,Calendar.MONTH) + 1;
	}
	public static int getYear(Timestamp ts){
		return getAttribute(ts,Calendar.YEAR);
	}
	public static int getDayOfMonth(Timestamp ts){
		return getAttribute(ts,Calendar.DAY_OF_MONTH);
	}
	public static int getDayOfYear(Timestamp ts){
		return getAttribute(ts,Calendar.DAY_OF_YEAR);
	}
	private static int getAttribute(Timestamp ts,int att){
		long timestamp = ts.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		return cal.get(att);
	}
	
	public static Timestamp addDays(Timestamp ts,int days){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ts.getTime());
		cal.add(Calendar.DAY_OF_WEEK,days);
		Timestamp ts2 = new Timestamp(cal.getTimeInMillis());
		return ts2;
	}


	

	
}
