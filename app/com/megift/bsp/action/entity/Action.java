package com.megift.bsp.action.entity;

import java.util.List;

import com.megift.resources.base.Entity;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.picture.entity.Picture;

/** 
 * @class        : Action.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 26, 2015<br/> 
 * @update date  : Feb 26, 2015<br/> 
 * @update by    : Feb 26, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class Action extends Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private MasterValue type;
    private String name;
    private String description;
    private double price;
    private List<Picture> pictures;

    /**
     * @param id
     */
    public Action(int id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public MasterValue getType() {
        return type;
    }

    public void setType(MasterValue type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
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
