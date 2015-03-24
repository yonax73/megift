/**
 * 
 */
package com.megift.set.setting.controller;

import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.partner.entity.Partner;
import com.megift.sec.login.entity.Login;
import com.megift.set.master.entity.MasterValue;
import com.megift.set.setting.entity.Setting;
import com.megift.set.setting.logic.SettingLogic;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Mar 22, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Mar 22, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class SettingControl extends Controller {

	public static Result saveSearchSetting() {
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			String result = "No se ha podido completar la solicitud";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Partner user = new Partner(new Login(Integer.parseInt(data.get("id-login")[0])));
				Setting setting = new Setting(Integer.parseInt(data.get("id-setting")[0]));
				setting.setSearchSetting(new MasterValue(Integer.parseInt(data.get("type-view")[0])));
				user.setSettings(setting);
				if (SettingLogic.saveSearchSetting(user)) {
					result = SUCCESS_RESPONSE;
				} else {
					result = "Ha ocurrido un error intentado guardar la configuración de búsqueda";
				}
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando guardar la configuración de búsqueda \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando la configuración de búsqueda( " + e.getMessage() + " )");
		}
	}

	public static Result loadSettings(int id) {
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			String result = "No se ha podido completar la solicitud";
			Partner user = new Partner(new Login(id));
			if (SettingLogic.load(user)) {
				result = Json.toJson(user).toString();
			} else {
				result = "Ha ocurrido un error intentado cargar la configuración";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar la configuración\n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error cargar la configuración ( " + e.getMessage() + " )");
		}
	}
}
