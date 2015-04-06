/**
 * 
 */
package com.megift.bsp.action.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.pos.entity.POS;
import com.megift.resources.base.Dao;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.entity.Location;
import com.megift.set.location.geolocation.entity.Geolocation;

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
public class ActionDao extends Dao {

	/**
	 * @param action
	 * @return
	 */
	public static boolean update(Action action) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_bsp_actions_UPDATE(?,?,?,?,?,?)");
			cst.setInt(1, action.getId());
			cst.setInt(2, action.getType().getId());
			cst.setString(3, action.getOtherType());
			cst.setDouble(4, action.getPrice());
			cst.setString(5, action.getName());
			cst.setString(6, action.getDescription());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param action
	 * @return
	 */
	public static boolean create(Action action) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_actions_CREATE(?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			result = cst.executeUpdate() > 0;
			if (result)
				action.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

	public static boolean searchAction(Partner user) {
		List<POS> POSList = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_actions_SEARCH(?,?)}");
			cst.setInt(1, user.getPos() == null ? 0 : user.getPos().getId());
			cst.setInt(2, user.getGift().getAction() == null ? 0 : user.getGift().getAction().getType().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				POSList = new ArrayList<POS>();
				int POSIdOld = 0;
				int POSIdCurrent = 0;
				POS pos = null;
				List<Gift> giftList = null;
				int posCount = 0;
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
						if (posCount > 0) {
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
						posCount++;
					}
					Gift gift = new Gift(rs.getInt(5));
					gift.setStartDate(rs.getTimestamp(6).toLocalDateTime());
					gift.setExpirationDate(rs.getTimestamp(7).toLocalDateTime());
					Action action = new Action(rs.getInt(8));
					action.setPrice(rs.getDouble(9));
					action.setName(rs.getString(10));
					gift.setAction(action);
					giftList.add(gift);

				} while (rs.next());
				// agregar el ultimo punto de venta
				if (POSIdCurrent == POSIdOld && !giftList.isEmpty()) {
					pos.setGiftList(giftList);
					POSList.add(pos);
				}
			}
			user.setPOSList(POSList);
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to search the Actions .\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

}
