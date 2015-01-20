package Model.map;

import java.util.ArrayList;
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

	public void GridToInput(MapGrid s) {

		List<String> savedate = new ArrayList<String>();
		String line = "";

		int i = 0;
		for (i = 0; i < MapGrid.getMaxRow(); i++) {
			for (int j = 0; j < MapGrid.getMaxColum(); j++) {
				// check temporal
				if (s.IsValidGridPoint(i, j)) {
					line = (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getVtec() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat();
					savedate.add(line);
				}
			}
		}

		// repeat firts colum for 180 = -180
		i = 0;
		for (int j = 0; j < MapGrid.getMaxColum(); j++) {
			// check temporal
			if (s.IsValidGridPoint(i, j)) {
				line = (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getVtec() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat();
				savedate.add(line);
			}

		}
		


		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(filenames[1] + s.getInit() + ".txt");
		writer.Write(savedate);
		
		//Make kml
		WriteCurrentData writer2 = new WriteCurrentData();
		writer2.setFilename("Matrix.kml");		
		writer2.Write(FunctionsExtra.ToKml("Matrix", savedate));
		
		savedate = new ArrayList<String>();
		line = "";

		for (i = 0; i < MapGrid.getMaxRow(); i++) {
			for (int j = 0; j < MapGrid.getMaxColum(); j++) {
				// check temporal
				if (s.IsValidGridPoint(i, j)) {
					line = (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getRms() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat();
					savedate.add(line);
				}
			}
		}

		// repeat firts colum for 180 = -180
		i = 0;
		for (int j = 0; j < MapGrid.getMaxColum(); j++) {
			// check temporal
			if (s.IsValidGridPoint(i, j)) {
				line += (i + 1) + " " + (j + 1) + " " + s.getGrid()[i][j].getRms() + " " + s.getGrid()[i][j].getLon() + " " + s.getGrid()[i][j].getLat();
				savedate.add(line);
			}

		}

		writer = new WriteCurrentData();
		writer.setFilename(filenames[2] + s.getInit() + ".txt");
		writer.Write(savedate);

	}

	public void GenParametersInfoFile() {

		List<String> savedate = new ArrayList<String>();
		String line = "";

		line += "";
		savedate.add(line);

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(filenames[0]);
		writer.Write(savedate);
	}

}
