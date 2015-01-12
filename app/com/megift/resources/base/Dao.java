package com.megift.resources.base;

import java.sql.Connection;
import java.sql.SQLException;

import play.Logger;

public class Dao {
	public static  void close(Connection connection){		
	    try {	    	
			connection.close();			
		} catch (SQLException e) {			
			Logger.error("Error tryining to connect to database. \n"+e.getMessage(), e);
		}
	}
}
