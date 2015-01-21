package com.megift.user.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.megift.user.dao.UserDao;
import com.megift.user.entity.User;

public class UserLogic {

	public static boolean registerUser(User user) {		
		return UserDao.registerUser(user);
	}
	
	public static int countUsers(){
		return UserDao.countUsers();
	}

	public static List<User> loadUsers() {		
		return UserDao.loadUsers();
	}
	
	public static File generateExcelUsers() throws IOException{
		    File file = new File("mydata.xlsx");
	        FileOutputStream fileOut = new FileOutputStream(file);
	        XSSFWorkbook wb = new XSSFWorkbook();	         
	        XSSFSheet sheet = wb.createSheet("Sheet1");
	        int rNum = 0;
	        XSSFRow row = sheet.createRow(rNum);
	        int cNum = 0;
	        XSSFCell cell = row.createCell(cNum);
	        cell.setCellValue("My Cell Value");
	        wb.write(fileOut);
	        fileOut.flush();
	        fileOut.close();	        
	        return file;
	}

}
