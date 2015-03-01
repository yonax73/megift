/**
 * 
 */
package com.megift.set.location.phone.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.set.location.phone.entity.Phone;

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
public class PhoneDao extends Dao {

	/**
	 * @param phone
	 * @return
	 */
	public static boolean update(Phone phone) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_set_phones_UPDATE(?,?,?,?)");
			cst.setInt(1, phone.getId());
			cst.setString(2, phone.getNumber());
			cst.setString(3, phone.getExtension());
			cst.setString(4, phone.getMobile());
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
	 * @param phone
	 * @return
	 */
	public static boolean create(Phone phone) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_phones_CREATE(?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, phone.getNumber());
			cst.setString(3, phone.getExtension());
			cst.setString(4, phone.getMobile());
			result = cst.executeUpdate() > 0;
			if (result)
				phone.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

}
