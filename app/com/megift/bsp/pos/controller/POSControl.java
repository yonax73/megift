/**
 * 
 */
package com.megift.bsp.pos.controller;

import static com.megift.resources.utils.Constants.SESSION_BUSINESS_ID;

import java.util.List;
import java.util.Map;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.business.entity.Business;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.partner.logic.PartnerLogic;
import com.megift.bsp.pos.entity.POS;
import com.megift.bsp.pos.logic.POSLogic;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.address.logic.AddressLogic;
import com.megift.set.location.entity.Location;
import com.megift.set.location.geolocation.entity.Geolocation;
import com.megift.set.location.geolocation.logic.GeolocationLogic;
import com.megift.set.location.logic.LocationLogic;
import com.megift.set.location.phone.entity.Phone;
import com.megift.set.location.phone.logic.PhoneLogic;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * created : Mar 2, 2015<br/>
 * update date : Mar 2, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class POSControl extends Controller {

	public static Result POSList() {
		return ok(views.html.bsp.POS.POSList.render());
	}

	public static Result POS() {
		return ok(views.html.bsp.POS.POS.render());
	}

	public static Result savePOS() {
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();

		if (data != null && session(SESSION_BUSINESS_ID) != null) {
			Geolocation geolocation = new Geolocation(Integer.parseInt(data.get("id-geolocation-pos")[0]));
			geolocation.setLongitude(Double.parseDouble(data.get("longitude-address-pos")[0]));
			geolocation.setLatitude(Double.parseDouble(data.get("latitude-address-pos")[0]));
			if (GeolocationLogic.save(geolocation)) {
				Address address = new Address(Integer.parseInt(data.get("id-address-pos")[0]));
				address.setAddress(data.get("address-pos")[0]);
				address.setGeolocation(geolocation);
				if (AddressLogic.save(address)) {
					Phone phone = new Phone(Integer.parseInt(data.get("id-phone-pos")[0]));
					phone.setNumber(data.get("phone-pos")[0]);
					phone.setExtension(data.get("extension-phone-pos")[0]);
					phone.setMobile(data.get("mobile-phone-pos")[0]);
					if (PhoneLogic.save(phone)) {
						Location location = new Location(Integer.parseInt(data.get("id-location-pos")[0]));
						location.setAddress(address);
						location.setPhone(phone);
						if (LocationLogic.save(location)) {
							Partner contact = new Partner(Integer.parseInt(data.get("id-contact")[0]));
							contact.setName(data.get("name-contact")[0]);
							if (PartnerLogic.save(contact)) {
								POS pos = new POS(Integer.parseInt(data.get("id-POS")[0]));
								pos.setName(data.get("name-POS")[0]);
								pos.setBussinesId(Integer.parseInt(session(SESSION_BUSINESS_ID)));
								pos.setContact(contact);
								pos.setLocation(location);
								if (POSLogic.save(pos)) {
									result = Json.toJson(pos).toString();
								} else {
									result = "error guardando el Punto de venta!";
								}
							} else {
								result = "Error guardando el contacto";
							}
						} else {
							result = "Error guardando la localización";
						}
					} else {
						result = "Error guardando los teléfonos";
					}
				} else {
					result = "Error guardando la dirección";
				}
			} else {
				result = "Error guardando la geolocalización";
			}
		}
		return ok(result);
	}

	public static Result loadPOSByBusiness() {
		List<POS> POSList = POSLogic.loadPOSByBusiness(new Business(Integer.parseInt(session(SESSION_BUSINESS_ID))));
		if (POSList != null && !POSList.isEmpty()) {
			return ok(Json.toJson(POSList));
		}
		return ok("No hay puntos de venta para mostrar");
	}

	public static Result loadPOS() {
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			POS pos = new POS(Integer.parseInt(data.get("id-POS")[0]));
			result = Json.toJson(POSLogic.Load(pos)).toString();
		}
		return ok(result);
	}
}
