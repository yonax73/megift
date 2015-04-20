package com.megift.bsp.pos.entity;

import java.util.List;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.base.Entity;
import com.megift.resources.utils.Utils;
import com.megift.set.location.entity.Location;
import com.megift.set.location.geolocation.entity.Geolocation;

/**
 * @class : POS.java<br/>
 * @company : Megift S.A<br/>
 * @user : YQ<br/>
 * @date : Feb 26, 2015<br/>
 * @update date : Feb 26, 2015<br/>
 * @update by : Feb 26, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class POS extends Entity {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private String name;
	private Location location;
	private Partner contact;
	private List<Gift> giftList;
	private Gift gift;
	// TODO: cambiar por el obejto
	private int bussinesId;
	private String businessName;
	private Partner user;
	private double distanceInMeters;// TDOD: quitar in meters
	private double distanceInKiloMeters;
	private String email;

	/**
	 * @param id
	 */
	public POS(int id) {
		super(id);
	}

	/**
	 * 
	 */
	public POS() {
		super(0);

	}

	/**
	 * @param idPOS
	 * @param gift
	 */
	public POS(int id, Gift gift) {
		super(id);
		this.gift = gift;
	}

	public int compareTo(POS pos) {
		return distanceInMeters > pos.getDistanceInMeters() ? 1 : distanceInMeters == pos.getDistanceInMeters() ? 0 : -1;
	}

	/*
	 * Distancia entre el usuario y el punto de venta
	 */
	public void distanceInMetersBetweenUser() {
		if (user != null && user.getLocation() != null) {
			// TODO: quitar in meters
			distanceInMeters = Geolocation.distanceInMetersBetween(user.getLocation().getAddress().getGeolocation(), location.getAddress().getGeolocation());
			setDistanceInKiloMeters(Geolocation.distanceInKiloMetersBetween(user.getLocation().getAddress().getGeolocation(), location.getAddress().getGeolocation()));
		}

	}

	public int quantityOfGifts() {
		if (giftList != null) {
			return giftList.size();
		}
		return 0;
	}

	@Override
	public boolean isEmpty() {

		return name == null;
	}

	public boolean equals(POS pos) {
		return id == pos.id;
	}

	public boolean hasGift() {
		return gift != null && gift.exists();
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

	/**
	 * @return the giftList
	 */
	public List<Gift> getGiftList() {
		return giftList;
	}

	/**
	 * @param giftList
	 *            the giftList to set
	 */
	public void setGiftList(List<Gift> giftList) {
		this.giftList = giftList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bussinesId
	 */
	public int getBussinesId() {
		return bussinesId;
	}

	/**
	 * @param bussinesId
	 *            the bussinesId to set
	 */
	public void setBussinesId(int bussinesId) {
		this.bussinesId = bussinesId;
	}

	/**
	 * @return the gift
	 */
	public Gift getGift() {
		return gift;
	}

	/**
	 * @param gift
	 *            the gift to set
	 */
	public void setGift(Gift gift) {
		this.gift = gift;
	}

	/**
	 * @return the user
	 */
	public Partner getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Partner user) {
		this.user = user;
	}

	/**
	 * @return the distanceInMeters
	 */
	public double getDistanceInMeters() {
		return distanceInMeters;
	}

	/**
	 * @return the distanceInKiloMeters
	 */
	public double getDistanceInKiloMeters() {
		return distanceInKiloMeters;
	}

	// TODO:quitar
	public String getDistanceInMetersStr() {
		return String.valueOf(Utils.roundTo2Decimals(distanceInMeters)).concat(" m");
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getDistanceFormatted() {
		if (getDistanceInKiloMeters() >= 1) {
			return String.valueOf(Utils.roundTo2Decimals(getDistanceInKiloMeters())).concat(" km");
		} else {
			return String.valueOf(Utils.roundTo2Decimals(Utils.convertKilometersToMeteres(getDistanceInKiloMeters()))).concat(" m");
		}

	}

	public void setDistanceInKiloMeters(double distanceInKiloMeters) {
		this.distanceInKiloMeters = distanceInKiloMeters;
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
}
