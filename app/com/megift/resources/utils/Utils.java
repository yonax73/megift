package com.megift.resources.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

	/*
	 * elapsed time in days, hours, minutes and seconds format
	 */
	public static String getElapsaTime(Date startDate, Date endDate) {

		// milliseconds
		long different = endDate.getTime() - startDate.getTime();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		return String.format("%d days, %d hours, %d minutes, %d seconds%n", elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

	}

	/*
	 * elapsed time hours, minutes and seconds format
	 */
	public static String getElapsaTime(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {

		Date startDate = asDate(startLocalDateTime);
		Date endDate = asDate(endLocalDateTime);
		// milliseconds
		long different = endDate.getTime() - startDate.getTime();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		return String.format("%d:%d:%d", elapsedHours * elapsedDays, elapsedMinutes, elapsedSeconds);

	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
