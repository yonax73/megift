/**
 * 
 */
package com.megift.bsp.gift.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.megift.bsp.business.entity.Business;
import com.megift.bsp.gift.dao.GiftDao;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.pos.entity.POS;
import com.megift.bsp.relationgiftpos.logic.RelationGiftPOSLogic;
import com.megift.set.picture.logic.PictureLogic;

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
public class GiftLogic {

	/**
	 * @param gift
	 * @return
	 */
	public static boolean save(Gift gift) {
		boolean saved = false;
		if (!gift.isEmpty()) {
			if (gift.exists()) {
				saved = GiftDao.update(gift);
			} else {
				saved = GiftDao.create(gift);
			}
		}
		return saved;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean createGift(Gift gift) {
		boolean created = false;
		if (gift != null) {
			created = GiftDao.create(gift);
			if (created) {
				created = RelationGiftPOSLogic.createPOSList(gift);
			}
		}
		return created;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean update(Gift gift) {
		boolean saved = false;
		if (gift.exists()) {
			saved = GiftDao.update(gift);
		}
		return saved;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean load(Gift gift) {
		boolean result = false;
		if (gift.exists() && GiftDao.load(gift)) {
			if (PictureLogic.loadPicturesByGift(gift)) {
				result = PictureLogic.loadPicturesByAction(gift.getAction());
			}
		}
		return result;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean loadGiftByBusiness(Business business) {
		boolean result = false;
		if (business.exists()) {
			result = GiftDao.loadGiftByBusiness(business) && business.getGiftList() != null;
			if (result) {
				result = PictureLogic.loadPicturesByGiftList(business.getGiftList());
			}
		}
		return result;
	}

	/**
	 * @param business
	 * @param pos
	 * @return
	 */
	public static boolean loadGiftsByPOS(Business business, POS pos) {
		boolean result = false;
		if (business.exists() && pos.exists()) {
			result = GiftDao.loadGiftByBusiness(business) && business.getGiftList() != null;
			if (result) {
				result = PictureLogic.loadPicturesByGiftList(business.getGiftList());
			}
			if (result) {
				if (GiftDao.loadGiftsByPOS(pos) && pos.getGiftList() != null) {
					/*
					 * regalos de este punto de venta
					 */
					List<Gift> giftList = business.getGiftList();
					/*
					 * Todos los regalos de este negocio
					 */
					List<Gift> giftListByPOS = pos.getGiftList();
					for (Gift gift : giftListByPOS) {
						boolean found = false;
						int i = 0;
						int n = giftList.size();
						do {
							Gift tmpGift = giftList.get(i);
							if (gift.equals(tmpGift)) {
								tmpGift.setPos(new POS(pos.getId()));
								found = true;
							}
							i++;
						} while (!found && i < n);
					}
				}
			}
		}
		return result;
	}

	public static boolean searchGift(Partner user) {
		boolean result = false;
		if (user.getLocation() != null) {
			result = GiftDao.searchGift(user);
			if (result) {
				/*
				 * Ordenar los puntos de ventas por distancia de menor a mayor
				 */
				Collections.sort(user.getPOSList(), new Comparator<POS>() {
					@Override
					public int compare(POS pos1, POS pos2) {
						return pos1.compareTo(pos2);
					}
				});
				/*
				 * Cargar las imagenes del regalo
				 */
				for (POS pos : user.getPOSList()) {
					result = PictureLogic.loadPicturesByGiftList(pos.getGiftList());
					if (!result) {
						break;
					}
				}

			}
		}
		return result;
	}
}
