package com.megift.resources.utils;

public class Constants {
	public final static String DATA_TEMP_PATH = "public/tmp/data/";

	public final static String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm";
	public final static String DATE_FORMATTER = "dd-MM-yyyy";

	public static final String CACHE_SOCIAL_AUTH = "AUTH_MANAGER";

	public static final String SUCCESS_RESPONSE = "OK";

	public static final String CHECKED = "on";

	public static final String SESSION_LOGIN_ID = "SESSION-LOGIN-ID";
	public static final String SESSION_BUSINESS_ID = "BUSINESS-LOGIN-ID";

	public static final double KILOMETER_TO_METERS = 1000;// 1 KM IS EQUAL TO
															// 1000 METERS

	/*
	 * This function converts decimal degrees to radians
	 */
	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*
	 * This function converts radians to decimal degrees
	 */
	public static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public static double convertKilometersToMeteres(double kilometers) {
		return kilometers * KILOMETER_TO_METERS;
	}

}
