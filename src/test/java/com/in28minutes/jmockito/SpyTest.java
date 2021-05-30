package com.in28minutes.jmockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.stub;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SpyTest {

	@Test
	public void test() {
		List arrayListMock = mock(ArrayList.class);
		
		/*Reason of Spy existence:
		 * If we apply assert before stubbing method, then method would return
		 * default value and not stub value.
		*/
		assertEquals(0,arrayListMock.size());
		
		//If size() called then return 5
		stub(arrayListMock.size()).toReturn(5);
		
		//Even if we add element to array it would still use mocked values
		arrayListMock.add("try");
		
		assertEquals(5,arrayListMock.size());
		
		/**
		 * Reason: for behavior is that Mocks actually create dummy copy of the list
		 * and use only stubbed behavior to get their expected values. i.e. why size
		 *  was not 6. 
		 *  Where as spy is used to when user wants all functionality of Mock class
		 *  but wants to just override some.
		 */
		List arrayListSpy = spy(ArrayList.class);
		
		assertEquals(0,arrayListSpy.size());
		
		arrayListSpy.add("try");
		
		//Size increased, which wasn't possible in mocks
		//because it is using actual implementation
		assertEquals(1,arrayListSpy.size());

		arrayListSpy.remove("try");
		
		assertEquals(0,arrayListSpy.size());
		
		//Overriding functionality
		stub(arrayListSpy.size()).toReturn(5);
		
		//Prior to stub it was using actual values
		//This happens because here spy would keep all
		//other functionalities as it is but, just override
		//size(). It works like partial mocking of the class.
		assertEquals(5,arrayListSpy.size());
	}

}
