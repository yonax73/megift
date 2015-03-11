package com.megift.sec.login.entity;

import com.megift.resources.base.Entity;
import com.megift.set.master.entity.MasterValue;

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
public class Login extends Entity {

	public static final int USER_TYPE = 3;
	public static final int BUSINESS_TYPE = 4;
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private String email;
	private transient String password;
	private transient int codeRequest;
	private MasterValue type;

	/**
	 * @param id
	 */
	public Login(int id) {
		super(id);

	}

	public Login(String email, String password) {
		super(0);
		this.email = email;
		this.password = password;
	}

	/**
	 * @param email
	 */
	public Login(String email) {
		super(0);
		this.email = email;
	}

	@Override
	public boolean isEmpty() {
		return email == null || email == "";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the codeRequest
	 */
	public int getCodeRequest() {
		return codeRequest;
	}

	/**
	 * @param codeRequest
	 *            the codeRequest to set
	 */
	public void setCodeRequest(int codeRequest) {
		this.codeRequest = codeRequest;
	}

	public MasterValue getType() {
		return type;
	}

	public void setType(MasterValue type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public boolean isBusinessType() {
		if (type != null)
			return type.getId() == BUSINESS_TYPE;
		else
			return false;
	}

}
