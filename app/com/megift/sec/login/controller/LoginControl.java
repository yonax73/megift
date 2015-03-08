package com.megift.sec.login.controller;

import static com.megift.resources.utils.Constants.CHECKED;
import static com.megift.resources.utils.Constants.SESSION_LOGIN_ID;
import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;

import java.util.Map;

import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.partner.logic.PartnerLogic;
import com.megift.resources.social.logic.SocialLogic;
import com.megift.sec.login.entity.Login;
import com.megift.sec.login.logic.LoginLogic;
import com.megift.set.master.entity.MasterValue;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * date : Feb 19, 2015<br/>
 * update date : Feb 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class LoginControl extends Controller {

	public static Result createAccount() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		Partner partner = null;
		String result = null;
		Login login = null;
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			login = new Login(data.get("email-partner")[0], data.get("password-partner")[0]);
			login.setType(new MasterValue(Login.USER_TYPE));
			if (LoginLogic.exists(login)) {
				result = "Este usuario ya se encuentra registrado!";
			} else {
				if (LoginLogic.create(login)) {
					partner = new Partner(data.get("name-partner")[0]);
					partner.setLogin(login);
					if (PartnerLogic.create(partner)) {
						result = SUCCESS_RESPONSE;
					} else {
						result = "Error tryning create Partner";
					}
				} else {
					result = "Error tryning create login!";
				}
			}

		} else {
			result = "request without data";
		}
		return ok(result);
	}

	public static Result signIn() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		Login login = null;
		String result = null;
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			login = new Login(data.get("email-login")[0], data.get("password-login")[0]);
			login.setType(new MasterValue(Integer.parseInt(data.get("business-type")[0])));
			if (LoginLogic.signIn(login)) {
				if (login.isBusinessType()) {
					session(SESSION_LOGIN_ID, String.valueOf(login.getId()));
					result = SUCCESS_RESPONSE;
				} else {
					result = String.valueOf(login.getId());
				}

			} else {
				result = "El email o la contraseña es incorrecta!";
			}
		} else {
			result = "request without data";
		}
		return ok(result);
	}

	public static Result passwordChangeRequest() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			Login login = new Login(data.get("email-login")[0], null);
			if (data.get("is-business")[0] != null && data.get("is-business")[0].equals(CHECKED)) {
				login.setType(new MasterValue(Login.BUSINESS_TYPE));
			} else {
				login.setType(new MasterValue(Login.USER_TYPE));
			}
			if (LoginLogic.exists(login)) {
				if (LoginLogic.createPasswordChangeRequest(login)) {
					if (SocialLogic.sendPasswordChangeRequest(login)) {
						result = SUCCESS_RESPONSE;
					} else {
						result = "Error al enviar la peteción al correo";
					}
				} else {
					result = "Error al crear la petición para cambio de contraseña";
				}
			} else {
				result = "Este usuario no esta registrado!";
			}
		}

		return ok(result);
	}

	public static Result passwordChange(int idLogin, int codeRequest) {
		String result = "No se ha podido completar la solicitud";
		if (idLogin > 0 && codeRequest > 0) {
			Login login = new Login(idLogin);
			login.setCodeRequest(codeRequest);
			if (LoginLogic.existsPasswordChangeRequest(login)) {
				session("login", String.valueOf(idLogin));
				session("codeRequest", String.valueOf(codeRequest));
				return passwordChangeSafely();
			} else {
				result = "La solicitud ha expirado!";
			}
		}
		return ok(result);
	}

	public static Result passwordChangeSafely() {
		return ok(views.html.sec.login.passwordChange.render());

	}

	public static Result passwordReset() {
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			String password = data.get("password-login")[0];
			if (password != null) {
				Login login = new Login(Integer.parseInt(session("login")));
				login.setPassword(password);
				login.setCodeRequest(Integer.parseInt(session("codeRequest")));
				if (LoginLogic.deletePasswordChangeRequest(login)) {
					session().clear();
					if (LoginLogic.passwordReset(login)) {
						result = SUCCESS_RESPONSE;
					} else {
						result = "La contasña no se pudo cambiar";
					}
				} else {
					result = "La solicitud de cambio de contraseña no se pudo completar";
				}
			} else {
				result = "El password esta vacio, por favor verifique!";
			}
		}
		return ok(result);
	}

	public static Result login() {
		session().clear();
		return ok(views.html.sec.login.login.render());

	}

	public static Result signOut() {
		return redirect("/login");
	}

	public static Result termsAndConditions() {
		return ok(views.html.sec.login.termsAndConditions.render());

	}

	public static Result privacyPolicy() {
		return ok(views.html.sec.login.privacyPolicy.render());

	}

	public static Result recoveryPassword() {
		return ok(views.html.sec.login.recoveryPassword.render());

	}

}
