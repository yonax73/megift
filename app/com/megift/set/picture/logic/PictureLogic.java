package com.megift.set.picture.logic;

import java.util.List;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.dao.PartnerDao;
import com.megift.bsp.partner.entity.Partner;
import com.megift.set.picture.entity.Picture;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * date : Feb 19, 2015<br/>
 * update date : Feb 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class PictureLogic {
	/**
	 * @param picture
	 * @return
	 */
	public static boolean savePicturePartner(Partner partner) {
		boolean result = false;
		if (partner.getPicture().exists()) {
			result = PictureDao.update(partner.getPicture());
		} else if (partner.exists()) {
			result = PictureDao.create(partner.getPicture());
			if (partner.getPicture().exists()) {
				result = PartnerDao.updatePicture(partner);
			}
		}
		return result;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean savePictureCollection(Gift gift) {
		boolean result = false;
		Picture picture = gift.getPictures().get(0);
		if (picture.exists()) {
			result = PictureDao.update(picture);
		} else if (gift.exists()) {
			result = PictureDao.create(picture);
			if (picture.exists()) {
				result = PictureDao.createPictureCollection(gift);
			}
		}
		return result;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean savePictureCollection(Action action) {
		boolean result = false;
		Picture picture = action.getPictures().get(0);
		if (picture.exists()) {
			result = PictureDao.update(picture);
		} else if (action.exists()) {
			result = PictureDao.create(picture);
			if (picture.exists()) {
				result = PictureDao.createPictureCollection(action);
			}
		}
		return result;
	}

	/**
	 * @return
	 */
	public static boolean loadPicturesByGift(Gift gift) {
		boolean result = false;
		if (gift.exists()) {
			result = PictureDao.loadPicturesByGift(gift);
		}
		return result;
	}

	/**
	 * @return
	 */
	public static boolean loadPicturesByAction(Action action) {
		boolean result = false;
		if (action.exists()) {
			result = PictureDao.loadPicturesByAction(action);
		}
		return result;
	}

	/**
	 * @param gifList
	 * @return
	 */
	public static boolean loadPicturesByGiftList(List<Gift> giftList) {
		boolean result = false;
		if (giftList != null && !giftList.isEmpty()) {
			result = PictureDao.loadPicturesByGiftList(giftList);
		}
		return result;
	}

}
