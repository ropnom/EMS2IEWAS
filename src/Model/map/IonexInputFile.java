package Model.map;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.FunctionsExtra;
import Model.WriteCurrentData;

public class IonexInputFile {

	protected String[] filenames = { "t_lat_lon.info", "tec", "rms" };
	protected int version = 0;
	protected float initlat = 85.0f;
	protected float endlat = -85.0f;
	protected float latinterval = 5.0f;
	protected float initlong = -180.0f;
	protected float endlong = 180.0f;
	protected float longinterval = 5.0f;
	protected int timeinterval;
	protected Date Init;
	protected Date finish;

	public IonexInputFile() {

	}

	public IonexInputFile(float maxlat, float maxlong, float latinterval, float longinterval, int version, int timeinterval) {

		this.initlat = maxlat;
		this.endlat = -maxlat;
		this.initlong = -maxlong;
		this.endlong = maxlong;
		this.latinterval = latinterval;
		this.longinterval = longinterval;
		this.version = version;
		this.timeinterval = timeinterval;

	}

	public void GridToInput(MapGrid s, int day, int year, boolean kml, boolean show) {

		List<String> savedate = new ArrayList<String>();
		String line = "";

		int i = 0;
		int indexlat = 1;
		int indexlong = 1;
		for (i = 0; i < MapGrid.getMaxRow(); i++) {

			indexlong = 1;
			for (int j = 0; j < MapGrid.getMaxColum(); j++) {
				// check temporal
				if (s.IsValidGridPoint(i, j)) {
					line = (indexlong) + " " + (indexlat) + " " + s.getGrid()[i][j].getVtec() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " " + s.getGrid()[i][j].getIsmonitored() + " " + s.getGrid()[i][j].getDate();
					
				}else
				{
					line = (indexlong) + " " + (indexlat) + " 9999 " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " F -";
					
				}
				indexlong++;
				savedate.add(line);
			}
			line = (indexlong) + " " + (indexlat) + " " + s.getGrid()[i][0].getVtec() + " " + -s.getGrid()[i][0].getLon() + " " + s.getGrid()[i][0].getLat() + " " + s.getGrid()[i][0].getIsmonitored() + " " + s.getGrid()[i][0].getDate();
			savedate.add(line);
			indexlat++;
		}

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(filenames[1]+ "." + String.format("%03d", this.version));
		writer.setSubFolder(year + "_" + String.format("%03d", day));
		writer.Write(savedate);
		
		if(show)
			System.out.println("-- Create: " +writer.getSubFolder()+writer.getFilename());

		if(kml){
		// Make kml
		writer = new WriteCurrentData();
		writer.setFilename("MatrixVtec" + this.version + ".kml");
		writer.setSubFolder(year + "_" + day);
		writer.Write(FunctionsExtra.ToKml("MatrixVtec" + String.format("%03d", this.version), savedate));
		if(show)
			System.out.println("-- Create: " +writer.getSubFolder()+writer.getFilename());
		}
		savedate = new ArrayList<String>();
		line = "";

		indexlat = 1;
		indexlong = 1;
		for (i = 0; i < MapGrid.getMaxRow(); i++) {

			indexlong = 1;
			for (int j = 0; j < MapGrid.getMaxColum(); j++) {
				// check temporal
				if (s.IsValidGridPoint(i, j)) {
					line = (indexlong) + " " + (indexlat) + " " + s.getGrid()[i][j].getRms() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " " + s.getGrid()[i][j].getIsmonitored() + " " + s.getGrid()[i][j].getDate();
					
				}else
				{
					line = (indexlong) + " " + (indexlat) + " 9999 " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " F -";					
				}
				indexlong++;
				savedate.add(line);
			}
			line = (indexlong) + " " + (indexlat) + " " + s.getGrid()[i][0].getRms() + " " + -s.getGrid()[i][0].getLon() + " " + s.getGrid()[i][0].getLat() + " " + s.getGrid()[i][0].getIsmonitored() + " " + s.getGrid()[i][0].getDate();
			savedate.add(line);
			indexlat++;
		}

		writer = new WriteCurrentData();
		writer.setFilename(filenames[2]+ "."+ String.format("%03d", this.version));
		writer.setSubFolder(year + "_" + String.format("%03d", day));
		writer.Write(savedate);
		if(show)
			System.out.println("-- Create: " +writer.getSubFolder()+writer.getFilename());

	}

	public void GenParametersInfoFile(int year, int day, int intervalo) {

		List<String> savedate = new ArrayList<String>();
		String formatdate = new SimpleDateFormat("yyyy MM dd HH mm ss").format(Init);
		savedate.add(formatdate);
		formatdate = new SimpleDateFormat("yyyy MM dd HH mm ss").format(finish);
		savedate.add(formatdate);
		savedate.add(""+intervalo);
		savedate.add(""+version);
		savedate.add("6371.0");
		savedate.add("2");
		savedate.add("450.0 450.0 0.0");
		savedate.add("85 -85 -5");
		savedate.add("-180 180 5");
		savedate.add("-1");

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(filenames[0]);
		writer.setSubFolder(year + "_" + String.format("%03d", day));
		writer.Write(savedate);
	}

	public String[] getFilenames() {
		return filenames;
	}

	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public float getInitlat() {
		return initlat;
	}

	public void setInitlat(float initlat) {
		this.initlat = initlat;
	}

	public float getEndlat() {
		return endlat;
	}

	public void setEndlat(float endlat) {
		this.endlat = endlat;
	}

	public float getLatinterval() {
		return latinterval;
	}

	public void setLatinterval(float latinterval) {
		this.latinterval = latinterval;
	}

	public float getInitlong() {
		return initlong;
	}

	public void setInitlong(float initlong) {
		this.initlong = initlong;
	}

	public float getEndlong() {
		return endlong;
	}

	public void setEndlong(float endlong) {
		this.endlong = endlong;
	}

	public float getLonginterval() {
		return longinterval;
	}

	public void setLonginterval(float longinterval) {
		this.longinterval = longinterval;
	}

	public int getTimeinterval() {
		return timeinterval;
	}

	public void setTimeinterval(int timeinterval) {
		this.timeinterval = timeinterval;
	}

	public Date getInit() {
		return Init;
	}

	public void setInit(Date init) {
		Init = init;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

}
