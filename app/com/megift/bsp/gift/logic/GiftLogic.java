/**
 * 
 */
package com.megift.bsp.gift.logic;

import com.megift.bsp.gift.dao.GiftDao;
import com.megift.bsp.gift.entity.Gift;
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
				return PictureLogic.loadPicturesByAction(gift.getAction());
			}
		}
		return result;
	}

}
