package com.megift.bsp.partner.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Base64;

import play.Logger;
import play.db.DB;

import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.base.Dao;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.city.entity.City;
import com.megift.set.location.entity.Location;
import com.megift.set.master.entity.MasterValue;
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

	public static boolean loadPartner(Partner partner) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_partners_LOAD(?)";
			cst = conn.prepareCall(sql);
			cst.setInt(1, partner.getLogin().getId());

			rs = cst.executeQuery();
			if (rs.next()) {
				partner.setId(rs.getInt(1));
				partner.setPicture(new Picture(rs.getInt(2)));
				if (partner.getPicture().exists()) {
					partner.getPicture().setMime(rs.getString(3));
					Blob blob = rs.getBlob(4);
					partner.getPicture().setSrc(Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length())));
					partner.getPicture().setCoding(rs.getString(5));
				}
				partner.getLogin().setEmail(rs.getString(6));
				partner.setGender(new MasterValue(rs.getInt(7)));
				partner.setName(rs.getString(8));
				partner.setLastName(rs.getString(9));
				if (rs.getTimestamp(10) != null)
					partner.setBirthday(rs.getTimestamp(10).toLocalDateTime().toLocalDate());
				partner.setLocation(new Location(rs.getInt(11)));
				if (partner.getLocation().exists())
					partner.getLocation().setAddress(new Address(new City(rs.getInt(12), rs.getString(13))));
			}
			result = partner.exists();

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
     * @param partner
     * @return
     */
    public static boolean update(Partner partner) {
        boolean result = false;
        CallableStatement cst = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            cst = conn.prepareCall("CALL sp_bsp_partners_UPDATE(?,?,?,?,?)");
            cst.setInt(1, partner.getId());
            cst.setInt(2, partner.getGender().getId());
            cst.setInt(3, partner.getLocation().getId());
            cst.setString(4, partner.getName());
            cst.setTimestamp(5, Timestamp.valueOf(partner.getBirthday().atStartOfDay()));
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
