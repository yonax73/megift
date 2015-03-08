package com.megift.set.master.entity;

import com.megift.resources.base.Entity;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * date : Feb 19, 2015<br/>
 * update date : Feb 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class MasterValue extends Entity {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Master master;
	private String value1;
	private String value2;
	private String value3;

	/**
	 * @param id
	 */
	public MasterValue(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param string
	 */
	public MasterValue(String value1) {
		super(0);
		this.value1 = value1;
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

	public Master getMaster() {
		return master;
	}

	public void setMaster(Master master) {
		this.master = master;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

}
