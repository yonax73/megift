/**
 * 
 */
package com.megift.bsp.gift.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.gift.entity.Gift;
import com.megift.resources.base.Dao;
import com.megift.set.master.entity.MasterValue;

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
public class GiftDao extends Dao {

	/**
	 * @param gift
	 * @return
	 */
	public static boolean create(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_gift_CREATE(?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			result = cst.executeUpdate() > 0;
			if (result)
				gift.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error(e.getMessage());
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
	public static boolean update(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_bsp_gift_UPDATE(?,?,?,?,?,?,?,?,?,?)");
			cst.setInt(1, gift.getId());
			cst.setInt(2, gift.getAction().getId());
			cst.setInt(3, gift.getStatus().getId());
			cst.setInt(4, gift.getType().getId());
			cst.setString(5, gift.getOtherType());
			cst.setDouble(6, gift.getPrice());
			cst.setTimestamp(7, Timestamp.valueOf(gift.getStartDate()));
			cst.setTimestamp(8, Timestamp.valueOf(gift.getExpirationDate()));
			cst.setString(9, gift.getName());
			cst.setString(10, gift.getDescription());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error(e.getMessage());
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
	public static Gift load(Gift gift) {
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = DB.getConnection();
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_gifts_LOAD(?)}");
			cst.setInt(1, gift.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				Action action = new Action(rs.getInt(1));
				action.setType(new MasterValue(rs.getInt(2)));
				if (action.isOtherType()) {
					action.setOtherType(rs.getString(3));
				}
				action.setName(rs.getString(4));
				action.setDescription(rs.getString(5));
				action.setPrice(rs.getDouble(6));
				gift.setAction(action);
				gift.setStatus(new MasterValue(rs.getInt(7)));
				gift.setType(new MasterValue(rs.getInt(8)));
				if (gift.isOtherType()) {
					gift.setOtherType(rs.getString(9));
				}
				gift.setPrice(rs.getDouble(10));
				gift.setStartDate(rs.getTimestamp(11).toLocalDateTime());
				gift.setExpirationDate(rs.getTimestamp(12).toLocalDateTime());
				gift.setName(rs.getString(13));
				gift.setDescription(rs.getString(14));

			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the POS.\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return gift;
	}

}
