/**
 * 
 */
package com.megift.bsp.action.logic;

import java.util.Collections;
import java.util.Comparator;

import com.megift.bsp.action.dao.ActionDao;
import com.megift.bsp.action.entity.Action;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.pos.entity.POS;
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
public class ActionLogic {

	/**
	 * @param gift
	 * @return
	 */
	public static boolean save(Action action) {
		boolean saved = false;
		if (!action.isEmpty()) {
			if (action.exists()) {
				saved = ActionDao.update(action);
			} else {
				saved = ActionDao.create(action);
			}
		}
		return saved;
	}

	/**
	 * @param action
	 * @return
	 */
	public static boolean createAction(Action action) {

		return ActionDao.create(action);
	}

	/**
	 * @param action
	 * @return
	 */
	public static boolean update(Action action) {
		boolean saved = false;
		if (action.exists()) {
			saved = ActionDao.update(action);
		}
		return saved;
	}

	public static boolean searchAction(Partner user) {
		boolean result = false;
		if (user.getLocation() != null) {
			result = ActionDao.searchAction(user);
			if (result && user.getPOSList() != null && !user.getPOSList().isEmpty()) {
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
				 * Traer solo la imagen principal
				 */
				for (POS pos : user.getPOSList()) {
					result = PictureLogic.loadActionMainPictureByGiftList(pos.getGiftList());
					if (!result) {
						break;
					}
				}
			}
		}
		return result;
	}

}
