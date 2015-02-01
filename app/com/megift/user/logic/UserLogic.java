package com.megift.user.logic;

import static com.megift.resources.utils.Constants.DATA_TEMP_PATH;
import static com.megift.resources.utils.Constants.DATE_FORMATTER;
import static com.megift.resources.utils.Constants.EMAIL_SERVER_NAME;
import static com.megift.resources.utils.Constants.EMAIL_SERVER_SECURITY_TYPE_SSL;
import static com.megift.resources.utils.Constants.EMAIL_USER_NAME;
import static com.megift.resources.utils.Constants.EMAIL_USER_PASSWORD;

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
		Email email = new Email(EMAIL_SERVER_NAME, EMAIL_USER_NAME, EMAIL_USER_PASSWORD, EMAIL_SERVER_SECURITY_TYPE_SSL);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body>");
		buffer.append("<table>");
		buffer.append("<tbody><tr>");
		buffer.append("<td><img style=\"width: 30%;\" src='${0}'/></td>");
		buffer.append("</tr><tr><td>Bienvenido a megift.<br><br>Hola "+user.getName()+",<br><br>¡Felicitaciones! Tu solicitud ha sido recibida.<br>");
		buffer.append("Estamos trabajando muy duro, para poderte dar muchos regalos y alegrar tu vida todos los días, porque sabemos que eres alguien especial.<br>");
		buffer.append("Con Megift podrás recibir regalos por:<ul><li>Salir a comer</li><li>Comprar ropa</li><li>Consentirte</li><li>Y mucho más...</li></ul>");
		buffer.append("Te pedimos un poco de paciencia, valdrá la pena la espera.<br>Pronto te enviaremos un email con el link de descarga.<br>¡Disfruta el regalo de la vida!<br>Equipo de Megift</td></tr>");
		buffer.append("</table></body></html>");	
		try {
			Resource image[] = {new Resource("https://cdn.rawgit.com/yonaxTics/megift/master/public/images/templates/headerMail.png", "Megift")};
			email.sendHTMLMail(EMAIL_USER_NAME, "Megift", new String[]{user.getEmail()}, new String[]{user.getName()}, "Bienvenido", buffer.toString(),image);
			registered = true;
		} catch (EmailException e) {
			Logger.error("Error tryning send register email \n"+e.getMessage());
		
		} catch (MalformedURLException e) {
			Logger.error("Error tryning create the resources for send register email \n"+e.getMessage());
		
		}
		return registered;
	}
	


}
