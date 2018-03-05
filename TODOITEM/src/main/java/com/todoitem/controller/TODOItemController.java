package com.todoitem.controller;
 
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todoitem.model.TODOItem;
import com.todoitem.service.TODOItemService;
import com.todoitem.util.CustomMessage;

 
@RestController
@RequestMapping("/toDoItem")
public class TODOItemController {
 
    public static final Logger logger = LoggerFactory.getLogger(TODOItemController.class);
 
    @Autowired
    TODOItemService toDoItemService; //Service to retrieve/manipulate to do item
 
   /*
    * To retrieve all To DO Items
    */
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/item/", method = RequestMethod.GET)
    public ResponseEntity<List<TODOItem>> listAllToDoItems() {
        List<TODOItem> toDoList = toDoItemService.findAllToDoList();
        if (toDoList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
           
        }
        return new ResponseEntity<List<TODOItem>>(toDoList, HttpStatus.OK);
    }
 
   /*
    * Retrieve Single To Do Item
    */
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getToDoItem(@PathVariable("id") long id) {
        logger.info("Fetching To DO item with id", id);
        TODOItem toDoItem = toDoItemService.findById(id);
        if (toDoItem == null) {
            logger.error("To Do Item with id not found.", id);
            return new ResponseEntity(new CustomMessage("Request Failed","To DO Item with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TODOItem>(toDoItem, HttpStatus.OK);
    }
 
    /*
     * Creating a To DO Item
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/item/", method = RequestMethod.POST)
    public ResponseEntity<?> createToDoItem(@Valid @RequestBody TODOItem toDoItem, BindingResult result) {
        
    	if(result.hasErrors()){
    		
    		List<FieldError> errors = result.getFieldErrors();
            List<String> errorMessage = new ArrayList<>();
            for (FieldError e : errors){
            	errorMessage.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
            }
            return new ResponseEntity(new CustomMessage("Update Failed",errorMessage.toString()),HttpStatus.BAD_REQUEST);
    	}
    	logger.info("Creating To DO Item : ", toDoItem);
 
        if (toDoItemService.isToDoItemExist(toDoItem)) {
            logger.error("Unable to create. A To DO Item with title already exist", toDoItem.getTitle());
            return new ResponseEntity(new CustomMessage("Update Failed","Unable to create. A To DO Item with title " + 
            toDoItem.getTitle() + " already exist."),HttpStatus.CONFLICT);
        }
        toDoItemService.saveToDoItem(toDoItem);
 
        return new ResponseEntity(new CustomMessage("Success","To DO Item created successfully with " + toDoItem.getId() ), HttpStatus.CREATED);
    }
 
    
    /*
     * Update a Item
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/item/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchToDoItem(@PathVariable("id") long id, @RequestBody TODOItem toDoItem, BindingResult result) {
        
    	if(result.hasErrors()){
    		
    		List<FieldError> errors = result.getFieldErrors();
            List<String> errorMessage = new ArrayList<>();
            for (FieldError e : errors){
            	errorMessage.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
            }
            return new ResponseEntity(new CustomMessage("Update Failed",errorMessage.toString()),HttpStatus.BAD_REQUEST);
    	}
    	logger.info("Updating To Do Item id data", id);
 
        TODOItem currentToDoItem = toDoItemService.findById(id);
 
        if (currentToDoItem == null) {
            logger.error("Unable to update. To Do Item with id not found.", id);
            return new ResponseEntity(new CustomMessage("Update Failed","Unable to upate. To DO Item  with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
         if(toDoItem.getTitle() != null){
        	currentToDoItem.setTitle(toDoItem.getTitle());
        }
        if(toDoItem.getDescription()!= null){
        	 currentToDoItem.setDescription(toDoItem.getDescription());
        }
        if(toDoItem.getDueDate() != null) {
        	currentToDoItem.setDueDate(toDoItem.getDueDate());
        }
 
        toDoItemService.updateToDoItem(currentToDoItem);
        
        return new ResponseEntity<TODOItem>(currentToDoItem, HttpStatus.OK);
    }
 
}
