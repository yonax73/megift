package com.megift.resources.social.controller;

import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;

import java.util.Map;

import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.social.logic.SocialLogic;
import com.megift.sec.login.entity.Login;

public class SocialControl extends Controller {

	public static Result inviteFriends() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		String result = "Error al recibir la petición";
		if (data != null) {
			String emailFriend1 = data.get("email-friend-1")[0];
			String emailFriend2 = data.get("email-friend-2")[0];
			String namePartner = data.get("name-partner")[0];
			String emailPartner = data.get("email-partner")[0];
			String emails[] = new String[2];
			if (emailFriend1 != null)
				emails[0] = emailFriend1;
			if (emailFriend2 != null)
				emails[1] = emailFriend2;
			if (emails.length > 0) {
				Partner partner = new Partner(namePartner);
				partner.setLogin(new Login(emailPartner, null));
				if (SocialLogic.inviteFriendsByEmail(partner, emails)) {
					result = SUCCESS_RESPONSE;
				} else {
					result = "Error al enviar invitaciones por correo";
				}
			} else {
				result = "No hay correos para invitar a tus amigos";
			}
		}
		return ok(result);
	}
}