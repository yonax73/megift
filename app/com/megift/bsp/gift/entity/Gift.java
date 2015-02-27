package com.megift.bsp.gift.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.term_and_condition.TermAndCondition;
import com.megift.resources.base.Entity;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.picture.entity.Picture;

/** 
 * @class        : Gift.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 26, 2015<br/> 
 * @update date  : Feb 26, 2015<br/> 
 * @update by    : Feb 26, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class Gift extends Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Action action;
    private MasterValue type;
    private String otherType;
    private double price;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String name;
    private String description;
    private List<Picture> pictures;
    private List<TermAndCondition> termsAndConditions;

    /**
     * @param id
     */
    public Gift(int id) {
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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public MasterValue getType() {
        return type;
    }

    public void setType(MasterValue type) {
        this.type = type;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
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

    /**
     * @return the pictures
     */
    public List<Picture> getPictures() {
        return pictures;
    }

    /**
     * @param pictures the pictures to set
     */
    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    /**
     * @return the termsAndConditions
     */
    public List<TermAndCondition> getTermsAndConditions() {
        return termsAndConditions;
    }

    /**
     * @param termsAndConditions
     *            the termsAndConditions to set
     */
    public void setTermsAndConditions(List<TermAndCondition> termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }


}