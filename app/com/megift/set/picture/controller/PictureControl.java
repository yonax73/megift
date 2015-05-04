package com.megift.set.picture.controller;

import static com.megift.resources.utils.Constants.IMAGES_GIFTS_PATH;
import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;
import static com.megift.resources.utils.Utils.uploadFile;
import static com.megift.set.picture.entity.Picture.BASE64_CODING;

import java.util.ArrayList;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.business.entity.Business;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.utils.Constants;
import com.megift.set.picture.entity.Picture;
import com.megift.set.picture.logic.PictureLogic;

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
public class PictureControl extends Controller {

	public static Result uploadpictureBusiness() {
		try {
			String result = null;
			FilePart file = request().body().asMultipartFormData().getFile("picture");
			if (file != null) {
				Business business = new Business(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-business")[0]));
				Picture picture = new Picture(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-picture-business")[0]), file.getFile());
				picture.setMime(file.getContentType());
				picture.setName(file.getFilename());
				picture.setCoding(BASE64_CODING);
				business.setPicture(picture);
				if (uploadFile(picture, Constants.IMAGES_BUSINESSES_PATH)) {
					if (PictureLogic.savePictureBusiness(business)) {
						result = SUCCESS_RESPONSE;
					} else {
						result = "Error intentado subir la imagen";
					}
				} else {
					result = "Error intentando subir la imagen al servidor";
				}
			} else {
				result = "No hay ningun archivo para subir";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentado subir la foto de la empresa \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentado subir la la foto de la empresa ( " + e.getMessage() + " )");
		}
	}

	public static Result uploadpicturePartner() {
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			String result = null;
			FilePart file = request().body().asMultipartFormData().getFile("file");
			if (file != null) {
				Partner partner = new Partner(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("idPartner")[0]));
				Picture picture = new Picture(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("idPicture")[0]), file.getFile());
				picture.setMime(file.getContentType());
				picture.setName(file.getFilename());
				picture.setCoding(BASE64_CODING);
				partner.setPicture(picture);
				if (uploadFile(picture, Constants.IMAGES_PARTNERS_PATH)) {
					if (PictureLogic.savePicturePartner(partner)) {
						result = picture.getPath();
					} else {
						result = "Error intentado subir la imagen";
					}
				} else {
					result = "Error intentando subir la imagen al servidor";
				}
			} else {
				result = "No hay ningun archivo para subir";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentado subir la foto del usuario \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentado subir la foto del usuario ( " + e.getMessage() + " )");
		}
	}

	public static Result uploadpictureGift() {
		try {
			String result = null;
			FilePart file = request().body().asMultipartFormData().getFile("picture");
			if (file != null) {
				Gift gift = new Gift(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-gift")[0]));
				Picture picture = new Picture(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-picture-gift")[0]), file.getFile());
				picture.setMain(true);
				picture.setMime(file.getContentType());
				picture.setName(file.getFilename());
				picture.setCoding(BASE64_CODING);
				gift.setPictures(new ArrayList<Picture>());
				gift.getPictures().add(picture);
				if (uploadFile(picture, IMAGES_GIFTS_PATH)) {
					if (PictureLogic.savePictureCollection(gift)) {
						result = SUCCESS_RESPONSE;
					} else {
						result = "Error intentado subir la imagen";
					}
				} else {
					result = "Error intentando subir la imagen al servidor";
				}

			} else {
				result = "No hay ningun archivo para subir";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentado subir la foto del regalo \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentado subir la foto del regalo ( " + e.getMessage() + " )");
		}
	}

	public static Result uploadpictureAction() {
		try {
			String result = null;
			FilePart file = request().body().asMultipartFormData().getFile("picture");
			if (file != null) {
				Action action = new Action(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-action")[0]));
				Picture picture = new Picture(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-picture-action")[0]), file.getFile());
				picture.setMain(true);
				picture.setMime(file.getContentType());
				picture.setName(file.getFilename());
				picture.setCoding(BASE64_CODING);
				action.setPictures(new ArrayList<Picture>());
				action.getPictures().add(picture);
				if (uploadFile(picture, Constants.IMAGES_ACTIONS_PATH)) {
					if (PictureLogic.savePictureCollection(action)) {
						result = SUCCESS_RESPONSE;
					} else {
						result = "Error intentado subir la imagen";
					}
				} else {
					result = "Error intentando subir la imagen al servidor";
				}

			} else {
				result = "No hay ningun archivo para subir";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentado subir la foto de la acción \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentado subir la foto de la acción ( " + e.getMessage() + " )");
		}
	}

}
