/**
 * 
 */
package com.megift.set.setting.logic;

import com.megift.bsp.partner.entity.Partner;
import com.megift.set.setting.dao.SettingDao;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Mar 22, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Mar 22, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class SettingLogic {

	/**
	 * @param user
	 * @return
	 */
	public static boolean saveSearchSetting(Partner user) {
		boolean result = false;
		if (user != null) {
			if (user.getSettings().exists()) {
				result = SettingDao.updateSearchSetting(user);
			} else {
				result = SettingDao.createSearchSetting(user);
			}

		}
		return result;
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean load(Partner user) {
		boolean result = false;
		if (user != null) {
			result = SettingDao.load(user);
		}
		return result;
	}

}
