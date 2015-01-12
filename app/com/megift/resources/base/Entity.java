package com.megift.resources.base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Entity {

	protected int id;
	protected LocalDateTime created;
	public final static String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm";

	public Entity(int id) {
		this.id = id;
	}
	
	public String getFormatCreated(){
		String formateDate = null;
		if(created!=null)
		formateDate = created.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));
		return formateDate;
	}
	
	public abstract boolean isEmpty();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

}
