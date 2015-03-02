/**
 * 
 */
package com.megift.bsp.pos.logic;

import java.util.List;

import com.megift.bsp.business.entity.Business;
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
	public static List<POS> loadPOSByBusiness(Business business) {
		return POSDao.loadPOSByBusiness(business);
	}

	/**
	 * @param pos
	 * @return
	 */
	public static POS Load(POS pos) {
		// TODO Auto-generated method stub
		return null;
	}

}
