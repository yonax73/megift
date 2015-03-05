package com.megift.set.picture.controller;

import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;
import static com.megift.set.picture.entity.Picture.BASE64_CODING;

import java.util.ArrayList;

import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.partner.entity.Partner;
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

	public static Result uploadpicturePartner() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		String result = null;
		FilePart file = request().body().asMultipartFormData().getFile("picture");
		if (file != null) {
			Partner partner = new Partner(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-partner")[0]));
			Picture picture = new Picture(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-picture")[0]), file.getFile());
			picture.setMime(file.getContentType());
			picture.setCoding(BASE64_CODING);
			partner.setPicture(picture);
			if (PictureLogic.savePicturePartner(partner)) {
				result = SUCCESS_RESPONSE;
			} else {
				result = "Error intentando subir la imagen";
			}
		} else {
			result = "No hay ningun archivo para subir";
		}
		return ok(result);
	}

	public static Result uploadpictureGift() {
		String result = null;
		FilePart file = request().body().asMultipartFormData().getFile("picture");
		if (file != null) {
			Gift gift = new Gift(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-gift")[0]));
			Picture picture = new Picture(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-picture-gift")[0]), file.getFile());
			picture.setMain(true);
			picture.setMime(file.getContentType());
			picture.setCoding(BASE64_CODING);
			gift.setPictures(new ArrayList<Picture>());
			gift.getPictures().add(picture);
			if (PictureLogic.savePictureCollection(gift)) {
				result = SUCCESS_RESPONSE;
			} else {
				result = "Error intentando subir la imagen";
			}
		} else {
			result = "No hay ningun archivo para subir";
		}
		return ok(result);
	}

	public static Result uploadpictureAction() {
		String result = null;
		FilePart file = request().body().asMultipartFormData().getFile("picture");
		if (file != null) {
			Action action = new Action(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-action")[0]));
			Picture picture = new Picture(Integer.parseInt(request().body().asMultipartFormData().asFormUrlEncoded().get("id-picture-action")[0]), file.getFile());
			picture.setMain(true);
			picture.setMime(file.getContentType());
			picture.setCoding(BASE64_CODING);
			action.setPictures(new ArrayList<Picture>());
			action.getPictures().add(picture);
			if (PictureLogic.savePictureCollection(action)) {
				result = SUCCESS_RESPONSE;
			} else {
				result = "Error intentando subir la imagen";
			}
		} else {
			result = "No hay ningun archivo para subir";
		}
		return ok(result);
	}

}
