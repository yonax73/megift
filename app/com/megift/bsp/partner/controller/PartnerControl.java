package com.megift.bsp.partner.controller;

import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.partner.logic.PartnerLogic;
import com.megift.sec.login.entity.Login;
import com.megift.sec.login.logic.LoginLogic;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.city.entity.City;
import com.megift.set.location.city.logic.CityLogic;
import com.megift.set.location.entity.Location;
import com.megift.set.master.entity.MasterValue;

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

	public static Result updatePartner() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		String result = null;
		if (data != null) {
			Login login = new Login(Integer.parseInt(data.get("id-login")[0]));
			if (login != null && login.exists()) {
				login.setEmail(data.get("email-partner")[0]);
				if (LoginLogic.update(login)) {
					Location location = new Location(Integer.parseInt(data.get("id-location")[0]));
					if (location != null && location.exists()) {
						location.setAddress(new Address(new City(Integer.parseInt(data.get("id-city")[0]), data.get("city-partner")[0])));
						if (location.getAddress().getCity() != null && location.getAddress().getCity().exists()) {
							if (CityLogic.update(location.getAddress().getCity())) {
								Partner partner = new Partner(Integer.parseInt(data.get("id-partner")[0]));
								if (partner != null && partner.exists()) {
									partner.setName(data.get("name-partner")[0]);
									partner.setBirthday(LocalDate.parse(data.get("birthday-partner")[0], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
									partner.setGender(new MasterValue(Integer.parseInt(data.get("gender-partner")[0])));
									partner.setLogin(login);
									partner.setLocation(location);
									if (PartnerLogic.update(partner)) {
										result = SUCCESS_RESPONSE;
									} else {
										result = "Error actualizando el usuario";
									}
								} else {
									result = "No existe usuario para guardar";
								}
							} else {
								result = "Error actualizando la ciudad";
							}
							;
						} else {
							result = "No existe ciudad para actualizar";
						}

					} else {
						result = "No existe localización para actualizar";
					}
				} else {
					result = "Error al actualizar el login!";
				}
			} else {
				result = "No existe Login para actualizar!";
			}

		}
		return ok(result);
	}
}
