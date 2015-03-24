/**
 * 
 */
package com.megift.set.setting.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.base.Dao;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.setting.entity.Setting;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Mar 22, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Mar 22, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class SettingDao extends Dao {

	/**
	 * @param user
	 * @return
	 */
	public static boolean updateSearchSetting(Partner user) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_set_settings_UPDATE_SEARCH(?,?,?)");
			cst.setInt(1, user.getSettings().getId());
			cst.setInt(2, user.getLogin().getId());
			cst.setInt(3, user.getSettings().getSearchSetting().getId());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning update the search setting.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean createSearchSetting(Partner user) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_settings_CREATE_SEARCH(?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, user.getLogin().getId());
			cst.setInt(3, user.getSettings().getSearchSetting().getId());
			result = cst.executeUpdate() > 0;
			if (result) {
				user.getSettings().setId(cst.getInt(1));
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning create the search setting.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return result;
	}

	public static boolean load(Partner user) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_settings_LOAD(?)}");
			cst.setInt(1, user.getLogin().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				user.setSettings(new Setting(rs.getInt(1)));
				user.getSettings().setSearchSetting(new MasterValue(rs.getInt(2)));
				result = true;
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the settings.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

}
