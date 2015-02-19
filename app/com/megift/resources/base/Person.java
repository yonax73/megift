package com.megift.resources.base;

import com.megift.sec.login.entity.Login;
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
    protected String document;
    protected String name;
    protected String lastName;
    protected transient Login login;
    protected Picture picture;
    /**
     * @param id
     */
    public Person(int id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.megift.resources.base.Entity#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    public String getFullName() {
        return name.concat(" ").concat(lastName);
    }

    protected String getDocument() {
        return document;
    }

    protected void setDocument(String document) {
        this.document = document;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected Login getLogin() {
        return login;
    }

    protected void setLogin(Login login) {
        this.login = login;
    }

    protected Picture getPicture() {
        return picture;
    }

    protected void setPicture(Picture picture) {
        this.picture = picture;
    }

}
