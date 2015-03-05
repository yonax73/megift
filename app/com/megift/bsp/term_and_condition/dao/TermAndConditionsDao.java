/**
 * 
 */
package com.megift.bsp.term_and_condition.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.term_and_condition.TermAndCondition;
import com.megift.resources.base.Dao;

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
public class TermAndConditionsDao extends Dao {

	/**
	 * @param gift
	 * @return
	 */
	public static boolean loadTermsnAndConditions(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = DB.getConnection();
		List<TermAndCondition> termAndConditions = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_terms_and_conditions_LOAD(?)}");
			cst.setInt(1, gift.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				termAndConditions = new ArrayList<TermAndCondition>();
				do {
					termAndConditions.add(new TermAndCondition(rs.getInt(1), rs.getString(2)));
				} while (rs.next());
				gift.setTermsAndConditions(termAndConditions);
			}
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the Terms and conditions.\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

}
