package com.megift.user.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.megift.resources.Email;
import com.megift.user.entity.User;
import com.megift.user.logic.UserLogic;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static com.megift.resources.utils.Validator.isEmail;
import static com.megift.resources.utils.Constants.EMAIL_SERVER_NAME;
import static com.megift.resources.Email.Resource;

public class UserControl extends Controller {

	
    public static Result landing() {
        return ok(views.html.user.landing.render());
    }
    
    public static Result registerUser(){
    	String result = null;
    	User user = null;
    	final Map<String, String[]> req =  request().body().asFormUrlEncoded();
    	user = new User(req.get("user-name")[0], req.get("user-email")[0]);
    	if(!user.isEmpty() && isEmail(user.getEmail())){
    		if(!UserLogic.existsUser(user)){
        		if(UserLogic.registerUser(user)){
        			result = String.valueOf(UserLogic.countUsers());
        		}else{
        			result = "Error al registrar el usuario";
        		}
    		}else{
    			result = "Este usuario ya estaba registrado!";	
    		}
    	}else{
    		result = "Ingrese un correo valido!";
    	}
    	return ok(result);
    }
    
    public static Result countUsers(){
    	return ok(String.valueOf(UserLogic.countUsers()));
    }
    
    public static Result users(){
    	return ok(views.html.user.users.render());
    }
        
    public static Result loadUsers(){
    	return ok(Json.toJson(UserLogic.loadUsers()));
    }
    
    public static Result exportUsersToExcel(){    	
    	try {
    		File file = UserLogic.generateExcelUsers();
    		if (file!=null){
        		response().setContentType("application/x-download");
        		response().setHeader("Content-Disposition", "attachment; filename=mydata.xlsx");    		
    			return ok(file);    			
    		}else{
    			return ok("No  hay datos para exportar!");
    		}
		} catch (IOException e) {
			Logger.error("Error tryining generate excel users \n"+e.getMessage());
			return badRequest("Error tryining generate excel users \n"+e.getMessage());
		} catch (InvalidFormatException e) {
			Logger.error("Invalid format exception tryning generate User excel \n"+e.getMessage());
			return badRequest("Invalid format exception tryning generate User excel \n"+e.getMessage());
		}
    }
    
    public static Result sendEmail(){
    	try {
			try {
				new Email(EMAIL_SERVER_NAME, "gerencia@megift.co", "daniel1986").sendHTMLMail("gerencia@megift.co", "Megift", new String[]{"yonax73@gmail.com","yonatanalexis22@hotmail.com"}, 
						new String[]{"Yonax", "yonatan alexis"}, "Bienevenido", "<html><body><table><tr><td>Image 1</td><td>Image 2</td></tr><tr><td>" +
					            "<img src='${0}'/></td><td><img src='${1}'/></td></tr></table></body></html>", "alternate HTML", new Resource[]{new Resource("http://www.google.com/intl/en_ALL/images/logo.gif", "Google Logo 1"),
				        new Resource("http://www.google.com/intl/en_ALL/images/logo.gif", "Google Logo 2")});
			} catch (MalformedURLException e) {
				Logger.error("Error tryning send register email \n"+e.getMessage());
				return badRequest("Error tryning send register email \n"+e.getMessage());
			}
			
		} catch (EmailException e) {
			Logger.error("Error tryning send register email \n"+e.getMessage());
			return badRequest("Error tryning send register email \n"+e.getMessage());
		}
    	
    	return ok("send");
    }
}
