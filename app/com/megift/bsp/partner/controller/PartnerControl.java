package com.megift.bsp.partner.controller;

import static com.megift.resources.utils.Constants.SESSION_LOGIN_ID;
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
		int loginId = Integer.parseInt(session().get(SESSION_LOGIN_ID));
		String result = null;
		if (loginId > 0) {
			Partner partner = new Partner(new Login(loginId));
			if (PartnerLogic.loadPartner(partner)) {
				result = Json.toJson(partner).toString();
			} else {
				result = "Error cargando perfil de usuario";
			}
		}

		return ok(result);
	}
}
