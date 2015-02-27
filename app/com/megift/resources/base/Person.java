package com.megift.resources.base;

import com.megift.set.document.entity.Document;
import com.megift.sec.login.entity.Login;
import com.megift.set.location.entity.Location;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.picture.entity.Picture;

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
public abstract class Person extends Entity {

	public final static int FEMALE_GENDER = 1;
	public final static int MALE_GENDER = 2;
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	protected String name;
	protected String lastName;
	protected transient Login login;
	protected Picture picture;
    protected MasterValue gender;
    protected Location location;
    protected Document document;

	/**
	 * @param id
	 */
	public Person(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.megift.resources.base.Entity#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return name == null || name == "";
	}

	public String getFullName() {
		if (name != null && lastName != null) {
			return name.concat(" ").concat(lastName);
		}
		return name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public MasterValue getGender() {
		return gender;
	}

	public void setGender(MasterValue gender) {
		this.gender = gender;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
