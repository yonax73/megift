/**
 * 
 */
package com.megift.bsp.term_and_condition.controller;

import static com.megift.resources.utils.Constants.SUCCESS_RESPONSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public static Result saveTermnsAndConditions() {
		String result = "No se ha podido completar la solicitud";
		final Map<String, String[]> data = request().body().asFormUrlEncoded();
		if (data != null) {
			Gift gift = new Gift(Integer.parseInt(data.get("id-git-terms-and-conditions")[0]));
			String termsAndConditions[] = data.get("term-and-condition-txt");
			String ids[] = data.get("id-term-and-condition");
			int n = termsAndConditions.length;
			if (n > 0) {
				List<TermAndCondition> termAndConditionsList = new ArrayList<TermAndCondition>();
				for (int i = 0; i < n; i++) {
					TermAndCondition t = new TermAndCondition(Integer.parseInt(ids[i]), termsAndConditions[i]);
					if (!t.isEmpty()) {
						termAndConditionsList.add(t);
					}
				}
				gift.setTermsAndConditions(termAndConditionsList);
				if (TermAndConditionLogic.save(gift)) {
					result = SUCCESS_RESPONSE;
				} else {
					result = "Error guardando los terminos y condiciones";
				}
			}

		}
		return ok(result);
	}

	public static Result deleteTermAndCondition(int id) {
		String result = "No se ha podido completar la solicitud";
		TermAndCondition termAndCondition = new TermAndCondition(id);
		if (termAndCondition.exists()) {
			if (TermAndConditionLogic.delete(termAndCondition)) {
				result = SUCCESS_RESPONSE;
			} else {
				result = "Error eliminando termino y condición";
			}
		} else {
			result = SUCCESS_RESPONSE;
		}
		return ok(result);
	}

}
