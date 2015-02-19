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
            String sql = "CALL sp_sec_login_CREATE(?,?,?);";
            cst = conn.prepareCall(sql);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setString(2, login.getEmail());
            cst.setString(3, login.getPassword());
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
            String sql = "CALL sp_sec_login_SIGN_IN(?,?);";
            cst = conn.prepareCall(sql);
            cst.setString(1, login.getEmail());
            cst.setString(2, login.getPassword());
            rs = cst.executeQuery();
            if (rs.next())
                login.setId(rs.getInt(1));
            result = login.getId() > 0;
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
