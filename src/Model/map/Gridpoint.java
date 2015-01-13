package Model.map;

import java.util.Date;

public class Gridpoint {

	// revisar inicializacionde valores
	protected int lat = 0;
	protected int lon = 0;
	protected float vtec = 0;
	protected Date timestamp = null;

	public Gridpoint() {

	}

	public Gridpoint(int lat, int lon) {

		this.lat = lat;
		this.lon = lon;
	}

	public Gridpoint(int lat, int lon, float vtec, Date timestap) {

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

	public float getVtec() {
		return vtec;
	}

	public void setVtec(float vtec) {
		this.vtec = vtec;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String ToFile() {		
		return(""+this.lon+","+this.lat+","+this.vtec+","+this.timestamp+";");

	}

	public String ToIONEX() {

		//dudas de lso indices de la latitud y longitus y la interpolacion
		return null;
		
	}

}
