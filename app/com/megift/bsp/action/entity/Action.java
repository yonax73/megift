package com.megift.bsp.action.entity;

import java.util.List;

import com.megift.resources.base.Entity;
import com.megift.resources.utils.Utils;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.picture.entity.Picture;

/**
 * @class : Action.java<br/>
 * @company : Megift S.A<br/>
 * @user : YQ<br/>
 * @date : Feb 26, 2015<br/>
 * @update date : Feb 26, 2015<br/>
 * @update by : Feb 26, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Action extends Entity {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	public static final int OTHER_TYPE = 14;
	private MasterValue type;
	private String otherType;
	private String name;
	private String description;
	private double price;
	private List<Picture> pictures;
	private Picture mainPicture;
	private int position;

	/**
	 * @param id
	 */
	public Action(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param name
	 */
	public Action(int id, String name) {
		super(id);
		this.name = name;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.megift.resources.base.Entity#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return name == null;
	}

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	/**
	 * @return
	 */
	public boolean isOtherType() {
		if (type != null) {
			return type.getId() == OTHER_TYPE;
		}
		return false;
	}

	public String getPriceFormatted() {
		return Utils.priceWithoutDecimal(price, "$");
	}

	/**
	 * @return the mainPicture
	 */
	public Picture getMainPicture() {
		return mainPicture;
	}

	/**
	 * @param mainPicture
	 *            the mainPicture to set
	 */
	public void setMainPicture(Picture mainPicture) {
		this.mainPicture = mainPicture;
	}

	public boolean hasMainPicture() {
		return mainPicture != null;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
