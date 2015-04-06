/**
 * 
 */
package com.megift.bsp.gift.controller;

import static com.megift.resources.utils.Constants.SESSION_BUSINESS_ID;
import static com.megift.resources.utils.Constants.SESSION_LOGIN_ID;
import static com.megift.set.setting.entity.Setting.RESULTS_BY_ACTIONS;
import static com.megift.set.setting.entity.Setting.RESULTS_BY_GIFTS;
import static com.megift.set.setting.entity.Setting.RESULTS_BY_POS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.action.logic.ActionLogic;
import com.megift.bsp.business.entity.Business;
import com.megift.bsp.business.logic.BusinessLogic;
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
				gift.setTermsConditions(data.get("termsConditions-gift")[0]);
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
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			String result = "No se ha podido completar la solicitud";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Partner user = new Partner(Integer.parseInt(data.get("id-login")[0]));
				user.setLocation(new Location(new Address(new Geolocation(Double.parseDouble(data.get("latitude")[0]), Double.parseDouble(data.get("longitude")[0])))));
				int typeSearch = Integer.parseInt(data.get("type-view")[0]);
				switch (typeSearch) {
				case RESULTS_BY_GIFTS:
					user.setGift(new Gift(0));
					user.getGift().setType(new MasterValue(Integer.parseInt(data.get("gift-type-id")[0])));
					if (GiftLogic.searchGift(user)) {
						if (user.getPOSList() != null && !user.getPOSList().isEmpty()) {
							result = Json.toJson(user.getPOSList()).toString();
						} else {
							result = "No hay Regalos cerca a tu ubicación con este filtro";
						}

					} else {
						result = "Aun no tenemos cobertura cerca de esta ubicación. Escríbenos a soporte@megift.co para ponernos a Trabajar en ello";
					}
					break;
				case RESULTS_BY_ACTIONS:
					user.setGift(new Gift(0));
					user.getGift().setAction(new Action(0));
					user.getGift().getAction().setType(new MasterValue(Integer.parseInt(data.get("action-type-id")[0])));
					if (ActionLogic.searchAction(user)) {
						if (user.getPOSList() != null && !user.getPOSList().isEmpty()) {
							result = Json.toJson(user.getPOSList()).toString();
						} else {
							result = "No hay acciones cerca a tu ubicación con este filtro";
						}

					} else {
						result = "Aun no tenemos cobertura cerca de esta ubicación. Escríbenos a soporte@megift.co para ponernos a Trabajar en ello";
					}
					break;
				case RESULTS_BY_POS:
					Business b = new Business(0);
					b.setType(new MasterValue(Integer.parseInt(data.get("business-type-id")[0])));
					List<Business> businesses = BusinessLogic.search(b);
					if (businesses != null) {
						if (!businesses.isEmpty()) {
							result = Json.toJson(businesses).toString();
						} else {
							result = "No hay Empresas cerca a tu ubicación con este filtro";
						}

					} else {
						result = "Aun no tenemos cobertura cerca de esta ubicación. Escríbenos a soporte@megift.co para ponernos a Trabajar en ello";
					}

					break;
				}

			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando buscar regalos \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando buscar regalos ( " + e.getMessage() + " )");
		}
	}

	public static Result loadGiftForMobile(int idPOS, int idGift, double lat, double lng) {
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			String result = "No se ha podido completar la solicitud";
			POS pos = new POS(idPOS, new Gift(idGift));
			Partner user = new Partner(0);
			user.setLocation(new Location(new Address(new Geolocation(lat, lng))));
			pos.setUser(user);
			Business business = new Business(pos);
			if (GiftLogic.load(business)) {
				result = Json.toJson(business).toString();
			} else {
				result = "Error cargando el regalo ";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar el regalo \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando cargar el regalo ( " + e.getMessage() + " )");
		}
	}

}
