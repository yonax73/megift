package com.megift.set.picture.logic;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.base.Dao;
import com.megift.set.picture.entity.Picture;

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
public class PictureDao extends Dao {


    /**
     * @param partner
     *            Guarda la imagen y asocia el id de la imagen con el partner
     */
    public static boolean create(Partner partner) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "CALL sp_set_pictures_CREATE(?,?,?,?,?);";
            cst = conn.prepareCall(sql);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setInt(2, partner.getId());
            cst.setString(3, partner.getPicture().getMime());
            cst.setString(4, partner.getPicture().getCoding());
            cst.setBlob(5, new FileInputStream(partner.getPicture().getFile()));
            result = cst.executeUpdate() > 0;
            if (result)
                partner.getPicture().setId(cst.getInt(1));
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
     * @param picture
     */
    public static boolean update(Picture picture) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "CALL sp_set_pictures_UPDATE(?,?,?,?)";
            cst = conn.prepareCall(sql);
            cst.setInt(1, picture.getId());
            cst.setString(2, picture.getMime());
            cst.setString(3, picture.getCoding());
            cst.setBlob(4, new FileInputStream(picture.getFile()));
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
