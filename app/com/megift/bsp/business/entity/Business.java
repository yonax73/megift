package com.megift.bsp.business.entity;

import java.util.List;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.pos.entity.POS;
import com.megift.resources.base.Entity;
import com.megift.set.location.entity.Location;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.picture.entity.Picture;

/**
 * @class : Business.java<br/>
 * @company : Megift S.A<br/>
 * @user : YQ<br/>
 * @date : Feb 26, 2015<br/>
 * @update date : Feb 26, 2015<br/>
 * @update by : Feb 26, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Business extends Entity {

	public static final int OTHER_TYPE = 9;
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Partner legalRepresentative;
	private Partner contact;
	private Location location;
	private MasterValue type;
	private String otherType;
	private String NIT;
	private String tradeName;
	private String legalName;
	private List<POS> posList;
	private POS pos;
	private List<Gift> giftList;
	private int giftCount;
	private String webSite;
	private int position;
	private Picture picture;
	private Partner customerService;

	/**
	 * @param id
	 */
	public Business(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pos
	 */
	public Business(POS pos) {
		super(0);
		this.pos = pos;
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

	public Partner getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(Partner legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public Partner getContact() {
		return contact;
	}

	public void setContact(Partner contact) {
		this.contact = contact;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	public String getNIT() {
		return NIT;
	}

	public void setNIT(String nIT) {
		NIT = nIT;
	}

	/**
	 * @return the posList
	 */
	public List<POS> getPosList() {
		return posList;
	}

	/**
	 * @param posList
	 *            the posList to set
	 */
	public void setPosList(List<POS> posList) {
		this.posList = posList;
	}

	/**
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * @param tradeName
	 *            the tradeName to set
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * @return the legalName
	 */
	public String getLegalName() {
		return legalName;
	}

	/**
	 * @param legalName
	 *            the legalName to set
	 */
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	/**
	 * @return
	 */
	public boolean isOtherType() {

		return type.getId() == OTHER_TYPE;
	}

	public List<Gift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<Gift> giftList) {
		this.giftList = giftList;
	}

	/**
	 * @return the pos
	 */
	public POS getPos() {
		return pos;
	}

	/**
	 * @param pos
	 *            the pos to set
	 */
	public void setPos(POS pos) {
		this.pos = pos;
	}

	public int getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
	}

	/**
	 * @return the webSite
	 */
	public String getWebSite() {
		return webSite;
	}

	/**
	 * @param webSite
	 *            the webSite to set
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	/**
	 * @return the customerService
	 */
	public Partner getCustomerService() {
		return customerService;
	}

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	public void setCustomerService(Partner customerService) {
		this.customerService = customerService;
	}

}
