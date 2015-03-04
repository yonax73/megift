/**
 * 
 */
package com.megift.bsp.relationgiftpos.logic;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.relationgiftpos.dao.RelationGiftPOSDao;

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
public class RelationGiftPOSLogic {

	/**
	 * @param relationGiftPOS
	 * @return
	 */
	public static boolean createPOSList(Gift gift) {
		return RelationGiftPOSDao.createPOSList(gift);
	}

}
