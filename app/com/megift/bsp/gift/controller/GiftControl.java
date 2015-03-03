/**
 * 
 */
package com.megift.bsp.gift.controller;

import java.util.Map;

import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.gift.entity.Gift;

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

	public static Result saveGift() {
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {

			Gift gift = new Gift(Integer.parseInt(data.get("id-gift")[0]));
			gift.setName(data.get("name-gift")[0]);
			// gift

			// name-gift
			// gift-type
			// other-gift-type
			// price-gift
			// start-date-gift
			// end-date-gift
			// gift-status
			// name-action
			// action-type
			// other-action-type
			// price-action
			// id-gift
			// id-action
			// id-pos-list

		}
		return ok(result);
	}
}
