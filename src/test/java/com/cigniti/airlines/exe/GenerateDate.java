package com.cigniti.airlines.exe;

import java.util.Calendar;
import java.util.Random;

public class GenerateDate {

	
	public static void main(String[] args) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		year=year-18;
		String yearInString = String.valueOf(year);
		System.out.println(year);
		
		//Generate Date
		Random r = new Random();
		int Low = 1;
		int High = 29;
		int date = r.nextInt(High-Low) + Low;
		System.out.println(date);
		
		//Generate month
		Random rm = new Random();
		int startMonth = 1;
		int endMonth = 12;
		int month = r.nextInt(endMonth-startMonth) + startMonth;
		System.out.println(month);
	}
}
