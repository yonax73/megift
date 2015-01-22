package com.megift.user.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.megift.user.entity.User;
import com.megift.user.logic.UserLogic;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class UserControl extends Controller {

	
    public static Result landing() {
        return ok(views.html.user.landing.render());
    }
    
    public static Result registerUser(){
    	String result = null;
    	User user = null;
    	final Map<String, String[]> req =  request().body().asFormUrlEncoded();
    	user = new User(req.get("user-name")[0], req.get("user-email")[0]);
    	if(!user.isEmpty()){
    		if(UserLogic.registerUser(user)){
    			result = String.valueOf(UserLogic.countUsers());
    		}else{
    			result = "Error al registrar el usuario";
    		}
    	}else{
    		result = "El correo es requerido!";
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
}
