/**
 * 
 */
package com.megift.bsp.term_and_condition.controller;

import java.util.ArrayList;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.bsp.gift.entity.Gift;
import com.megift.bsp.term_and_condition.TermAndCondition;
import com.megift.bsp.term_and_condition.logic.TermAndConditionLogic;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * created : Mar 5, 2015<br/>
 * update date : Mar 5, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class TermAndConditionControl extends Controller {

	public static Result loadTermsAndConditionGift(int id) {
		Gift gift = new Gift(id);
		gift.setTermsAndConditions(new ArrayList<TermAndCondition>());
		if (TermAndConditionLogic.loadTermsAndConditions(gift)) {
			return ok(Json.toJson(gift.getTermsAndConditions()));
		}
		return ok("Error cargando los terminos y condiciones");
	}

}
