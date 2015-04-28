package com.megift.resources.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import play.Logger;

import com.megift.bsp.action.entity.Action;
import com.megift.bsp.gift.entity.Gift;
import com.megift.set.picture.entity.Picture;

public class Utils {

	public static boolean uploadFile(Picture picture, String path) {
		boolean result = false;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(path);
			buffer.append(String.valueOf(System.currentTimeMillis()));
			buffer.append(picture.getName());
			File file = picture.getFile();
			File newFile = new File(buffer.toString());
			file.renameTo(newFile);
			picture.setPath(newFile.getPath());
			result = true;
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentado subir el archivo al servidor \n" + e.getMessage(), e);
		}
		return result;
	}

	public static byte[] getFileBytes(String path) {
		byte[] fileBytes = null;
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream inputStream = new BufferedInputStream(fis);
			fileBytes = new byte[(int) file.length()];
			inputStream.read(fileBytes);
			inputStream.close();
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentado Obtener los bytes del archivo \n" + e.getMessage(), e);
		}
		return fileBytes;

	}

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
	public static String getElapsaTime(ZonedDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {

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

		return String.format("%d:%d:%d", (elapsedDays * 24) + elapsedHours, elapsedMinutes, elapsedSeconds);

	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(ZonedDateTime zonaDateTime) {
		return Date.from(zonaDateTime.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

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
		return kilometers * Constants.KILOMETER_TO_METERS;
	}

	public static double roundTo2Decimals(double value) {
		return (double) Math.round(value * 100) / 100;
	}

	public static String priceWithDecimal(Double price) {
		DecimalFormat formatter = new DecimalFormat("###,###,###.00");
		return formatter.format(price);
	}

	public static String priceWithDecimal(Double price, String symbol) {
		DecimalFormat formatter = new DecimalFormat(symbol.concat(" ###,###,###.00"));
		return formatter.format(price);
	}

	public static String priceWithoutDecimal(Double price) {
		DecimalFormat formatter = new DecimalFormat("###,###,###.##");
		return formatter.format(price);
	}

	public static String priceWithoutDecimal(Double price, String symbol) {
		DecimalFormat formatter = new DecimalFormat(symbol.concat(" ###,###,###"));
		return formatter.format(price);
	}

	public static String priceToString(Double price) {
		String toShow = priceWithoutDecimal(price);
		if (toShow.indexOf(".") > 0) {
			return priceWithDecimal(price);
		} else {
			return priceWithoutDecimal(price);
		}
	}
}
