package com.megift.sec.login.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.sec.login.entity.Login;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * date : Feb 19, 2015<br/>
 * update date : Feb 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class LoginDao extends Dao {
	/**
	 * @param login
	 * @return
	 */
	public static boolean create(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_login_CREATE(?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, login.getEmail());
			cst.setString(3, login.getPassword());
			cst.setInt(4, login.getType().getId());
			result = cst.executeUpdate() > 0;
			if (result)
				login.setId(cst.getInt(1));
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
	 * @param login
	 * @return
	 */
	public static boolean signIn(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_login_SIGN_IN(?,?,?);";
			cst = conn.prepareCall(sql);
			cst.setString(1, login.getEmail());
			cst.setString(2, login.getPassword());
			cst.setInt(3, login.getType().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1) > 0;
				if (result)
					login.setId(rs.getInt(2));
			}

		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

	public static boolean exists(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_login_EXISTS(?,?);";
			cst = conn.prepareCall(sql);
			cst.setString(1, login.getEmail());
			cst.setInt(2, login.getType().getId());
			rs = cst.executeQuery();
			if (rs.next())
				result = rs.getInt(1) > 0;
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
	 * @param login
	 * @return
	 */
	public static boolean update(Login login) {

		return false;
	}

	/**
	 * @param login
	 * @return
	 */
	public static boolean createPasswordChangeRequest(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_password_change_requests_CREATE(?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.registerOutParameter(2, Types.INTEGER);
			cst.setString(3, login.getEmail());
			cst.setInt(4, login.getType().getId());
			result = cst.executeUpdate() > 0;
			if (result) {
				login.setCodeRequest(cst.getInt(1));
				login.setId(cst.getInt(2));
			}
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
	 * @param login
	 * @return
	 */
	public static boolean existsPasswordChangeRequest(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_password_change_requests_EXISTS(?,?);";
			cst = conn.prepareCall(sql);
			cst.setInt(1, login.getCodeRequest());
			cst.setInt(2, login.getId());
			rs = cst.executeQuery();
			if (rs.next())
				result = rs.getInt(1) > 0;
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

	public static boolean deletePasswordChangeRequest(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_password_change_requests_DELETE(?);";
			cst = conn.prepareCall(sql);
			cst.setInt(1, login.getCodeRequest());
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

	public static boolean passwordReset(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_sec_login_PASSWORD_UPDATE(?,?)");
			cst.setInt(1, login.getId());
			cst.setString(2, login.getPassword());
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

}
