package com.megift.user.entity;

import com.megift.resources.base.Entity;

public class User extends Entity {

	private String name;
	private String email;
	
	public User(int id) {
		super(id);		
	}
	
	public User(String name, String email){
		super(0);
		this.name = name;
		this.email = email;
	}
	
	@Override
	public boolean isEmpty() {		
		return this.email == null ? true : this.email.isEmpty();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
