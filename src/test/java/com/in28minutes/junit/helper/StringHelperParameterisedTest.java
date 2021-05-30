package com.in28minutes.junit.helper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StringHelperParameterisedTest {

	private String input;
	private String expectedOutput;
	private StringHelper helper = new StringHelper();
	
	public StringHelperParameterisedTest(String expected, String input) {
		this.expectedOutput = expected;
		this.input = input;
	}
	
	@Parameters
	public static Collection<String[]> prepareTestData(){
		String array[][] = {{"CD","AACD"},{"CD","ACD"}};
		return Arrays.asList(array);
	}
	
	@BeforeClass
	public static void beforeClassMethod() {
		System.out.println("One time before");
	}
	
	@Before
	public void beforeMethod() {
		System.out.println("before exeuted");
	}
	
	@Test
	public void testCase() {
		assertEquals(this.expectedOutput, helper.truncateAInFirst2Positions(input));
	}
	
	@After
	public void afterMethod() {
		System.out.println("After");
	}
	
	@AfterClass
	public static void afterCass() {
		System.out.println("after class method");
	}
}
