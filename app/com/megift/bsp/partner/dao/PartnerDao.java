package com.megift.bsp.partner.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.base.Dao;
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
public class PartnerDao extends Dao {

    /**
     * @param partner
     * @return
     */
    public static boolean create(Partner partner) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "CALL sp_bsp_partners_CREATE(?,?,?);";
            cst = conn.prepareCall(sql);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setString(2, partner.getName());
            cst.setInt(3, partner.getLogin().getId());
            result = cst.executeUpdate() > 0;
            if (result)
                partner.setId(cst.getInt(1));
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
