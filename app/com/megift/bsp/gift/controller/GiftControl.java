/**
 * 
 */
package com.megift.bsp.gift.controller;

import static com.megift.resources.utils.Constants.SESSION_BUSINESS_ID;
import static com.megift.resources.utils.Constants.SESSION_LOGIN_ID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.action.logic.ActionLogic;
import com.megift.bsp.business.entity.Business;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.gift.logic.GiftLogic;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.pos.entity.POS;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.entity.Location;
import com.megift.set.location.geolocation.entity.Geolocation;
import com.megift.set.master.entity.MasterValue;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * created : Mar 3, 2015<br/>
 * update date : Mar 3, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class GiftControl extends Controller {

	public static Result giftList() {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		return ok(views.html.bsp.gift.giftList.render());

	}

	public static Result gift() {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		return ok(views.html.bsp.gift.gift.render());

	}

	public static Result createGift() {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			Gift gift = new Gift(0);
			String pos[] = data.get("id-pos");
			int n = pos.length;
			if (n > 0) {
				List<POS> posList = new ArrayList<POS>();
				for (int i = 0; i < n; i++) {
					POS p = new POS(Integer.parseInt(pos[i]));
					if (p.exists()) {
						posList.add(p);
					}
				}
				gift.setPosList(posList);
				Action action = new Action(0);
				if (ActionLogic.createAction(action)) {
					gift.setAction(action);
					if (GiftLogic.createGift(gift)) {
						result = String.valueOf(gift.getId());
					} else {
						result = "Error creando el regalo";
					}
				} else {
					result = "Error creando la acción";
				}

			} else {
				result = "No hay Puntos de venta para guardar";
			}
		}
		return ok(result);
	}

	public static Result updateGift() {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			Gift gift = new Gift(Integer.parseInt(data.get("id-gift")[0]));
			Action action = new Action(Integer.parseInt(data.get("id-action")[0]));
			action.setName(data.get("name-action")[0]);
			action.setType(new MasterValue(Integer.parseInt(data.get("action-type")[0])));
			action.setDescription(data.get("description-action")[0]);
			if (action.isOtherType()) {
				action.setOtherType(data.get("other-action-type")[0]);
			}
			action.setPrice(Double.parseDouble(data.get("price-action")[0]));
			if (ActionLogic.update(action)) {
				gift.setName(data.get("name-gift")[0]);
				gift.setType(new MasterValue(Integer.parseInt(data.get("gift-type")[0])));
				if (gift.isOtherType()) {
					gift.setOtherType(data.get("other-gift-type")[0]);
				}
				gift.setPrice(Double.parseDouble(data.get("price-gift")[0]));
				gift.setStartDate((LocalDate.parse(data.get("start-date-gift")[0], DateTimeFormatter.ofPattern("dd-MM-yyyy"))).atStartOfDay());
				gift.setExpirationDate((LocalDate.parse(data.get("end-date-gift")[0], DateTimeFormatter.ofPattern("dd-MM-yyyy"))).atStartOfDay());
				gift.setStatus(new MasterValue(Integer.parseInt(data.get("gift-status")[0])));
				gift.setDescription(data.get("description-gift")[0]);
				gift.setAction(action);
				if (GiftLogic.update(gift)) {
					result = Json.toJson(gift).toString();

				} else {
					result = "Error guardando el regalo!";
				}

			} else {
				result = "Error guardando la acción!";
			}
		}
		return ok(result);
	}

	public static Result loadGift(int id) {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		Gift gift = new Gift(id);
		if (GiftLogic.load(gift)) {
			return ok(Json.toJson(gift));
		} else {
			return ok("Error cargando los datos del regalo");
		}
	}

	public static Result loadGiftByBusiness() {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		Business business = new Business(Integer.parseInt(session(SESSION_BUSINESS_ID)));
		if (GiftLogic.loadGiftByBusiness(business)) {
			return ok(Json.toJson(business.getGiftList()));
		}
		return ok("No hay regalos para mostrar");
	}

	/*
	 * Todos los regalos que tiene este punto de venta y ademas trae tambien los
	 * regalos que no estan asociados a este punto de venta para poder ser
	 * asociados desde el cliente
	 */
	public static Result loadGiftsByPOS(int id) {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		Business business = new Business(Integer.parseInt(session(SESSION_BUSINESS_ID)));
		POS pos = new POS(id);
		if (GiftLogic.loadGiftsByPOS(business, pos)) {
			return ok(Json.toJson(business.getGiftList()));
		}
		return ok("No hay regalos para mostrar");
	}

	public static Result searchGift() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			Partner user = new Partner(Integer.parseInt(data.get("id-login")[0]));
			user.setLocation(new Location(new Address(new Geolocation(Double.parseDouble(data.get("latitude")[0]), Double.parseDouble(data.get("longitude")[0])))));
			if (GiftLogic.searchGift(user)) {
				result = Json.toJson(user.getPOSList()).toString();
			} else {
				result = "Error buscando regalos";
			}
		}
		return ok(result);
	}

	public static Result loadGiftForMobile(int idPOS, int idGift) {
		response().setHeader("Access-Control-Allow-Origin", "*");
		String result = "No se ha podido completar la solicitud";
		Business business = new Business(new POS(idPOS, new Gift(idGift)));
		if (GiftLogic.load(business)) {
			result = Json.toJson(business.getPos()).toString();
		} else {
			result = "Error cargando el regalo ";
		}
		return ok(result);
	}

}
