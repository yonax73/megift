package com.megift.user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.user.entity.POS;

public class UserDao extends Dao {

	public static boolean registerUser(POS user) {
		boolean registered = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL megift_schema.sp_users_REGISTER_USER(?,?,?)}");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, user.getName());
			cst.setString(3, user.getEmail());
			registered = cst.executeUpdate() > 0;
			if (registered)
				user.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to register the user.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return registered;
	}

	public static int countUsers() {
		int counted = 0;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL megift_schema.sp_users_COUNT_USERS()}");
			rs = cst.executeQuery();
			if (rs.next())
				counted = rs.getInt(1);
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to count the users.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
			close(conn);
		}
		return counted;
	}

	public static List<POS> loadUsers() {
		List<POS> users = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL megift_schema.sp_users_LOAD_USERS()}");
			rs = cst.executeQuery();
			if (rs.next()) {
				users = new ArrayList<POS>();
				do {
					POS user = new POS(rs.getString(1), rs.getString(2));
					user.setCreated(rs.getTimestamp(3).toLocalDateTime());
					users.add(user);
				} while (rs.next());
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to load the users.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return users;
	}

	public static boolean existsUser(POS user) {
		boolean exists = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = DB.getConnection();
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_users_EXISTS_USER(?)}");
			cst.setString(1, user.getEmail());
			rs = cst.executeQuery();
			if (rs.next()) {
				exists = rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred checking the user.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return exists;
	}

}
