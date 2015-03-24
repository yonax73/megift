package com.megift.resources.utils;

import java.util.List;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.gift.entity.Gift;

public class Utils {

	public static String concatGiftIds(List<Gift> gifts) {
		StringBuffer buffer = new StringBuffer();
		for (Gift gift : gifts) {
			buffer.append(gift.getId());
			buffer.append(",");
		}
		return buffer.toString();
	}

	public static String concatActionIds(List<Action> actions) {
		StringBuffer buffer = new StringBuffer();
		for (Action action : actions) {
			buffer.append(action.getId());
			buffer.append(",");
		}
		return buffer.toString();
	}
}
