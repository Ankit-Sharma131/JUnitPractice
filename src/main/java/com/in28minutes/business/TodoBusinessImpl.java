package com.in28minutes.business;

import java.util.ArrayList;
import java.util.List;

import com.in28minutes.data.api.TodoService;

/*
 * Here, this class is SUT - System under test and
 * TodoService is its dependency - its exact implementation is not with us
 * as it may be developed by other team
 */
public class TodoBusinessImpl {
	
	//
	private TodoService todoService;
	
	public TodoBusinessImpl(TodoService todoService) {
		super();
		this.todoService = todoService;
	}
	
	/*Business logic to filter out whatever todos we obtain from TodoService
	  Here, TodoService's retrieveTodos implementation actually call something
	  internally to fetch list of todos. 
	*/
	public List<String> retrieveTodosRelatedToSpring(String user){
		List<String> filteredTodos = new ArrayList<String>();
		List<String> todos = todoService.retrieveTodos(user);
		
		for(String todo : todos) {
			if(todo.contains("Spring")) {
				filteredTodos.add(todo);
			}
		}
		return filteredTodos;
	}
	
	
	public void deleteTodosNotRelatedToString(String user){
		List<String> todos = todoService.retrieveTodos(user);
		
		for(String todo : todos) {
			if(!todo.contains("Spring")) {
				todoService.deleteTodo(todo);
			}
		}
	}
}