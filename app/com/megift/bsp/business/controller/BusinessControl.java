/**
 * 
 */
package com.megift.bsp.business.controller;

import play.mvc.Controller;
import play.mvc.Result;

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
}
