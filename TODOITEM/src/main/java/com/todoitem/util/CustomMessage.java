package com.todoitem.util;


public class CustomMessage {
 
	
    private String status;
    
    private String cause;
 
    public CustomMessage(String status, String cause){
        this.status = status;
        this.cause = cause;
    }
 
	public String getStatus() {
        return status;
    }

	public String getCause() {
		return cause;
	}

}
