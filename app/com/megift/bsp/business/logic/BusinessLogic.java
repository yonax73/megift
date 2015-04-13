/**
 * 
 */
package com.megift.bsp.business.logic;

import java.util.List;

import com.megift.bsp.business.dao.BusinessDao;
import com.megift.bsp.business.entity.Business;

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
public class BusinessLogic {

	/**
	 * @param business
	 * @return
	 */
	public static boolean save(Business business) {
		boolean saved = false;
		if (!business.isEmpty()) {
			if (business.exists()) {
				saved = BusinessDao.update(business);
			} else {
				saved = BusinessDao.create(business);
			}
		}
		return saved;
	}

	/**
	 * @param business
	 * @return
	 */
	public static Business load(Business business) {
		return BusinessDao.load(business);
	}

	public static List<Business> search(Business business) {
		if (business != null) {
			return BusinessDao.search(business);
		}
		return null;
	}

	/**
	 * @param business
	 * @return
	 */
	public static Business loadById(Business business) {
		return BusinessDao.loadById(business);
	}

	public static int searchCount(Business business) {
		int searchCount = 0;
		if (business != null) {
			searchCount = BusinessDao.searchCount(business);
		}
		return searchCount;
	}

}
