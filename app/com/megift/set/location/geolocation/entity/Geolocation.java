package com.megift.set.location.geolocation.entity;

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

	/**
	 * @param id
	 */
	public Geolocation(int id) {
		super(id);
		// TODO Auto-generated constructor stub
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

}
