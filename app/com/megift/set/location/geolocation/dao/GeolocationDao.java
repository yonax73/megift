/**
 * 
 */
package com.megift.set.location.geolocation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.set.location.geolocation.entity.Geolocation;

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
public class GeolocationDao extends Dao {

	/**
	 * @param geolocation
	 * @return
	 */
	public static boolean create(Geolocation geolocation) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_geolocations_CREATE(?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setDouble(2, geolocation.getLatitude());
			cst.setDouble(3, geolocation.getLongitude());
			result = cst.executeUpdate() > 0;
			if (result)
				geolocation.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param geolocation
	 * @return
	 */
	public static boolean update(Geolocation geolocation) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_set_geolocations_UPDATE(?,?,?)");
			cst.setInt(1, geolocation.getId());
			cst.setDouble(2, geolocation.getLatitude());
			cst.setDouble(3, geolocation.getLongitude());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

}
