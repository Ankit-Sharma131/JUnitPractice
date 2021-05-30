package com.in28minutes.business;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

// List Class mocking
public class ListTest {

	@Test
	public void testMockListSizeMethod() {
		
		List listMock = mock(List.class);
		
		//Dynamic Stubing
		when(listMock.size()).thenReturn(2);
		
		assertEquals(2, listMock.size());
	}
	
	/*
	 * Mocking multiple calls to same method
	 */
	@Test
	public void mockListSize_ReturnMultipleValues() {
		
		List listMock = mock(List.class);
		
		when(listMock.size()).thenReturn(2).thenReturn(3);
		
		assertEquals(2,listMock.size());
		assertEquals(3, listMock.size());
	}
	
	/*
	 * Calling method with case which is not mocked
	 * - default mock value - primitive or null of Object type
	 */
	@Test
	public void mockListGet_DefaultValues() {
		
		List listMock = mock(List.class);
		
		when(listMock.get(0)).thenReturn("test 0");
		
		assertEquals("test 0",listMock.get(0));
		assertEquals(null, listMock.get(1));
	}
	
	/*
	 * Argument Matcher - anyInt, anyString, anySetOf, anyObject ... etc
	 */
	@Test
	public void mockListGet_ArgMatcher() {
		
		List listMock = mock(List.class);
		
		//Argument Matcher
		when(listMock.get(anyInt())).thenReturn("test");
		
		assertEquals("test",listMock.get(0));
		assertEquals("test", listMock.get(1));
	}
	
	/*
	 * throwing exception on specific case
	 */
	@Test(expected=RuntimeException.class)
	public void mockListGet_throwException() {
		
		List listMock = mock(List.class);
		
		//Argument Matcher
		when(listMock.get(anyInt())).thenThrow(new RuntimeException("Something is wrong!"));
		
		listMock.get(0);
	}

	/*
	 * Arg Matcher with specific value don't work as expected
	 * Either they should be specific or all arg matcher
	 */
	@Test(expected=RuntimeException.class)
	public void mockListSubList_MultiArgThrowException() {
		
		List listMock = mock(List.class);
		
		//Argument Matcher
		when(listMock.subList(anyInt(),7)).thenThrow(new RuntimeException("Something is wrong!"));
		
		//Pass
		listMock.subList(0,7);
		
		//Should fail test as mock should only work for 7, but don't
		listMock.subList(0,5);
	}
	
	/**
	 * BDD - Business Data Driven. 
	 * As most of the tasks in Agile world are in story format,
	 * they are in format - Given, when and then
	 * Unit Test Cases in BDD have just a small difference in syntax
	 */
	@Test
	public void testMockListSizeMethod_inBDDStyle() {
		
		//Given - includes
		//pre-initialization steps + condition in BDD format
		List listMock = mock(List.class);
		given(listMock.size()).willReturn(2);
		
		//When
		int listSize = listMock.size();
		
		//then
		assertThat(2, is(listSize));
	}
}