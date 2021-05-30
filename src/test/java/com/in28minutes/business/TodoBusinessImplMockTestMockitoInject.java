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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.api.TodoServiceStub;

/*
 * This class is copy of TodoBusinessImplMockTest 
 * and aims at using @Mock
 */

//@Mock requires specific runner
//@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessImplMockTestMockitoInject {

	/*
	 * Use of @RunWith is to define what needs to be done before running JUnit
	 * Default implementation is provided by MockitoJUnitRunner.
	 * But what if, we want multiple Runners to run before and after JUnit tests
	 * There comes @Rule. As 1 JUnit can have only one @RunWith only.
	 * Make sure Runners are always public and non-static fields. 
	 */
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	
	//Automatically creates mock instance of the class mentioned
	@Mock
	TodoService todoServiceMock;
	
	//This annotation opens TodoBusinessImpl and check if any @Mocks
	//mentioned here are required by this class. If yes, inject them
	//So no need of this line:
	//TodoBusinessImpl businessImpl = new TodoBusinessImpl(todoServiceMock);
	@InjectMocks
	TodoBusinessImpl businessImpl;

	@Captor
	ArgumentCaptor<String> stringArgCaptor;
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMock() {
		
		//No need for stub class
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		
		//Now define on what condition of above implementation method return what
		//when -> used to stub a method
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		
		//Now as above instance of todoService was mock instance, it would use above when-then condition
		List<String> filteredTodos = businessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMockEmptyList() {
		
		//No need for stub class
		List<String> todos = Arrays.asList();
		
		//Now define on what condition of above implementation method return what
		//when -> used to stub a method
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		
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
		
		//Given
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
		List<String> todos = Arrays.asList("Learn to Rock And Roll","Learn Spring","Learn to Dance");
		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		//When
		businessImpl.deleteTodosNotRelatedToString("Dummy");
		
		//Then
		then(todoServiceMock).should(atLeast(1)).deleteTodo(stringArgCaptor.capture());
		assertThat(stringArgCaptor.getAllValues().size(),is(2));
		
	}
}