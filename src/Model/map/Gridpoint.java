package Model.map;

import java.util.Date;

public class Gridpoint {

	// revisar inicializacionde valores
	protected int lat = 0;
	protected int lon = 0;
	protected int vtec = 0;
	protected int rms = 0;
	protected Date timestamp = null;

	public Gridpoint() {

	}

	public Gridpoint(int lat, int lon) {

		this.lat = lat;
		this.lon = lon;
	}

	public Gridpoint(int lat, int lon, int vtec, int rms, Date timestap) {

		this.lat = lat;
		this.lon = lon;
		this.vtec = vtec;
		this.timestamp = timestap;
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

	public String ToFile() {		
		return(""+this.lon+","+this.lat+","+this.vtec+","+this.rms+","+this.timestamp+";");

	}

	public String ToIONEX() {

		//dudas de lso indices de la latitud y longitus y la interpolacion
		return null;
		
	}

}
