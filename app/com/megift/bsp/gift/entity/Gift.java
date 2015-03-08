package com.megift.bsp.gift.entity;

import static com.megift.resources.utils.Constants.DATE_FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.pos.entity.POS;
import com.megift.bsp.relationgiftpos.entity.RelationGiftPOS;
import com.megift.bsp.term_and_condition.TermAndCondition;
import com.megift.resources.base.Entity;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.picture.entity.Picture;

/**
 * @class : Gift.java<br/>
 * @company : Megift S.A<br/>
 * @user : YQ<br/>
 * @date : Feb 26, 2015<br/>
 * @update date : Feb 26, 2015<br/>
 * @update by : Feb 26, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Gift extends Entity {

	public static final int OTHER_TYPE = 24;
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Action action;
	private MasterValue type;
	private String otherType;
	private MasterValue status;
	private double price;
	private LocalDateTime startDate;
	private LocalDateTime expirationDate;
	private String name;
	private String description;
	private List<Picture> pictures;
	private List<TermAndCondition> termsAndConditions;
	private List<POS> posList;
	private POS pos;
	private List<RelationGiftPOS> giftPOSList;

	/**
	 * @param id
	 */
	public Gift(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getExpirationTime() {
		if (startDate != null && expirationDate != null) {
			String hours = String.valueOf(ChronoUnit.HOURS.between(startDate, expirationDate));
			return hours.concat(":").concat(" Horas");
		} else {
			return "";
		}

	}

	public String getFormatStartDate() {
		if (startDate != null) {
			return startDate.format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
		} else {
			return "";
		}
	}

	public String getFormatExpirtationtDate() {
		if (expirationDate != null) {
			return expirationDate.format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
		} else {
			return "";
		}
	}

	@Override
	public boolean isEmpty() {
		return name == null;
	}

	public boolean equals(Gift gift) {
		return id == gift.getId();
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
	 * @param pictures
	 *            the pictures to set
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

	/**
	 * @return
	 */
	public boolean isOtherType() {
		if (type != null) {
			return type.getId() == OTHER_TYPE;
		}
		return false;
	}

	public MasterValue getStatus() {
		return status;
	}

	public void setStatus(MasterValue status) {
		this.status = status;
	}

	public List<RelationGiftPOS> getGiftPOSList() {
		return giftPOSList;
	}

	public void setGiftPOSList(List<RelationGiftPOS> giftPOSList) {
		this.giftPOSList = giftPOSList;
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

	public POS getPos() {
		return pos;
	}

	public void setPos(POS pos) {
		this.pos = pos;
	}

}
