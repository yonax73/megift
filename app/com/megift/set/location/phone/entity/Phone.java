package com.megift.set.location.phone.entity;

import com.megift.resources.base.Entity;

/**
 * @class : Phone.java<br/>
 * @company : Megift S.A<br/>
 * @user : YQ<br/>
 * @date : Feb 23, 2015<br/>
 * @update date : Feb 23, 2015<br/>
 * @update by : Feb 23, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Phone extends Entity {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private String number;
	private String extension;
	private String mobile;

	/**
	 * @param id
	 */
	public Phone(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param number
	 */
	public Phone(String number) {
		super(0);
		this.number = number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.megift.resources.base.Entity#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
