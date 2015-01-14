package Model.map;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.LoadDataFile;
import Model.WriteCurrentData;
import Model.TypeMessage.MessageType18;

public class Reciverorder {

	// sub class para cada banda
	public class Bandorder {

		protected List<Integer> bandX;
		protected Date lastupdate;
		protected boolean isblock = true;

		public Bandorder(List<Integer> bandnumbers, Date lastupdate) {
			this.bandX = bandnumbers;
			this.lastupdate = lastupdate;
			this.isblock = false;
		}

		public Bandorder() {
			this.bandX = new ArrayList<Integer>();
			isblock = true;
		}

		public List<Integer> getBandX() {
			return bandX;
		}

		public void setBandX(List<Integer> bandX) {
			this.bandX = bandX;
		}

		public Date getLastupdate() {
			return lastupdate;
		}

		public void setLastupdate(Date lastupdate) {
			this.lastupdate = lastupdate;
		}

		public void addorder(int num) {
			this.bandX.add(num);
		}

		public boolean isIsblock() {
			return isblock;
		}

		public void setIsblock(boolean isblock) {
			this.isblock = isblock;
		}

		public String ToFile() {

			String line = "";
			for (int i = 0; i < bandX.size(); i++) {
				line += bandX.get(i) + ",";
			}
			return line;
		}

	}

	// declaramos 10 bandas por cada iodi
	protected Bandorder[][] matrix;
	protected String file = "reciverorder.txt";
	protected int numberofbands;
	protected boolean[] allmt18resiver = {false,false,false};

	public Reciverorder() {

		matrix = new Bandorder[3][11];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 11; j++) {
				matrix[i][j] = new Bandorder();
			}
		}

	}

	public Reciverorder(String file) {

		// load file
		this.file = file;

	}

	public Bandorder[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(Bandorder[][] matrix) {
		this.matrix = matrix;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getNumberofbands() {
		return numberofbands;
	}

	public void setNumberofbands(int numberofbands) {
		this.numberofbands = numberofbands;
	}

	public boolean[] isAllmt18resiver() {
		return allmt18resiver;
	}

	public void setAllmt18resiver(boolean[] allmt18resiver) {
		this.allmt18resiver = allmt18resiver;
	}

	public void Save() {

		List<String> savedates = new ArrayList<String>();
		String line = "";

		for (int i = 0; i < 3; i++) {
			line = "";
			for (int j = 0; j < 11; j++) {
				line += matrix[i][j].ToFile() + ";";
			}
			savedates.add(line);

		}

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(file);
		writer.Write(savedates);

	}

	private void Load() {

		// generator of matrix
		List<Integer> bandnumbers = new ArrayList<Integer>();
		Bandorder band = new Bandorder();

		// load file
		List<String> lines = new ArrayList<String>();
		LoadDataFile load = new LoadDataFile(file);
		lines = load.LoadData();

		try {

			String[] bandx;
			String[] values;

			for (int i = 0; i < 3; i++) {
				// get a line and split points
				bandx = lines.get(i).split(";");
				for (int j = 0; j < 11; j++) {
					// split varaiable of point
					values = bandx[j].split(",");

					for (int m = 0; m < values.length; m++) {
						// agregar la lista
					}
					// ponerlista en el band

				}

			}
		} catch (Exception e) {

			// Error de formato!

		}

	}

	public void ProcessMT18(MessageType18 mt18, Date time) {

		// get iodi
		int iodi = mt18.getIodi();
		// get band
		int band = mt18.getIodi();

		if (iodi == -1 || band == -1) {

			// LOG error message decoding
		} else {

			if (mt18.getOrden().size() > 0) {

				matrix[iodi][band] = new Bandorder(mt18.getOrden(),time);

			} else {
				// LOG error not found information in mt18
			}
		}
		// get list

	}
	
	

}
