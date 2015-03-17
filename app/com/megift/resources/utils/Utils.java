package com.megift.resources.utils;

import java.util.List;

import com.megift.bsp.gift.entity.Gift;

public class Utils {

	public static String concatIds(List<Gift> gifts) {
		StringBuffer buffer = new StringBuffer();
		for (Gift gift : gifts) {
			buffer.append(gift.getId());
			buffer.append(",");
		}
		return buffer.toString();
	}
}
