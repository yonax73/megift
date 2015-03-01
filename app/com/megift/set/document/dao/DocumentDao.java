/**
 * 
 */
package com.megift.set.document.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.set.document.entity.Document;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Feb 28, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Feb 28, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class DocumentDao extends Dao {

	/**
	 * @param doc
	 * @return
	 */
	public static boolean update(Document doc) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_set_document_UPDATE(?,?,?,?)");
			cst.setInt(1, doc.getId());
			cst.setString(2, doc.getDocument());
			cst.setInt(3, doc.getType().getId());
			cst.setString(4, doc.getPlaceOfIssue());
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
	 * @param doc
	 * @return
	 */
	public static boolean create(Document doc) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_document_CREATE(?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, doc.getDocument());
			cst.setInt(3, doc.getType().getId());
			cst.setString(4, doc.getPlaceOfIssue());
			result = cst.executeUpdate() > 0;
			if (result)
				doc.setId(cst.getInt(1));
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
