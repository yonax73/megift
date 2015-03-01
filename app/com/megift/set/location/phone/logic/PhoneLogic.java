/**
 * 
 */
package com.megift.set.location.phone.logic;

import com.megift.set.location.phone.dao.PhoneDao;
import com.megift.set.location.phone.entity.Phone;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Feb 28, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Feb 28, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class PhoneLogic {

	/**
	 * @param phone
	 * @return
	 */
	public static boolean save(Phone phone) {
		boolean saved = false;
		if (phone != null) {
			if (phone.exists()) {
				saved = PhoneDao.update(phone);
			} else {
				saved = PhoneDao.create(phone);
			}
		}
		return saved;
	}

}
