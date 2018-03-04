package com.todoitem.service;

import java.util.List;

import com.todoitem.model.TODOItem;


 
public interface TODOItemService {
     
	TODOItem findById(long id);
     
	TODOItem findByTitle(String title);
     
	TODOItem saveToDoItem(TODOItem toDoItem);
     
	TODOItem updateToDoItem(TODOItem toDoItem);
     
    List<TODOItem> findAllToDoList();
     
    boolean isToDoItemExist(TODOItem toDoItem);
     
}
