package com.megift.user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.megift.resources.base.Dao;
import com.megift.user.entity.User;

public class UserDao extends Dao {

	public static boolean registerUser(User user) {
		boolean registered = false;
		CallableStatement cst = null;
		Connection conn = null;	
		try {
			 conn = DB.getConnection();
			 cst = conn.prepareCall("{CALL megift_schema.sp_users_REGISTER_USER(?,?,?)}");
			 cst.registerOutParameter(1, Types.INTEGER);
			 cst.setString(2,user.getName());
			 cst.setString(3, user.getEmail());
			 registered = cst.executeUpdate() > 0;
			 if(registered) user.setId(cst.getInt(1));
		} catch (Exception e) {              
			Logger.error("An error has been occurred trying to register the user.\n"+e.getMessage(),e);			
		} finally{			
			if(cst != null) cst = null;
			close(conn);			
		}
		return registered;
	}
	
	public static int countUsers(){
		int counted = 0;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL megift_schema.sp_users_COUNT_USERS()}");
			rs  = cst.executeQuery();
			if(rs.next()) counted = rs.getInt(1);
		} catch (Exception e) {              
			Logger.error("An error has been occurred trying to count the users.\n"+e.getMessage(),e);			
		} finally{			
			if(cst != null) cst = null;
			close(conn);			
		}
		return counted;
	}

}
