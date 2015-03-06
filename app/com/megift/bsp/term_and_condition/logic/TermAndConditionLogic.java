/**
 * 
 */
package com.megift.bsp.term_and_condition.logic;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.term_and_condition.TermAndCondition;
import com.megift.bsp.term_and_condition.dao.TermAndConditionsDao;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * created : Mar 5, 2015<br/>
 * update date : Mar 5, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class TermAndConditionLogic {

	/**
	 * @param gift
	 * @return
	 */
	public static boolean loadTermsAndConditions(Gift gift) {
		boolean result = false;
		if (gift.exists()) {
			result = TermAndConditionsDao.loadTermsnAndConditions(gift);
		}
		return result;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean save(Gift gift) {
		boolean saved = false;
		if (gift.exists()) {
			saved = TermAndConditionsDao.save(gift);
		}
		return saved;
	}

	/**
	 * @param termAndCondition
	 * @return
	 */
	public static boolean delete(TermAndCondition termAndCondition) {
		return TermAndConditionsDao.delete(termAndCondition);
	}

}
