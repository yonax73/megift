/**
 * 
 */
package com.megift.set.base.item.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.set.base.item.entity.Item;

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
public class ItemDao extends Dao {

	/**
	 * @param masterId
	 * @return
	 */
	public static List<Item> listItemValue1(int masterId) {
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Item> list = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_set_master_values_LIST_ITEM_VALUE1(?)";
			cst = conn.prepareCall(sql);
			cst.setInt(1, masterId);

			rs = cst.executeQuery();
			if (rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(new Item(rs.getInt(1), rs.getString(2)));

				} while (rs.next());
			}

		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			if (cst != null)
				cst = null;
			close(conn);
		}
		return list;
	}

}
