/**
 * 
 */
package com.megift.bsp.relationgiftpos.logic;

import java.util.ArrayList;

import com.megift.bsp.relationgiftpos.dao.RelationGiftPOSDao;
import com.megift.bsp.relationgiftpos.entity.RelationGiftPOS;

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
	public static ArrayList<RelationGiftPOS> savePOSList(RelationGiftPOS relationGiftPOS) {
		return RelationGiftPOSDao.createPOSList(relationGiftPOS);
	}

}
