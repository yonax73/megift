/**
 * 
 */
package com.megift.bsp.gift.controller;

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
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.gift.logic.GiftLogic;
import com.megift.bsp.pos.entity.POS;
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
		return ok(views.html.bsp.gift.giftList.render());
	}

	public static Result gift() {
		return ok(views.html.bsp.gift.gift.render());
	}

	public static Result createGift() {
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
					posList.add(p);
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
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			Gift gift = new Gift(Integer.parseInt(data.get("id-gift")[0]));
			Action action = new Action(Integer.parseInt(data.get("id-action")[0]));
			action.setName(data.get("name-action")[0]);
			action.setType(new MasterValue(Integer.parseInt(data.get("action-type")[0])));
			action.setDescription(data.get("description-action")[0]);
			if (action.isOtherType()) {
				action.setOtherType(data.get("other-type-action")[0]);
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
		Gift gift = new Gift(id);
		if (GiftLogic.load(gift)) {
			return ok(Json.toJson(gift));
		} else {
			return ok("Error cargando los datos del regalo");
		}

	}

}
