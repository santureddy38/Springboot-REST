package com.todoitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.todoitem"})
public class ToDoItemRestApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(ToDoItemRestApplication.class, args);
    }

}
