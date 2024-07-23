package com.testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LeapYearTesting {
	
	static TestingApp app;
	
	@BeforeClass
	public static void beforeClass() {
		app=new TestingApp();
	}
	
	@Before
	public void setUp() {
		app.memeoryClear();
	}
	

	@Test
	public void test() {
		long currentTimeMillis = System.currentTimeMillis();
		
		 long days = TestingApp.epochTime(currentTimeMillis);
		  
		 long currentYear = TestingApp.leapYear(days);
		 
		 System.out.println("test ---->"+currentYear);
		 
		 assertEquals(2024,currentYear);
	}

	@Test
	public void test1() {
		
		long currentTimeMillis = System.currentTimeMillis();
		
		 long days = TestingApp.epochTime(currentTimeMillis);
		  
		 long currentYear = TestingApp.leapYear(days);
		 
		 System.out.println("test1 ---->"+currentYear);
		 
		 assertTrue(currentYear==2024);
		
	}
	
	@After
	public void reset() {
		app.reset();
	}
	
	@AfterClass
	public static void tearDownClass() {
		app=null;
	}
	
	
}
