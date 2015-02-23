package com.megift.bsp.partner.controller;

import java.util.Map;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.partner.logic.PartnerLogic;
import com.megift.sec.login.entity.Login;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * update date : Feb 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Feb 19, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class PartnerControl extends Controller {

	public static Result loadPartner() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        final Map<String, String[]> data = request().body().asFormUrlEncoded();
        int idLogin = Integer.parseInt(data.get("id-login")[0]);
		String result = null;
		if (idLogin > 0) {
			Partner partner = new Partner(new Login(idLogin));
			if (PartnerLogic.loadPartner(partner)) {
				result = Json.toJson(partner).toString();
			} else {
				result = "Error cargando perfil de usuario";
			}
		}
		return ok(result);
	}
}
