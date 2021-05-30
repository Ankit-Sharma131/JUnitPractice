package com.in28minutes.business;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.api.TodoServiceStub;

public class TodoBusinessImplStubTest {

	TodoService todoService = new TodoServiceStub();
	TodoBusinessImpl businessImpl = new TodoBusinessImpl(todoService); 
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingStub() {		
		List<String> filteredTodos = businessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingStub1() {		
		List<String> filteredTodos = businessImpl.retrieveTodosRelatedToSpring("Dummy1");
		assertEquals(0, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_exact2ArrayElements() {
		List<String> filteredTodos = businessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertTrue(filteredTodos.contains("Learn Spring"));
		assertTrue(filteredTodos.contains("Learn Spring MVC"));
	}
}

/*Notes:
Issues with JUnit stubs are:
	1) Dynamic condition - If retrieveTodosRelatedToSpring() method changes,
	 all its related test methods have to be changed as well
	2) Service Definitions - If TodoService interface adds or changes any method, 
	then we have to propogate that change as well.
To reduce these disadvantages of stubs, mockitos were introduced.
*/