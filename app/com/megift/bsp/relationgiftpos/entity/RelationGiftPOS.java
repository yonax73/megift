/**
 * 
 */
package com.megift.bsp.relationgiftpos.entity;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.pos.entity.POS;
import com.megift.resources.base.Entity;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Mar 3, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Mar 3, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class RelationGiftPOS extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Gift gift;
	private POS pos;
	private String idPOSList[];

	/**
	 * @param id
	 */
	public RelationGiftPOS(int id) {
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
		// TODO Auto-generated method stub
		return false;
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

	/**
	 * @return the idPOSList
	 */
	public String[] getIdPOSList() {
		return idPOSList;
	}

	/**
	 * @param idPOSList
	 *            the idPOSList to set
	 */
	public void setIdPOSList(String[] idPOSList) {
		this.idPOSList = idPOSList;
	}

}
