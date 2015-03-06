/**
 * 
 */
package com.megift.bsp.term_and_condition.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
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

	/**
	 * @param gift
	 * @return
	 */
	public static boolean save(Gift gift) {
		CallableStatement cst = null;
		Connection conn = null;
		List<TermAndCondition> termAndConditions = gift.getTermsAndConditions();
		int count = 0;
		try {
			conn = DB.getConnection();
			String sql = null;
			int idGift = gift.getId();
			for (TermAndCondition termAndCondition : termAndConditions) {
				if (termAndCondition.exists()) {
					sql = "CALL sp_bsp_terms_and_conditions_UPDATE(?,?)";
					cst = conn.prepareCall(sql);
					cst.setInt(1, termAndCondition.getId());
					cst.setString(2, termAndCondition.getDescription());
					if (cst.executeUpdate() > 0)
						count++;
				} else {
					sql = "CALL sp_bsp_terms_and_conditions_CREATE(?,?,?)";
					cst = conn.prepareCall(sql);
					cst.registerOutParameter(1, Types.INTEGER);
					cst.setInt(2, idGift);
					cst.setString(3, termAndCondition.getDescription());
					if (cst.executeUpdate() > 0) {
						termAndCondition.setId(cst.getInt(1));
						count++;
					}
				}
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning save the Terms and conditions.\n" + e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return count == termAndConditions.size();
	}

	/**
	 * @param termAndCondition
	 * @return
	 */
	public static boolean delete(TermAndCondition termAndCondition) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "{call sp_bsp_terms_and_conditions_DELETE(?)}";
			cst = conn.prepareCall(sql);
			cst.setInt(1, termAndCondition.getId());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning delete the Terms and conditions.\n" + e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

}
