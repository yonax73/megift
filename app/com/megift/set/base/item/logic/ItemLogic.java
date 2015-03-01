/**
 * 
 */
package com.megift.set.base.item.logic;

import java.util.List;

import com.megift.set.base.item.dao.ItemDao;
import com.megift.set.base.item.entity.Item;

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
public class ItemLogic {

	/**
	 * @param masterId
	 * @return
	 */
	public static List<Item> listItemValue1(int masterId) {
		// TODO Auto-generated method stub
		return ItemDao.listItemValue1(masterId);
	}

}
