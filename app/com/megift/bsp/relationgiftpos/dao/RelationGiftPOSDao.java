/**
 * 
 */
package com.megift.bsp.relationgiftpos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;

import play.Logger;
import play.db.DB;

import com.megift.bsp.pos.entity.POS;
import com.megift.bsp.relationgiftpos.entity.RelationGiftPOS;
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
	public static ArrayList<RelationGiftPOS> createPOSList(RelationGiftPOS relationGiftPOS) {
		ArrayList<RelationGiftPOS> giftPOSList = null;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			giftPOSList = new ArrayList<>();
			conn = DB.getConnection();
			int idGift = relationGiftPOS.getGift().getId();
			String idPOSList[] = relationGiftPOS.getIdPOSList();
			int n = idPOSList.length;
			for (int i = 0; i < n; i++) {
				int idPOS = Integer.parseInt(idPOSList[i]);
				cst = conn.prepareCall("CALL sp_bsp_gifts_by_pos_CREATE(?,?,?)");
				cst.registerOutParameter(1, Types.INTEGER);
				cst.setInt(2, idGift);
				cst.setInt(3, idPOS);
				if (cst.executeUpdate() > 0) {
					RelationGiftPOS giftPOS = new RelationGiftPOS(cst.getInt(1));
					giftPOS.setPos(new POS(idPOS));
					giftPOSList.add(giftPOS);
				}
			}

		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return giftPOSList;
	}
}
