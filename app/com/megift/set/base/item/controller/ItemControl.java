/**
 * 
 */
package com.megift.set.base.item.controller;

import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.megift.set.base.item.entity.Item;
import com.megift.set.base.item.logic.ItemLogic;
import com.megift.set.master.entity.Master;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Feb 28, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Feb 28, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class ItemControl extends Controller {

	public static Result documentTypeList() {
		List<Item> list = ItemLogic.listItemValue1(Master.DOCUMENT_TYPE);
		if (list != null)
			list.add(0, new Item(0, "--TIPO DE DOCUMENTO REP. LEGAL--"));
		return ok(Json.toJson(list).toString());
	}

	public static Result businessTypeList() {
		List<Item> list = ItemLogic.listItemValue1(Master.BUSINESS_TYPE);
		if (list != null)
			list.add(0, new Item(0, "--TIPO DE NEGOCIO--"));
		return ok(Json.toJson(list).toString());
	}

	public static Result giftTypeList() {
		List<Item> list = ItemLogic.listItemValue1(Master.GIFT_TYPE);
		if (list != null)
			list.add(0, new Item(0, "--TIPO DE REGALO--"));
		return ok(Json.toJson(list).toString());
	}

	public static Result giftStatusList() {
		List<Item> list = ItemLogic.listItemValue1(Master.GIFT_STATUS);
		return ok(Json.toJson(list).toString());
	}

	public static Result actionTypeList() {
		List<Item> list = ItemLogic.listItemValue1(Master.ACTION_TYPE);
		if (list != null)
			list.add(0, new Item(0, "--TIPO DE ACCIÓN--"));
		return ok(Json.toJson(list).toString());
	}
}
