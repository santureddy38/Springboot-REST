package com.todoitem.controller;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.todoitem.model.TODOItem;
import com.todoitem.service.TODOItemService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=TODOItemController.class, secure=false)
public class ToDoItemControllerTest {
	
	public static final Logger logger = LoggerFactory.getLogger(ToDoItemControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private TODOItemService toDoItemService;
	
	
	
	String todoJson = "{\"title\":\"Task4\",\"description\":\"Learn Microservices\",\"dueDate\":\"2018-06-20 10 30\"}";
	
	@Test
	public void getToDoItem() throws Exception {
		
		TODOItem todo = new TODOItem(1,"Task1","Creating Spring Boot Application",new GregorianCalendar(2018, 03, 05, 02,30).getTime());

		Mockito.when(toDoItemService.findById(Mockito.anyLong())).thenReturn(todo);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/toDoItem/item/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		logger.info("To Do Item:" +result.getResponse());
		
		String expected = "{id:1,title:Task1,description:Creating Spring Boot Application,dueDate:2018-04-05 01 30}";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
		
	}

	@Test
	public void createToDOItem() throws Exception {
		TODOItem todo = new TODOItem(2,"Task3","Creating Spring Boot Application",new GregorianCalendar(2018, 03, 05, 20,30).getTime());

		Mockito.when(toDoItemService.saveToDoItem(Mockito.any(TODOItem.class))).thenReturn(todo);

		// Send To Do as body to post url
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/toDoItem/item/").accept(MediaType.APPLICATION_JSON).content(todoJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());


	}

}
