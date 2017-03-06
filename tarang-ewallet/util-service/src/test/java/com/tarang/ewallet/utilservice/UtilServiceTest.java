package com.tarang.ewallet.utilservice;

import java.util.Date;

import org.junit.Test;

import com.tarang.ewallet.util.DateConvertion;



public class UtilServiceTest {
	
	@Test
	public void futureDateTest(){
		Date date = DateConvertion.stirngToDate("2/8/2013");
		int numberOfDays = 20;
		Date fDate = DateConvertion.futureDate(date, numberOfDays);
		System.out.println("given date: " + date);
		System.out.println("future date: " + fDate);
	}
	
	@Test
	public void pastDateTest(){
		Date date = DateConvertion.stirngToDate("2/8/2013");
		int numberOfDays = 20;
		Date fDate = DateConvertion.pastDate(date, numberOfDays);
		System.out.println("given date: " + date);
		System.out.println("future date: " + fDate);
	}

	@Test
	public void getToDateTest(){
		String date = "2/8/2013";
		Date fDate = DateConvertion.getToDateWithEndTime(date);
		System.out.println("given date: " + date);
		System.out.println("future date: " + fDate);
	}

}
