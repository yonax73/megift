package com.megift.set.location.address.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.set.location.address.entity.Address;

/** 
 * @class        : AddressDao.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 24, 2015<br/> 
 * @update date  : Feb 24, 2015<br/> 
 * @update by    : Feb 24, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class AddressDao extends Dao {

    /**
     * @param address
     * @return
     */
    public static boolean update(Address address) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            cst = conn.prepareCall("CALL sp_set_addresses_UPDATE(?,?)");
            cst.setInt(1, address.getId());
            cst.setInt(2, address.getCity().getId());
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
     * @param address
     * @return
     */
    public static boolean create(Address address) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "CALL sp_set_addresses_CREATE(?,?);";
            cst = conn.prepareCall(sql);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setInt(2, address.getCity().getId());
            result = cst.executeUpdate() > 0;
            if (result)
                address.setId(cst.getInt(1));
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
