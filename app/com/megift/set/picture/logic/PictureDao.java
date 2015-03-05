package com.megift.set.picture.logic;

import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
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
			cst.setBlob(4, new FileInputStream(picture.getFile()));
			cst.setBoolean(5, picture.isMain());
			result = cst.executeUpdate() > 0;
			if (result)
				picture.setId(cst.getInt(1));
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
			String sql = "CALL sp_set_pictures_UPDATE(?,?,?,?,?)";
			cst = conn.prepareCall(sql);
			cst.setInt(1, picture.getId());
			cst.setString(2, picture.getMime());
			cst.setString(3, picture.getCoding());
			cst.setBlob(4, new FileInputStream(picture.getFile()));
			cst.setBoolean(5, picture.isMain());
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
	public static boolean loadPicturesByGift(Gift gift) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = DB.getConnection();
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
					Blob blob = rs.getBlob(4);
					p.setSrc(Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length())));
					p.setMain(rs.getBoolean(5));
					pictures.add(p);
				} while (rs.next());
				gift.setPictures(pictures);
			}
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the Pictures by Gift.\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
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
		Connection conn = DB.getConnection();
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
					Blob blob = rs.getBlob(4);
					p.setSrc(Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length())));
					p.setMain(rs.getBoolean(5));
					pictures.add(p);
				} while (rs.next());
				action.setPictures(pictures);
			}
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the Pictures by Gift.\n" + e.getMessage(), e);
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return result;
	}

}
