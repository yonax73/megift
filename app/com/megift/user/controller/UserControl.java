package com.megift.user.controller;

import static com.megift.resources.utils.Constants.CACHE_SOCIAL_AUTH;
import static com.megift.resources.utils.Validator.isEmail;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import play.Logger;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.resources.oauth.OAuth;
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
    
    public static Result countUsers() {
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
    
    public static Result oAuth(String id){
    	try {
    		OAuth socialAuth = new OAuth(id);
    		if (socialAuth.authenticate()) {
            	Cache.set(CACHE_SOCIAL_AUTH, socialAuth,60*5);	
            	return redirect(socialAuth.getAuthURL());
			}
		} catch (Exception e) {
			Logger.error("Error tryning authenticate \n"+e.getMessage());
			return badRequest("Error tryning authenticate \n"+e.getMessage());
		}
    	return badRequest("Error tryning authenticate");
    }
    
    public static Result successAuth(){      	
    	OAuth socialAuth = (OAuth) Cache.get(CACHE_SOCIAL_AUTH);
    	if(socialAuth.loadParamsMap(request().queryString().entrySet())){
    		if(UserLogic.sendInviteToContacts(socialAuth.getProfile(), socialAuth.getContactsList())){
    			return redirect("/");	
    		}
    	}
    	return badRequest("Error tryning load the authenticate");
    }

    

}
