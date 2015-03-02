/**
 * 
 */
package com.megift.set.location.geolocation.logic;

import com.megift.set.location.geolocation.dao.GeolocationDao;
import com.megift.set.location.geolocation.entity.Geolocation;

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
public class GeolocationLogic {

	/**
	 * @param geolocation
	 * @return
	 */
	public static boolean save(Geolocation geolocation) {
		boolean saved = false;
		if (geolocation != null) {
			if (geolocation.exists()) {
				saved = GeolocationDao.update(geolocation);
			} else {
				saved = GeolocationDao.create(geolocation);
			}
		}
		return saved;
	}

}
