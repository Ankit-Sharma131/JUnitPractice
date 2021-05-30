package com.in28minutes.data.api;

import java.util.Arrays;
import java.util.List;

//Stub is a sample implementation of the external service - TodoService
public class TodoServiceStub implements TodoService{
	
	public List<String> retrieveTodos(String user){
		
		if(user.equals("Dummy")) {
			return Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		}else {
			return null;
		}
			
	}

	public void deleteTodo(String str) {
		// TODO Auto-generated method stub
		
	}

}
