package com.todoitem.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TODOItem {
 
    private long id;
    
    @NotBlank(message="Title Can't be empty")
    private String title;
    
    @NotBlank(message="Description Can't be empty")
    private String description;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH mm",timezone="EST")
    private Date dueDate;
 
    public TODOItem(){
        id=0;
    }
     
    public TODOItem(long id, String title, String description, Date dueDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
     
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
    
    public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDtae) {
		this.dueDate = dueDtae;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TODOItem other = (TODOItem) obj;
        if (id != other.id)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "TODOItem [id=" + id + ", title=" + title + ", description=" + description
                + ", due date=" + dueDate + "]";
    }

}
