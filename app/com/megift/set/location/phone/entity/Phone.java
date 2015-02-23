package com.megift.set.location.phone.entity;

import com.megift.resources.base.Entity;

/** 
 * @class        : Phone.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 23, 2015<br/> 
 * @update date  : Feb 23, 2015<br/> 
 * @update by    : Feb 23, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class Phone extends Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String description;
    private String number;
    private boolean main;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    /**
     * @param id
     */
    public Phone(int id) {
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

}
