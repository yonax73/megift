/**
 * 
 */
package com.megift.bsp.pos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.megift.bsp.business.entity.Business;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.pos.entity.POS;
import com.megift.resources.base.Dao;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.entity.Location;
import com.megift.set.location.geolocation.entity.Geolocation;
import com.megift.set.location.phone.entity.Phone;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * created : Mar 2, 2015<br/>
 * update date : Mar 2, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class POSDao extends Dao {

	/**
	 * @param pos
	 * @return
	 */
	public static boolean update(POS pos) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_bsp_POS_UPDATE(?,?,?,?)");
			cst.setInt(1, pos.getId());
			cst.setString(2, pos.getName());
			cst.setInt(3, pos.getContact().getId());
			cst.setInt(4, pos.getLocation().getId());
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
	 * @param pos
	 * @return
	 */
	public static boolean create(POS pos) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_POS_CREATE(?,?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, pos.getBussinesId());
			cst.setString(3, pos.getName());
			cst.setInt(4, pos.getContact().getId());
			cst.setInt(5, pos.getLocation().getId());
			result = cst.executeUpdate() > 0;
			if (result)
				pos.setId(cst.getInt(1));
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
	 * @param business
	 * @return
	 */
	public static boolean loadPOSByBusiness(Business business) {
		List<POS> POSList = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_POS_LOAD_BY_BUSINESS(?)}");
			cst.setInt(1, business.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				POSList = new ArrayList<POS>();
				do {
					POS pos = new POS(rs.getInt(1));
					pos.setName(rs.getString(2));
					Location loc = new Location(0);
					Address address = new Address(0);
					address.setAddress(rs.getString(3));
					Phone phone = new Phone(0);
					phone.setNumber(rs.getString(4));
					loc.setAddress(address);
					loc.setPhone(phone);
					pos.setContact(new Partner(rs.getString(5)));
					pos.setLocation(loc);
					POSList.add(pos);
				} while (rs.next());
			}
			business.setPosList(POSList);
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to load the POS List.\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

	/**
	 * @param pos
	 * @return
	 */
	public static POS load(POS pos) {
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = DB.getConnection();
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_POS_LOAD(?)}");
			cst.setInt(1, pos.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				pos.setName(rs.getString(1));
				Location location = new Location(rs.getInt(2));
				Phone phone = new Phone(rs.getInt(3));
				phone.setNumber(rs.getString(4));
				phone.setExtension(rs.getString(5));
				phone.setMobile(rs.getString(6));
				Address address = new Address(rs.getInt(7));
				address.setAddress(rs.getString(8));
				Geolocation geolocation = new Geolocation(rs.getInt(9));
				geolocation.setLongitude(rs.getDouble(10));
				geolocation.setLatitude(rs.getDouble(11));
				address.setGeolocation(geolocation);
				location.setAddress(address);
				location.setPhone(phone);
				pos.setLocation(location);
				pos.setContact(new Partner(rs.getString(12)));
				pos.getContact().setId(rs.getInt(13));
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the POS.\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return pos;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean loadPOSByGift(Gift gift) {
		List<POS> POSList = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_POS_LOAD_BY_GIFT(?)}");
			cst.setInt(1, gift.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				POSList = new ArrayList<POS>();
				do {
					POSList.add(new POS(rs.getInt(1)));
				} while (rs.next());
			}
			gift.setPosList(POSList);
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to load the POS List by Gift.\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

	/**
	 * @param pos
	 * @return
	 */
	public static boolean associateGifToPOS(POS pos) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "{CALL sp_bsp_POS_ASSOCIATE_GIFT(?,?,?)}";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, pos.getId());
			cst.setInt(3, pos.getGift().getId());
			result = cst.executeUpdate() > 0 && cst.getInt(1) > 0;
		} catch (Exception e) {
			Logger.error("An error has been ocurred trying to associate the gift to pos.\n" + e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

	/**
	 * @param pos
	 * @return
	 */
	public static boolean removeGifToPOS(POS pos) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "{call sp_bsp_POS_REMOVE_GIFT(?,?)}";
			cst = conn.prepareCall(sql);
			cst.setInt(1, pos.getId());
			cst.setInt(2, pos.getGift().getId());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning remove gift from pos.\n" + e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

}
