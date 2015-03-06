/**
 * 
 */
package com.megift.bsp.pos.logic;

import java.util.List;

import com.megift.bsp.business.entity.Business;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.pos.dao.POSDao;
import com.megift.bsp.pos.entity.POS;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * created : Mar 2, 2015<br/>
 * update date : Mar 2, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class POSLogic {

	/**
	 * @param pos
	 * @return
	 */
	public static boolean save(POS pos) {
		boolean saved = false;
		if (!pos.isEmpty()) {
			if (pos.exists()) {
				saved = POSDao.update(pos);
			} else {
				if (pos.getBussinesId() > 0) {
					saved = POSDao.create(pos);
				}
			}
		}
		return saved;
	}

	/**
	 * @param parseInt
	 * @return
	 */
	public static boolean loadPOSByBusiness(Business business) {
		boolean result = false;
		if (business.exists()) {
			result = POSDao.loadPOSByBusiness(business) && business.getPosList() != null;
		}
		return result;
	}

	/**
	 * @param pos
	 * @return
	 */
	public static POS load(POS pos) {
		return POSDao.load(pos);
	}

	/**
	 * @param business
	 * @param gift
	 * @return
	 */
	public static boolean loadPOSByGift(Business business, Gift gift) {
		boolean result = false;
		if (business.exists() && gift.exists()) {
			result = POSDao.loadPOSByBusiness(business) && business.getPosList() != null;
			if (result) {
				result = POSDao.loadPOSByGift(gift) && gift.getPosList() != null;
				if (result) {
					/*
					 * puntos de venta con este regalo
					 */
					List<POS> POSList = business.getPosList();
					/*
					 * Todos puntos de venta del negocio
					 */
					List<POS> POSListByGift = gift.getPosList();
					for (POS pos : POSListByGift) {
						boolean found = false;
						int i = 0;
						int n = POSList.size();
						do {
							POS tmpPOS = POSList.get(i);
							if (pos.equals(tmpPOS)) {
								tmpPOS.setGift(new Gift(gift.getId()));
								found = true;
							}
							i++;
						} while (!found || i < n);
					}
				}
			}
		}
		return result;
	}
}
