package com.megift.user.logic;

import static com.megift.resources.base.utils.Constants.DATA_TEMP_PATH;
import static com.megift.resources.base.utils.Constants.DATE_FORMATTER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
	
	public static File generateExcelUsers() throws IOException, InvalidFormatException{
		    File file = null;		    
		    List<User> users = UserDao.loadUsers();
		    if(users!= null && !users.isEmpty()){
		    	file = new File(DATA_TEMP_PATH.concat("exp_users_").concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)).concat(".xlsx")));
		    	int rowNumber = 0;
		    	int cellNumber = 0;
		    	Workbook wb = new XSSFWorkbook();
		    	Sheet sheet = wb.createSheet();
		    	Row head = sheet.createRow(rowNumber);
		        Cell nameUserHead = head.createCell(cellNumber);
		        nameUserHead.setCellValue("Nombre");
		        Cell emailUserHead = head.createCell(++cellNumber);
		        emailUserHead.setCellValue("Correo");
		        Cell dateUserHead = head.createCell(++cellNumber);
		        dateUserHead.setCellValue("Fecha");
		        for (User user : users) {
		        	cellNumber = 0;
		        	Row tmpRow = sheet.createRow(++rowNumber);
		        	Cell nameUser = tmpRow.createCell(cellNumber);
		        	nameUser.setCellValue(user.getName());
		        	Cell emailUser = tmpRow.createCell(++cellNumber);
		        	emailUser.setCellValue(user.getEmail());
		        	Cell dateUser = tmpRow.createCell(++cellNumber);
		        	dateUser.setCellValue(user.getFormatCreated());		        		
				}
	            FileOutputStream fileOut = new FileOutputStream(file);
	            wb.write(fileOut);
	            fileOut.close();		    	
		    }    
	        return file;
	}

}
