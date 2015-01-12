package com.megift.user.controller;

import java.util.Map;

import com.megift.user.entity.User;
import com.megift.user.logic.UserLogic;

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
}
