package com.java.covid.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse("2021-01-15");
		System.out.println(start);
		Date date=getMeYesterday(start);
		System.out.println(date);
		System.out.println(sdf.format(date));
	}
	
	private static Date getMeYesterday(Date start){
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(start);
	    calendar.add(Calendar.DAY_OF_YEAR, -1);
	    Date previousDate = calendar.getTime();
	    System.out.println("prev : "+previousDate);
	     return new Date(System.currentTimeMillis()-24*60*60*1000);
	}
}