package com.megift.set.location.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.set.location.entity.Location;

/** 
 * @class        : LocationDao.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 24, 2015<br/> 
 * @update date  : Feb 24, 2015<br/> 
 * @update by    : Feb 24, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class LocationDao extends Dao {

    /**
     * @param location
     * @return
     */
    public static boolean update(Location location) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            cst = conn.prepareCall("CALL sp_set_locations_UPDATE(?,?)");
            cst.setInt(1, location.getId());
            cst.setInt(2, location.getAddress().getId());
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
     * @param location
     * @return
     */
    public static boolean create(Location location) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "CALL sp_set_locations_CREATE(?,?);";
            cst = conn.prepareCall(sql);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setInt(2, location.getAddress().getId());
            result = cst.executeUpdate() > 0;
            if (result)
                location.setId(cst.getInt(1));
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
