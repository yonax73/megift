package com.megift.set.picture.dao;

import static com.megift.resources.utils.Utils.concatActionIds;
import static com.megift.resources.utils.Utils.concatGiftIds;

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

	public static boolean create(Picture picture) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_pictures_CREATE(?,?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, picture.getMime());
			cst.setString(3, picture.getCoding());
			cst.setString(4, picture.getPath());
			cst.setBoolean(5, picture.isMain());
			result = cst.executeUpdate() > 0;
			if (result)
				picture.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning create the picture.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
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
			String sql = "CALL sp_set_pictures_UPDATE(?,?,?,?,?)";
			cst = conn.prepareCall(sql);
			cst.setInt(1, picture.getId());
			cst.setString(2, picture.getMime());
			cst.setString(3, picture.getCoding());
			cst.setString(4, picture.getPath());
			cst.setBoolean(5, picture.isMain());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning update the picture.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean createPictureCollection(Gift gift) {

		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_pictures_colleactions_CREATE(?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, gift.getPictures().get(0).getId());
			cst.setInt(3, gift.getId());
			cst.setInt(4, 0);
			result = cst.executeUpdate() > 0 && cst.getInt(1) > 0;

		} catch (Exception e) {
			Logger.error("An error has been occurred tryning create the picture collection by gift.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return result;

	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean createPictureCollection(Action action) {

		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_pictures_colleactions_CREATE(?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, action.getPictures().get(0).getId());
			cst.setInt(3, 0);
			cst.setInt(4, action.getId());
			result = cst.executeUpdate() > 0 && cst.getInt(1) > 0;

		} catch (Exception e) {
			Logger.error("An error has been occurred tryning create the picture collection by action.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return result;

	}

	/**
	 * @param gift
	 * @return
	 */
	public static boolean loadPicturesByGift(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Picture> pictures = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_set_pictures_LOAD_PICTURES_BY_GIFT(?)}");
			cst.setInt(1, gift.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				pictures = new ArrayList<Picture>();
				do {
					Picture p = new Picture(rs.getInt(1));
					p.setMime(rs.getString(2));
					p.setCoding(rs.getString(3));
					p.setPath(rs.getString(4));
					p.setMain(rs.getBoolean(5));
					pictures.add(p);
				} while (rs.next());
				gift.setPictures(pictures);
			}
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the Pictures by Gift.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

	/**
	 * @param gifts
	 * @return
	 */

	public static boolean loadMainPictureByGiftList(List<Gift> giftList) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();

			cst = conn.prepareCall("{CALL sp_set_pictures_LOAD_MAIN_PICTURE_BY_GIFT_LIST(?)}");
			cst.setString(1, concatGiftIds(giftList));
			rs = cst.executeQuery();
			if (rs.next()) {
				do {
					Picture p = new Picture(rs.getInt(1));
					p.setMime(rs.getString(2));
					p.setCoding(rs.getString(3));
					p.setPath(rs.getString(4));
					p.setMain(true);
					/*
					 * Asociar imagen con regalos por el id
					 */
					for (Gift gift : giftList) {
						if (!gift.hasMainPicture() && gift.getId() == rs.getInt(5))
							gift.setMainPicture(p);
					}

				} while (rs.next());
			}

			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the main Pictures by Gift list.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

	/**
	 * @param action
	 * @return
	 */
	public static boolean loadPicturesByAction(Action action) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Picture> pictures = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_set_pictures_LOAD_PICTURES_BY_ACTION(?)}");
			cst.setInt(1, action.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				pictures = new ArrayList<Picture>();
				do {
					Picture p = new Picture(rs.getInt(1));
					p.setMime(rs.getString(2));
					p.setCoding(rs.getString(3));
					p.setPath(rs.getString(4));
					p.setMain(rs.getBoolean(5));
					pictures.add(p);
				} while (rs.next());
				action.setPictures(pictures);
			}
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the Pictures by action.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

	public static boolean loadMainPictureByActionList(List<Action> actionList) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_set_pictures_LOAD_ACTION_MAIN_PICTURE_BY_GIFT_LIST(?)}");
			cst.setString(1, concatActionIds(actionList));
			rs = cst.executeQuery();
			if (rs.next()) {
				do {
					Picture p = new Picture(rs.getInt(1));
					p.setMime(rs.getString(2));
					p.setCoding(rs.getString(3));
					p.setPath(rs.getString(4));
					p.setMain(true);
					/*
					 * Asociar imagen con acciones por el id
					 */
					for (Action action : actionList) {
						if (!action.hasMainPicture() && action.getId() == rs.getInt(5))
							action.setMainPicture(p);
					}

				} while (rs.next());
			}

			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the main Pictures by Action list.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

	public static boolean loadActionMainPictureByGiftList(List<Gift> giftList) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_set_pictures_LOAD_ACTION_MAIN_PICTURE_BY_GIFT_LIST(?)}");
			cst.setString(1, concatGiftIds(giftList));
			rs = cst.executeQuery();
			if (rs.next()) {
				do {
					Picture p = new Picture(rs.getInt(1));
					p.setMime(rs.getString(2));
					p.setCoding(rs.getString(3));
					p.setPath(rs.getString(4));
					p.setMain(true);
					/*
					 * Asociar imagen con regalos por el id
					 */
					for (Gift gift : giftList) {
						if (!gift.getAction().hasMainPicture() && gift.getId() == rs.getInt(5))
							gift.getAction().setMainPicture(p);
					}

				} while (rs.next());
			}

			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the main Pictures by Action list.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

}
