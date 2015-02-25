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
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.address.logic.AddressLogic;
import com.megift.set.location.city.entity.City;
import com.megift.set.location.city.logic.CityLogic;
import com.megift.set.location.entity.Location;
import com.megift.set.location.logic.LocationLogic;
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
		String result = "Error al recibir la petición";
		if (data != null) {
			City city = new City(Integer.parseInt(data.get("id-city")[0]), data.get("city-partner")[0]);
			if (CityLogic.save(city)) {
				Address address = new Address(city);
				if (AddressLogic.save(address)) {
					Location location = new Location(Integer.parseInt(data.get("id-location")[0]));
					location.setAddress(address);
					if (LocationLogic.save(location)) {
						Partner partner = new Partner(Integer.parseInt(data.get("id-partner")[0]));
						partner.setName(data.get("name-partner")[0]);
						partner.setBirthday(LocalDate.parse(data.get("birthday-partner")[0], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
						partner.setGender(new MasterValue(Integer.parseInt(data.get("gender-partner")[0])));
						partner.setLocation(location);
						if (PartnerLogic.update(partner)) {
							result = SUCCESS_RESPONSE;
						} else {
							result = "Error actualizando el usuario";
						}
					} else {
						result = "Error guardando la localización";
					}
				} else {
					result = "Error guardando la dirección";
				}
			} else {
				result = "Error guardando la ciudad";
			}
		}
		return ok(result);
	}

}
