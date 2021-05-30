package com.in28minutes.business;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.api.TodoServiceStub;

/*
 * This class aims at using Mocks instead of Stubs to test Business class
 */
public class TodoBusinessImplMockTest {

	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMock() {
		
		//Here, mock() takes a implementation and creates its mock with every method return 
		//Empty return like empty list etc.
		TodoService todoServiceMock = mock(TodoService.class);
		
		//No need for stub class
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		
		//Now define on what condition of above implementation method return what
		//when -> used to stub a method
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		
		TodoBusinessImpl businessImpl = new TodoBusinessImpl(todoServiceMock);
		
		//Now as above instance of todoService was mock instance, it would use above whenthen condition
		List<String> filteredTodos = businessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMockEmptyList() {
		
		//Here, mock() takes a implementation and creates its mock with every method return 
		//Empty return like empty list etc.
		TodoService todoServiceMock = mock(TodoService.class);
		
		//No need for stub class
		List<String> todos = Arrays.asList();
		
		//Now define on what condition of above implementation method return what
		//when -> used to stub a method
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		
		TodoBusinessImpl businessImpl = new TodoBusinessImpl(todoServiceMock);
		
		//Now as above instance of todoService was mock instance, it would use above whenthen condition
		List<String> filteredTodos = businessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(0, filteredTodos.size());
	}
	
	/**
	 * Test uses - verify() to know if method is called on when
	 * or not, and if yes, how many times
	 */
	@Test
	public void testDeleteTodosNotRelatedToSpring_VerifyIfMethodCalledInBDDFormat() {
		
		//Given
		TodoService todoServiceMock = mock(TodoService.class);
		TodoBusinessImpl businessImpl = new TodoBusinessImpl(todoServiceMock);
		List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");
		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		//When
		businessImpl.deleteTodosNotRelatedToString("Dummy");
		
		//Then
		
		// --------------  Normal then -------------
		//To verify if deleteTodo() of TodoService is called on these specific arguments
		//above when
		verify(todoServiceMock).deleteTodo("Learn to Dance");
		
		//this will fail
		//verify(todoServiceMock).deleteTodo("Learn Spring MVC");
		
		//To verify this specific is never called
		verify(todoServiceMock,never()).deleteTodo("Learn Spring MVC");
		
		//To verify number of times that method is called
		verify(todoServiceMock,times(1)).deleteTodo("Learn to Dance");
		
		//To verify at least number of times that method is called
		verify(todoServiceMock,atLeast(1)).deleteTodo("Learn to Dance");
		
		// -------------- BDD style then -------------
		then(todoServiceMock).should().deleteTodo("Learn to Dance");
		
		then(todoServiceMock).should(never()).deleteTodo("Learn Spring");
	}
	
	
	/**
	 * Use-Case : Check what argument was used to call a mocked method
	 */
	@Test
	public void testDeleteTodosNotRelatedToSpring_ArgumentCapture() {
		
		//Here, we define Argument captor object of argument type 
		ArgumentCaptor<String> stringArgCaptor = ArgumentCaptor.forClass(String.class);
		
		//Given
		TodoService todoServiceMock = mock(TodoService.class);
		TodoBusinessImpl businessImpl = new TodoBusinessImpl(todoServiceMock);
		List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");
		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		//When
		businessImpl.deleteTodosNotRelatedToString("Dummy");
		
		//Then
		then(todoServiceMock).should().deleteTodo(stringArgCaptor.capture());
		assertThat(stringArgCaptor.getValue(),is("Learn to Dance"));
		
	}

	
	/**
	 * Use-Case : Check what arguments were used to call a mocked method multiple times
	 */
	@Test
	public void testDeleteTodosNotRelatedToSpring_ArgumentCaptureMultiTimes() {
		
		//Here, we define Argument captor object of argument type 
		ArgumentCaptor<String> stringArgCaptor = ArgumentCaptor.forClass(String.class);
		
		//Given
		TodoService todoServiceMock = mock(TodoService.class);
		TodoBusinessImpl businessImpl = new TodoBusinessImpl(todoServiceMock);
		List<String> todos = Arrays.asList("Learn to Rock And Roll","Learn Spring","Learn to Dance");
		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		//When
		businessImpl.deleteTodosNotRelatedToString("Dummy");
		
		//Then
		then(todoServiceMock).should(atLeast(1)).deleteTodo(stringArgCaptor.capture());
		assertThat(stringArgCaptor.getAllValues().size(),is(2));
		
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