/**
 * 
 */
package com.megift.bsp.action.logic;

import com.megift.bsp.action.dao.ActionDao;
import com.megift.bsp.action.entity.Action;

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

}
