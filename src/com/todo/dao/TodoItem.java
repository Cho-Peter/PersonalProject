package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String category;
    private String desc;
    private String due_date;
    private String current_date;
    private int is_comp;
    private String is_feedback;
    private String day;
    


    public TodoItem(String title, String category, String desc, String due_date, String day){
        this.title=title;
        this.category = category;
        this.desc=desc;
        this.due_date = due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        this.day = day;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    
    
    public int getId(){
    	return id;
    }
	public void setId(int id){
    	this.id = id;
    }
    public int getIs_Comp(){
    	return is_comp;
    }
    public void setIs_Comp(int is_comp){
    	this.is_comp = is_comp;
    }
    
    public String getIs_Feedback() {
    	return is_feedback;
    }
    
    public void setIs_Feedback(String is_feedback) {
    	this.is_feedback = is_feedback;
    }
    
    public String getDay() {
    	return day;
    }
    
    public void setDay(String day) {
    	this.day = day;
    }

    
    @Override
    public String toString() {
    	if(is_comp == 1) {
    		if(is_feedback == null)
    			return  id + ". [" + category + "] "+ title+ "[V]" + " - " + desc + " - " + due_date + "(" + day + ")" + " - " + current_date;
    		else
    			return  id + ". [" + category + "] "+ title+ "[V]" + " - " + desc + " - " + due_date + "(" + day + ")" + " - " + current_date + " - " + is_feedback;
    	}else {
    		return id + ". [" + category + "] "+ title + " - " + desc + " - " + due_date + "(" + day + ")" + " - " + current_date;
    	}
    }
    
    public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##"+ due_date + "##" + current_date + "\n";
    }
}
