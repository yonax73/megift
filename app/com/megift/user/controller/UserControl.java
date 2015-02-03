package com.megift.user.controller;

import static com.megift.resources.utils.Validator.isEmail;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

import play.Logger;
import play.cache.Cache;
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
    
    public static Result social(){
    	return ok(views.html.user.social.render());
    }
    
    public static Result oAuth(){
    	SocialAuthManager manager; 
		String originalURL; 
		String providerID = "google"; 
		User profile;
		String authURL ;
    	try {
    		Properties properties = System.getProperties();
    		properties.put("www.google.com.consumer_key","AIzaSyBByQecsZL5lhqRBEvy3-5nSeiEtdlmGfs");
    		properties.put("www.google.com.consumer_secret","notasecret");    		
    		SocialAuthConfig config = SocialAuthConfig.getDefault(); 
    		config.load(properties); 
    		manager = new SocialAuthManager(); 
    		manager.setSocialAuthConfig(config);    		
    		authURL =  manager.getAuthenticationUrl(providerID, "/successAuth");
    		Cache.set("authManager", manager, 1000);    		
		} catch (Exception e) {
			Logger.error("Error tryning import contacts \n"+e.getMessage());
			return badRequest("Error tryning import contacts \n"+e.getMessage());
		}
    	return redirect(authURL);
    }
    
    public static Result successAuth(){
    	return ok(views.html.user.successAuth.render());
    }
    
    public static Result importContacts(){
    	String str = request().body().asText();    	
    	SocialAuthManager manager = (SocialAuthManager) Cache.get("authManager"); 
    	return ok(str);
    }
    

}
