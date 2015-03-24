/**
 * 
 */
package com.megift.bsp.business.controller;

import static com.megift.resources.utils.Constants.SESSION_BUSINESS_ID;
import static com.megift.resources.utils.Constants.SESSION_LOGIN_ID;
import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.business.entity.Business;
import com.megift.bsp.business.logic.BusinessLogic;
import com.megift.bsp.gift.logic.GiftLogic;
import com.megift.bsp.partner.entity.Partner;
import com.megift.bsp.partner.logic.PartnerLogic;
import com.megift.bsp.pos.entity.POS;
import com.megift.sec.login.entity.Login;
import com.megift.sec.login.logic.LoginLogic;
import com.megift.set.document.entity.Document;
import com.megift.set.document.logic.DocumentLogic;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.address.logic.AddressLogic;
import com.megift.set.location.entity.Location;
import com.megift.set.location.geolocation.entity.Geolocation;
import com.megift.set.location.logic.LocationLogic;
import com.megift.set.location.phone.entity.Phone;
import com.megift.set.location.phone.logic.PhoneLogic;
import com.megift.set.master.entity.MasterValue;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * created : Feb 27, 2015<br/>
 * update date : Feb 27, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class BusinessControl extends Controller {

	public static Result business() {
		return ok(views.html.bsp.business.business.render());
	}

	public static Result saveBusiness() {
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			Business business = new Business(Integer.parseInt(data.get("id-business")[0]));
			Partner contact = new Partner(Integer.parseInt(data.get("id-contact")[0]));
			boolean allowed = false;
			if (business.exists()) {
				allowed = true;
			} else {
				Login login = new Login(data.get("email-contact")[0], data.get("password-contact")[0]);
				login.setType(new MasterValue(Login.BUSINESS_TYPE));
				if (LoginLogic.exists(login)) {
					result = "Este usuario usuario ya se encuentra registrado";
					allowed = false;
				} else {
					if (LoginLogic.create(login)) {
						contact.setLogin(login);
						allowed = true;
					} else {
						result = "Error creando el login!";
						allowed = false;
					}
				}
			}
			if (allowed) {
				Document doc = new Document(Integer.parseInt(data.get("id-document-legal-representative")[0]));
				doc.setDocument(data.get("document-legal-representative-business")[0]);
				doc.setType(new MasterValue(Integer.parseInt(data.get("document-type")[0])));
				doc.setPlaceOfIssue(data.get("place-of-issue-legal-representative-business")[0]);
				if (DocumentLogic.save(doc)) {
					Partner legalRepresentative = new Partner(Integer.parseInt(data.get("id-legal-representative")[0]));
					legalRepresentative.setName(data.get("name-legal-representative-business")[0]);
					legalRepresentative.setDocument(doc);
					if (PartnerLogic.save(legalRepresentative)) {
						Phone phone = new Phone(Integer.parseInt(data.get("id-phone-contact")[0]));
						phone.setNumber(data.get("phone-contact")[0]);
						phone.setExtension(data.get("extension-phone-contact")[0]);
						phone.setMobile(data.get("mobile-phone-contact")[0]);
						if (PhoneLogic.save(phone)) {
							Address address = new Address(Integer.parseInt(data.get("id-address-contact")[0]));
							address.setAddress(data.get("address-contact")[0]);
							if (AddressLogic.save(address)) {
								Location location = new Location(Integer.parseInt(data.get("id-location-contact")[0]));
								location.setPhone(phone);
								location.setAddress(address);
								if (LocationLogic.save(location)) {
									contact.setName(data.get("name-contact")[0]);
									contact.setLocation(location);
									if (PartnerLogic.save(contact)) {
										business.setTradeName(data.get("trade-name-business")[0]);
										business.setLegalName(data.get("legal-name-business")[0]);
										business.setNIT(data.get("nit-business")[0]);
										business.setType(new MasterValue(Integer.parseInt(data.get("business-type")[0])));
										if (business.isOtherType()) {
											business.setOtherType(data.get("other-business-type")[0]);
										}
										business.setLegalRepresentative(legalRepresentative);
										business.setContact(contact);
										if (BusinessLogic.save(business)) {
											result = SUCCESS_RESPONSE;
										} else {
											result = "Error guardando la empresa";
										}
									} else {
										result = "Error guardando el contacto";
									}

								} else {
									result = "Error guardando la localización";
								}
							} else {
								result = "Error guardando la dirección!";
							}
						} else {
							result = "Error guardando los teléfonos";
						}

					} else {
						result = "Error guardando el representante legal";
					}
				} else {
					result = "Error guardando el documento";
				}
			}
		}

		return ok(result);
	}

	public static Result loadBusiness() {
		if (session(SESSION_LOGIN_ID) == null) {
			return redirect("/login");
		}
		Business business = new Business(0);
		business.setContact(new Partner(new Login(Integer.parseInt(session(SESSION_LOGIN_ID)))));
		business = BusinessLogic.load(business);
		session(SESSION_BUSINESS_ID, String.valueOf(business.getId()));
		return ok(Json.toJson(business));
	}

	public static Result loadBusinessForMobile(int id, double lat, double lng) {
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			String result = "No se ha podido completar la solicitud";
			Business business = new Business(id);
			if (BusinessLogic.loadById(business) != null) {
				Partner user = new Partner(0);
				user.setLocation(new Location(new Address(new Geolocation(lat, lng))));
				user.setPos(new POS());
				user.getPos().setBussinesId(id);
				if (GiftLogic.searchGift(user)) {
					business.setPosList(user.getPOSList());
					result = Json.toJson(business).toString();
				} else {
					result = "Error cargando los regalos de la empresa ";
				}
			} else {
				result = "Error cargando la empresa ";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar la empresa \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando cargar la empresa ( " + e.getMessage() + " )");
		}
	}
}
