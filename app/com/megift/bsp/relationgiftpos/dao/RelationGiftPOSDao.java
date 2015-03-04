/**
 * 
 */
package com.megift.bsp.relationgiftpos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.pos.entity.POS;
import com.megift.resources.base.Dao;

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
public class RelationGiftPOSDao extends Dao {

	/**
	 * @param relationGiftPOS
	 * @return
	 */
	public static boolean createPOSList(Gift gift) {
		CallableStatement cst = null;
		Connection conn = null;
		int count = 0;
		try {
			conn = DB.getConnection();
			for (POS pos : gift.getPosList()) {
				cst = conn.prepareCall("CALL sp_bsp_gifts_by_pos_CREATE(?,?,?)");
				cst.registerOutParameter(1, Types.INTEGER);
				cst.setInt(2, gift.getId());
				cst.setInt(3, pos.getId());
				if (cst.executeUpdate() > 0 && cst.getInt(1) > 0) {
					count++;
				}
			}

		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return count == gift.getPosList().size();
	}
}
