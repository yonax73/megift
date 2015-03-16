/**
 * 
 */
package com.megift.bsp.action.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.bsp.action.entity.Action;
import com.megift.resources.base.Dao;

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
public class ActionDao extends Dao {

	/**
	 * @param action
	 * @return
	 */
	public static boolean update(Action action) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_bsp_actions_UPDATE(?,?,?,?,?,?)");
			cst.setInt(1, action.getId());
			cst.setInt(2, action.getType().getId());
			cst.setString(3, action.getOtherType());
			cst.setDouble(4, action.getPrice());
			cst.setString(5, action.getName());
			cst.setString(6, action.getDescription());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param action
	 * @return
	 */
	public static boolean create(Action action) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_actions_CREATE(?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			result = cst.executeUpdate() > 0;
			if (result)
				action.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

}
