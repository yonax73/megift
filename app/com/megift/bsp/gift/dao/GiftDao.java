/**
 * 
 */
package com.megift.bsp.gift.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.business.entity.Business;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.pos.entity.POS;
import com.megift.resources.base.Dao;
import com.megift.sec.login.entity.Login;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.entity.Location;
import com.megift.set.location.geolocation.entity.Geolocation;
import com.megift.set.location.phone.entity.Phone;
import com.megift.set.master.entity.MasterValue;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Mar 3, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Mar 3, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class GiftDao extends Dao {

	/**
	 * @param gift
	 * @return
	 */
	public static boolean create(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_gift_CREATE(?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, gift.getAction().getId());
			result = cst.executeUpdate() > 0;
			if (result)
				gift.setId(cst.getInt(1));
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
	 * @param gift
	 * @return
	 */
	public static boolean update(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_bsp_gift_UPDATE(?,?,?,?,?,?,?,?,?,?,?)");
			cst.setInt(1, gift.getId());
			cst.setInt(2, gift.getAction().getId());
			cst.setInt(3, gift.getStatus().getId());
			cst.setInt(4, gift.getType().getId());
			cst.setString(5, gift.getOtherType());
			cst.setDouble(6, gift.getPrice());
			cst.setTimestamp(7, Timestamp.valueOf(gift.getStartDate()));
			cst.setTimestamp(8, Timestamp.valueOf(gift.getExpirationDate()));
			cst.setString(9, gift.getName());
			cst.setString(10, gift.getDescription());
			cst.setString(11, gift.getTermsConditions());
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
	 * @param gift
	 * @return
	 */
	public static boolean load(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = DB.getConnection();
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_gifts_LOAD(?)}");
			cst.setInt(1, gift.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				Action action = new Action(rs.getInt(1));
				action.setType(new MasterValue(rs.getInt(2)));
				if (action.isOtherType()) {
					action.setOtherType(rs.getString(3));
				}
				action.setName(rs.getString(4));
				action.setDescription(rs.getString(5));
				action.setPrice(rs.getDouble(6));
				gift.setAction(action);
				gift.setStatus(new MasterValue(rs.getInt(7)));
				gift.setType(new MasterValue(rs.getInt(8)));
				if (gift.isOtherType()) {
					gift.setOtherType(rs.getString(9));
				}
				gift.setPrice(rs.getDouble(10));
				gift.setStartDate(rs.getTimestamp(11) == null ? LocalDateTime.now() : rs.getTimestamp(11).toLocalDateTime());
				gift.setExpirationDate(rs.getTimestamp(12) == null ? LocalDateTime.now().plusDays(30) : rs.getTimestamp(12).toLocalDateTime());
				gift.setName(rs.getString(13));
				gift.setDescription(rs.getString(14));
				gift.setTermsConditions(rs.getString(15));
				result = true;
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the Gift.\n" + e.getMessage(), e);
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
	public static boolean load(Business business) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = DB.getConnection();
		POS pos = business.getPos();
		Gift gift = pos.getGift();
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_gifts_LOAD_FOR_MOBILE(?,?)}");
			cst.setInt(1, pos.getId());
			cst.setInt(2, gift.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				Action action = new Action(rs.getInt(1), rs.getString(2));
				action.setDescription(rs.getString(3));
				action.setPrice(rs.getDouble(4));
				gift.setAction(action);
				MasterValue type = new MasterValue(rs.getInt(5));
				type.setValue1(rs.getString(6));
				gift.setType(type);
				if (gift.isOtherType()) {
					gift.setOtherType(rs.getString(7));
				}
				gift.setPrice(rs.getDouble(8));
				gift.setName(rs.getString(9));
				gift.setDescription(rs.getString(10));
				gift.setTermsConditions(rs.getString(11));
				Phone phone = new Phone(rs.getString(12));
				phone.setExtension(rs.getString(13));
				phone.setMobile(rs.getString(14));
				Address address = new Address(rs.getString(15));
				address.setGeolocation(new Geolocation(rs.getDouble(16), rs.getDouble(17)));
				pos.setLocation(new Location(address, phone));
				pos.distanceInMetersBetweenUser();
				business.setTradeName(rs.getString(18));
				business.setContact(new Partner(new Login(rs.getString(19))));
				gift.setStartDate(rs.getTimestamp(20) == null ? LocalDateTime.now() : rs.getTimestamp(11).toLocalDateTime());
				gift.setExpirationDate(rs.getTimestamp(21) == null ? LocalDateTime.now().plusDays(30) : rs.getTimestamp(12).toLocalDateTime());
				result = true;
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the Gift by Mobile.\n" + e.getMessage(), e);
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
	public static boolean loadGiftByBusiness(Business business) {
		List<Gift> giftList = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_gifts_LOAD_BY_BUSINESS(?)}");
			cst.setInt(1, business.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				giftList = new ArrayList<Gift>();
				do {
					Gift gift = new Gift(rs.getInt(1));
					gift.setStatus(new MasterValue(rs.getString(2)));
					gift.setPrice(rs.getDouble(3));
					gift.setStartDate(rs.getTimestamp(4) == null ? LocalDateTime.now() : rs.getTimestamp(11).toLocalDateTime());
					gift.setExpirationDate(rs.getTimestamp(5) == null ? LocalDateTime.now().plusDays(30) : rs.getTimestamp(12).toLocalDateTime());
					gift.setName(rs.getString(6));
					giftList.add(gift);
				} while (rs.next());
			}
			business.setGiftList(giftList);
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to load the Gift List.\n" + e.getMessage(), e);
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
	public static boolean loadGiftsByPOS(POS pos) {
		List<Gift> giftList = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_gifts_LOAD_BY_POS(?)}");
			cst.setInt(1, pos.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				giftList = new ArrayList<Gift>();
				do {
					giftList.add(new Gift(rs.getInt(1)));
				} while (rs.next());
			}
			pos.setGiftList(giftList);
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to load the gift List by Pos.\n" + e.getMessage(), e);
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

	public static boolean searchGift(Partner user) {
		List<POS> POSList = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_gifts_SEARCH(?,?)}");
			cst.setInt(1, user.getPos() == null ? 0 : user.getPos().getId());
			cst.setInt(2, user.getGift() == null ? 0 : user.getGift().getType().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				POSList = new ArrayList<POS>();
				int POSIdOld = 0;
				int POSIdCurrent = 0;
				POS pos = null;
				List<Gift> giftList = null;
				do {
					POSIdCurrent = rs.getInt(1);
					/*
					 * Si el id del pos es diferente al anterior entonces
					 * creamos otro punto de venta
					 */
					if (POSIdCurrent != POSIdOld) {
						POSIdOld = POSIdCurrent;
						/*
						 * Cuando se va a crear el segundo punto de venta en
						 * adelante se agrega el anterior a la lista
						 */
						if (POSIdCurrent > 1) {
							pos.setGiftList(giftList);
							POSList.add(pos);
						}
						pos = new POS(POSIdCurrent);
						Partner tmpuser = new Partner(user.getLogin());
						tmpuser.setLocation(user.getLocation());
						pos.setUser(tmpuser);// impl clon
						pos.setName(rs.getString(2));
						pos.setLocation(new Location(new Address(new Geolocation(rs.getDouble(3), rs.getDouble(4)))));
						/*
						 * Calcula la distancia en metros del usuario al punto
						 * de venta
						 */
						pos.distanceInMetersBetweenUser();
						giftList = new ArrayList<>();
					}
					Gift gift = new Gift(rs.getInt(5));
					gift.setPrice(rs.getDouble(6));
					gift.setStartDate(rs.getTimestamp(7).toLocalDateTime());
					gift.setExpirationDate(rs.getTimestamp(8).toLocalDateTime());
					gift.setName(rs.getString(9));
					giftList.add(gift);

				} while (rs.next());
				// agregar el ultimo punto de venta
				if (POSIdCurrent == POSIdOld && !giftList.isEmpty()) {
					pos.setGiftList(giftList);
					POSList.add(pos);
				}
			}
			result = POSList != null && !POSList.isEmpty();
			user.setPOSList(POSList);
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to search the Gifts .\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}
}
