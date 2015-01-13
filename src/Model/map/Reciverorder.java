package Model.map;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.LoadDataFile;
import Model.WriteCurrentData;

public class Reciverorder {

	// sub class para cada banda
	public class Bandorder {

		protected List<Integer> bandX;
		protected Date lastupdate;

		public Bandorder() {
			this.bandX = new ArrayList<Integer>();
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

	public Reciverorder() {

		matrix = new Bandorder[3][11];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 11; j++) {
				matrix[i][j] = new Bandorder();
			}
		}

	}
	
	public Reciverorder(String file) {

		//load file
		this.file = file;

	}

	public Bandorder[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(Bandorder[][] matrix) {
		this.matrix = matrix;
	}

	public void Save() {

		List<String> savedates = new ArrayList<String>();
		String line = "";

		for (int i = 0; i < 3; i++) {
			line = "";
			for (int j = 0; j < 11; j++) {
				line += matrix[i][j].ToFile() + ";";
				savedates.add(line);
			}

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
		LoadDataFile load = new LoadDataFile();
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
					
					for(int m = 0 ; m<values.length ; m++){
						//agregar la lista
					}
					//ponerlista en el band

				}
				
			}
		} catch (Exception e) {

			// Error de formato!

		}

	}

}
