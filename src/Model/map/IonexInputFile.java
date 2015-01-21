package Model.map;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.FunctionsExtra;
import Model.WriteCurrentData;

public class IonexInputFile {

	protected String[] filenames = { "t_lat_lon.info", "vtec", "rms" };
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

	public void GridToInput(MapGrid s, int day, int year) {

		List<String> savedate = new ArrayList<String>();
		String line = "";

		int i = 0;
		for (i = 0; i < MapGrid.getMaxRow(); i++) {
			for (int j = 0; j < MapGrid.getMaxColum(); j++) {
				// check temporal
				if (s.IsValidGridPoint(i, j)) {
					line = (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getVtec() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " " + s.getGrid()[i][j].getIsmonitored() + " " + s.getGrid()[i][j].getDate();
					savedate.add(line);
				}
			}
		}

		// repeat firts colum for 180 = -180
		i = 0;
		for (int j = 0; j < MapGrid.getMaxColum(); j++) {
			// check temporal
			if (s.IsValidGridPoint(i, j)) {
				line = (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getVtec() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " " + s.getGrid()[i][j].getIsmonitored() + " " + s.getGrid()[i][j].getDate();
				savedate.add(line);
			}

		}

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(filenames[1] + this.version + ".txt");
		writer.setSubFolder(year+"_"+day);
		writer.Write(savedate);

		// Make kml
		WriteCurrentData writer2 = new WriteCurrentData();
		writer2.setFilename("MatrixVtec.kml");
		writer.setSubFolder(year+"_"+day);
		writer2.Write(FunctionsExtra.ToKml("Matrix", savedate));

		savedate = new ArrayList<String>();
		line = "";

		for (i = 0; i < MapGrid.getMaxRow(); i++) {
			for (int j = 0; j < MapGrid.getMaxColum(); j++) {
				// check temporal
				if (s.IsValidGridPoint(i, j)) {
					line = (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getRms() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " " + s.getGrid()[i][j].getIsmonitored() + " " + s.getGrid()[i][j].getDate();
					savedate.add(line);
				}
			}
		}

		// repeat firts colum for 180 = -180
		i = 0;
		for (int j = 0; j < MapGrid.getMaxColum(); j++) {
			// check temporal
			if (s.IsValidGridPoint(i, j)) {
				line += (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getRms() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat() + " " + s.getGrid()[i][j].getIsmonitored() + " " + s.getGrid()[i][j].getDate();
				savedate.add(line);
			}

		}

		writer = new WriteCurrentData();
		writer.setFilename(filenames[2] + this.version + ".txt");
		writer.setSubFolder(year+"_"+day);
		writer.Write(savedate);

	}

	public void GenParametersInfoFile() {

		List<String> savedate = new ArrayList<String>();
		String formatdate = new SimpleDateFormat("yyyy MM dd HH mm ss").format(Init);
		savedate.add(formatdate);
		formatdate = new SimpleDateFormat("yyyy MM dd HH mm ss").format(finish);
		savedate.add(formatdate);
		savedate.add("97");
		savedate.add("6371.0");
		savedate.add("2");
		savedate.add("450.0 450.0 0.0");
		savedate.add("85 -85 -5");
		savedate.add("deg");
		savedate.add("-180 180 5");
		savedate.add("/ deg");
		savedate.add("-1");

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(filenames[0]);
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
