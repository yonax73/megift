package com.megift.user.logic;

import static com.megift.resources.email.Email.INFO_EMAIL;
import static com.megift.resources.email.Email.INFO_EMAIL_PASSWORD;
import static com.megift.resources.email.Email.SERVER_NAME;
import static com.megift.resources.email.Email.SERVER_SECURITY_TYPE_SSL;
import static com.megift.resources.email.Templates.RESOURCE_HEADER_EMAIL_IMAGE;
import static com.megift.resources.email.Templates.inviteEmailContacts;
import static com.megift.resources.email.Templates.successRegister;
import static com.megift.resources.utils.Constants.DATA_TEMP_PATH;
import static com.megift.resources.utils.Constants.DATE_FORMATTER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;

import play.Logger;

import com.megift.resources.email.Email;
import com.megift.resources.email.Resource;
import com.megift.user.dao.UserDao;
import com.megift.user.entity.User;

public class UserLogic {

	public static boolean registerUser(User user) {		
		return sendEmailRegister(user) && UserDao.registerUser(user);
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

	public static boolean existsUser(User user) {		
		return UserDao.existsUser(user);
	}
	
	public static boolean sendEmailRegister(User user){
		boolean registered = false;
		Email email = new Email(SERVER_NAME, INFO_EMAIL, INFO_EMAIL_PASSWORD,SERVER_SECURITY_TYPE_SSL);
		try {			
			email.sendHTMLMail(INFO_EMAIL, "Megift", user.getEmail(),user.getName(), "Bienvenido", successRegister(user.getName()),new Resource(RESOURCE_HEADER_EMAIL_IMAGE, "Megift"));
			registered = true;
		} catch (EmailException e) {
			Logger.error("Error tryning send register email \n"+e.getMessage());
		
		} catch (MalformedURLException e) {
			Logger.error("Error tryning create the resources for send register email \n"+e.getMessage());		
		}
		return registered;
	}
	
	public static boolean sendInviteToContacts(Profile profile,List<Contact> contactsList){
     boolean sent = false;	   	
 	   try {
           if(contactsList!=null && !contactsList.isEmpty()){
        	   String userName = profile.getFullName() != null ? profile.getFullName() : profile.getDisplayName() != null ? profile.getDisplayName()  : profile.getFirstName()!=null ? profile.getFirstName() : "Equipo Megift";
        	   for (Contact contact : contactsList) {
        		   if(contact.getEmail()!=null && !contact.getEmail().isEmpty()){    
            			try {        				
                 		   String contactName =  contact.getDisplayName() != null ? contact.getDisplayName()  : contact.getFirstName()!=null ? contact.getFirstName() : "";
                		   Email email = new Email(SERVER_NAME, INFO_EMAIL, INFO_EMAIL_PASSWORD,SERVER_SECURITY_TYPE_SSL);    
            				email.sendHTMLMail(INFO_EMAIL, userName, contact.getEmail(),contactName ,"Invitación a Megift Colombia", inviteEmailContacts(userName, contactName),new Resource(RESOURCE_HEADER_EMAIL_IMAGE, "Megift"));
            		
            			} catch (EmailException e) {
            				Logger.error("Error tryning send register email \n"+e.getMessage());
            			
            			} catch (MalformedURLException e) {
            				Logger.error("Error tryning create the resources for send register email \n"+e.getMessage());		
            			}
        		   }

			    }
        	   sent = true;
           }
		} catch (Exception e) {
			Logger.error("Error tryning send invitation to  contacts \n"+e.getMessage());
			
		}
		return sent;
	}

	


}
