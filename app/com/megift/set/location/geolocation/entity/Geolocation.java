package com.megift.set.location.geolocation.entity;

import static com.megift.resources.utils.Constants.convertKilometersToMeteres;
import static com.megift.resources.utils.Constants.deg2rad;
import static com.megift.resources.utils.Constants.rad2deg;

import java.time.LocalDate;

import com.megift.resources.base.Entity;

/**
 * @class : GeoLocation.java<br/>
 * @company : Megift S.A<br/>
 * @user : YQ<br/>
 * @date : Feb 23, 2015<br/>
 * @update date : Feb 23, 2015<br/>
 * @update by : Feb 23, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Geolocation extends Entity {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private double latitude;
	private double longitude;
	private double altitude;
	private double accuracy;
	private double altitudeAccuracy;
	private double heading;
	private double speed;
	private LocalDate timeStamp;

	/**
	 * @param id
	 */
	public Geolocation(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public Geolocation(double latitude, double longitude) {
		super(0);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/*
	 * calculate distance in between two points
	 */
	public static double distance(Geolocation point1, Geolocation point2, char unit) {
		double theta = point1.getLongitude() - point2.getLongitude();
		double dist = Math.sin(deg2rad(point1.getLatitude())) * Math.sin(deg2rad(point2.getLatitude())) + Math.cos(deg2rad(point1.getLatitude())) * Math.cos(deg2rad(point2.getLatitude())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	/*
	 * TODO:quitar
	 * calculate distance in meters between two points
	 */
	public static double distanceInMetersBetween(Geolocation point1, Geolocation point2) {
		return convertKilometersToMeteres(distance(point1, point2, 'K'));
	}

	/*
	 * calculate distance in meters between two points
	 */
	public static double distanceInKiloMetersBetween(Geolocation point1, Geolocation point2) {
		return distance(point1, point2, 'K');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.megift.resources.base.Entity#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getAltitudeAccuracy() {
		return altitudeAccuracy;
	}

	public void setAltitudeAccuracy(double altitudeAccuracy) {
		this.altitudeAccuracy = altitudeAccuracy;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

}
