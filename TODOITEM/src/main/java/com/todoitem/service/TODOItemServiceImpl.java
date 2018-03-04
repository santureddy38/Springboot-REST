package com.todoitem.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.todoitem.model.TODOItem;
 
 
 
@Service("toDoItemService")
public class TODOItemServiceImpl implements TODOItemService{
     
    private static final AtomicLong idCounter = new AtomicLong();
    
    public static final Logger logger = LoggerFactory.getLogger(TODOItemServiceImpl.class);
     
    private static List<TODOItem> todoList;
     
    static{
        todoList= dummyTodoList();
    }
 
    public List<TODOItem> findAllToDoList() {
        return todoList;
    }
     
    public TODOItem findById(long id) {
        for(TODOItem todoItem : todoList){
            if(todoItem.getId() == id){
                return todoItem;
            }
        }
        return null;
    }
     
    public TODOItem findByTitle(String title) {
        for(TODOItem todoItem : todoList){
            if(todoItem.getTitle().equalsIgnoreCase(title)){
                return todoItem;
            }
        }
        return null;
    }
     
    public TODOItem saveToDoItem(TODOItem todoItem) {
    	todoItem.setId(idCounter.incrementAndGet());
        todoList.add(todoItem);
        return todoItem;
    }
 
    public TODOItem updateToDoItem(TODOItem todoItem) {
        int index = todoList.indexOf(todoItem);
        todoList.set(index, todoItem);
        return todoItem;
    }
 
    public boolean isToDoItemExist(TODOItem todoItem) {
        return findByTitle(todoItem.getTitle())!=null;
    }
     
    private static List<TODOItem> dummyTodoList(){
    	
        List<TODOItem> todoList = new ArrayList<TODOItem>();
        todoList.add(new TODOItem(idCounter.incrementAndGet(),"Task1","Creating Spring Boot Application",new GregorianCalendar(2018, 03,10, 02,30).getTime()));
        todoList.add(new TODOItem(idCounter.incrementAndGet(),"Task2","Creating Spring Application",new GregorianCalendar(2018, 03, 06, 17,30).getTime()));
     
        return todoList;
    }
 
}