package com.in28minutes.powermock;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/*
 * This class is going to use powermocks to
 * mock static methods
 */

/*When want to combine Mockito and PowerMock,
 *Require specific Runner - PowerMockRunner
 *For preparation, need to mention class with static methods to be mocked
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilityClass.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*","javax.xml.*","org.xml.*","org.w3c.*"})
public class MockingStaticMethod {

	@Mock
	Dependency dependency;
	
	@InjectMocks
	SystemUnderTest systemUnderTest; 

	@Test(expected=RuntimeException.class)
	public void testMethodCallingStaticMethod_SimpleTest() {
		
		List<Integer> todos = Arrays.asList(1,2,3);
		
		when(dependency.retrieveAllStats()).thenReturn(todos);
		
		//Below is not allowed with Mockito
		//when(UtilityClass.staticMethod(6)).thenReturn(150);		

		systemUnderTest.methodCallingAStaticMethod();
	}
	
	@Test
	public void testMethodCallingStaticMethod_WithPowerMock() {
		
		List<Integer> todos = Arrays.asList(1,2,3);
		
		when(dependency.retrieveAllStats()).thenReturn(todos);
		
		//This tells which class to mock that has static methods
		PowerMockito.mockStatic(UtilityClass.class);
		
		//because of above line, now we can mock
		when(UtilityClass.staticMethod(6)).thenReturn(150);		

		systemUnderTest.methodCallingAStaticMethod();
	}
}