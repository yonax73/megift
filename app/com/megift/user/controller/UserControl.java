package com.megift.user.controller;

import static com.megift.resources.utils.Validator.isEmail;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.user.entity.User;
import com.megift.user.logic.UserLogic;


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
        			result = "Error al registrar el usuario, por favor intentelo mas tarde!";
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
    
    public static Result importContacts(){    	
    	try {
    		SocialAuthConfig config = SocialAuthConfig.getDefault();
			config.load();
			SocialAuthManager manager = new SocialAuthManager();
			manager.setSocialAuthConfig(config);
			String successUrl = "http://opensource.brickred.com/socialauthdemo/socialAuthSuccessAction.do";
  		    // get Provider URL to which you should redirect for authentication.
		    // id can have values "facebook", "twitter", "yahoo" etc. or the OpenID URL
			String url = manager.getAuthenticationUrl("google", successUrl);
			
		} catch (Exception e) {
			Logger.error("Error tryning import contacts \n"+e.getMessage());
			return badRequest("Error tryning import contacts \n"+e.getMessage());
		}
    	return ok();
    }
    

}
