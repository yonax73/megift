package com.megift.set.location.city.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.set.location.city.entity.City;

/** 
 * @class        : CityDao.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 24, 2015<br/> 
 * @update date  : Feb 24, 2015<br/> 
 * @update by    : Feb 24, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class CityDao extends Dao {

    public static boolean update(City city) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            cst = conn.prepareCall("CALL sp_set_cities_UPDATE(?,?)");
            cst.setInt(1, city.getId());
            cst.setString(2, city.getCity());
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
     * @param city
     * @return
     */
    public static boolean create(City city) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "CALL sp_set_cities_CREATE(?,?);";
            cst = conn.prepareCall(sql);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setString(2, city.getCity());
            result = cst.executeUpdate() > 0;
            if (result)
                city.setId(cst.getInt(1));
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
