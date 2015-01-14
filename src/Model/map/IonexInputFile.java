package Model.map;

import java.util.ArrayList;
import java.util.List;

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
