package com.megift.user.logic;

import com.megift.user.dao.UserDao;
import com.megift.user.entity.User;

public class UserLogic {

	public static boolean registerUser(User user) {		
		return UserDao.registerUser(user);
	}
	
	public static int countUsers(){
		return UserDao.countUsers();
	}

}
