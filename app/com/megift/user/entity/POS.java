package com.megift.user.entity;

import com.megift.resources.base.Entity;

public class POS extends Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
	private String email;
	
	public POS(int id) {
		super(id);		
	}
	
	public POS(String name, String email){
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
