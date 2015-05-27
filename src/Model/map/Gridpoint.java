package Model.map;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Gridpoint {

	// revisar inicializacionde valores
	protected int lat = 0;
	protected int lon = 0;
	protected int vtec = 9999;
	protected int rms = 9999;
	protected Date timestamp = null;
	protected boolean ismonitored = false;

	public Gridpoint() {

	}

	public Gridpoint(int lat, int lon) {

		this.lat = lat;
		this.lon = lon;
	}

	public Gridpoint(int lat, int lon, int vtec, int rms, Date timestap, boolean monitored) {

		this.lat = lat;
		this.lon = lon;
		this.vtec = vtec;
		this.timestamp = timestap;
		this.ismonitored = monitored;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public int getVtec() {
		return vtec;
	}

	public void setVtec(int vtec) {
		this.vtec = vtec;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getRms() {
		return rms;
	}

	public void setRms(int rms) {
		this.rms = rms;
	}

	public boolean isIsmonitored() {
		return ismonitored;
	}

	public void setIsmonitored(boolean ismonitored) {
		this.ismonitored = ismonitored;
	}

	public String getIsmonitored() {

		if (ismonitored)
			return ("T");
		else
			return ("F");
	}

	public String getDate() {

		if (timestamp == null)
			return ("-");
		else {
			String format = new SimpleDateFormat("yyyy MM dd HH mm ss").format(timestamp);
			return (format);
		}
	}

	public String ToFile() {

		// 0 False
		// 1 True
		if (ismonitored) {
			return ("" + this.lon + "," + this.lat + "," + this.vtec + "," + this.rms + "," + this.timestamp + ",1;");
		} else {
			return ("" + this.lon + "," + this.lat + "," + this.vtec + "," + this.rms + "," + this.timestamp + ",0;");
		}

	}
	

	public String ToIONEX() {

		// dudas de lso indices de la latitud y longitus y la interpolacion
		return null;

	}

}
