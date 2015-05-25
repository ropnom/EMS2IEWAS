package Model.map;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.FunctionsExtra;
import Model.LoadDataFile;
import Model.WriteCurrentData;
import Model.TypeMessage.MessageType18;
import Model.TypeMessage.MessageType26;
import Model.message.Message;

public class MapGrid {

	// static variables
	protected final static int max_row = 35;
	protected final static int max_colum = 72;
	protected final static int interval = 5;
	protected static String file = "gridmap.txt";

	protected Date init;
	protected Date finish;
	protected int matrixversion = 0;

	// map with 5ºx5º
	Gridpoint[][] grid = new Gridpoint[max_row][max_colum];

	public MapGrid() {
		CreateMap();
	}

	private void CreateMap() {

		int lat = 85;
		int lon = -180;

		for (int i = 0; i < max_row; i++) {

			lon = -180;
			for (int j = 0; j < max_colum; j++) {
				grid[i][j] = new Gridpoint(lat, lon);
				lon += interval;
			}
			lat -= interval;
		}

	}

	private void LoadMap() {
		// load grid forma file
		List<String> lines = new ArrayList<String>();
		LoadDataFile load = new LoadDataFile(file);
		lines = load.LoadData();

		if (lines.size() >= 0) {
			try {
				String[] points;
				String[] variables;

				for (int i = 0; i < max_row; i++) {
					// get a line and split points
					points = lines.get(i).split(";");
					for (int j = 0; j < max_colum; j++) {
						// split varaiable of point
						variables = points[j].split(",");
						if (variables[3] == "null")
							grid[i][j] = new Gridpoint(Integer.parseInt(variables[1]), Integer.parseInt(variables[0]), Integer.parseInt(variables[2]), Integer.parseInt(variables[3]), null, (variables[3].equals("1") ? true : false));
						else {
							DateFormat df = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
							grid[i][j] = new Gridpoint(Integer.parseInt(variables[1]), Integer.parseInt(variables[0]), Integer.parseInt(variables[2]), Integer.parseInt(variables[3]), df.parse(variables[3]), (variables[3].equals("1") ? true : false));
						}

					}
				}
			} catch (Exception e) {

				// Error de formato!

			}
		}

	}

	// FUNCIONES DE TRADUCCION DE COORDENADAS
	public void PutPoint(int band, int num, int vtec, int rms, Date time) {

		int[] xy = getXY(band, num);

		if (vtec >= 0) {
			grid[xy[1]][xy[0]].setVtec(vtec);
			grid[xy[1]][xy[0]].setRms(rms);
		}
		grid[xy[1]][xy[0]].setTimestamp(time);
		grid[xy[1]][xy[0]].setIsmonitored(true);

	}

	public int[] getXY(int band, int num) {

		int[] xy = new int[2];
		int constante = 31;

		switch (band) {

		case 0: // BAND 0

			// -75 + (num)*5
			constante = 31;
			if (num < 29) {
				// 180W
				xy[0] = 0;
				constante = 31;
			} else if (num < 52) {
				// 175W
				xy[0] = 1;
				constante = 57;
			} else if (num < 79) {
				// 170W
				xy[0] = 2;
				constante = 82;
			} else if (num < 102) {
				// 165W
				xy[0] = 3;
				constante = 107;
			} else if (num < 129) {
				// 160W
				xy[0] = 4;
				constante = 132;
			} else if (num < 152) {
				// 155W
				xy[0] = 5;
				constante = 157;
			} else if (num < 179) {
				// 150W
				xy[0] = 6;
				constante = 182;
			} else {
				// 145W
				xy[0] = 7;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 52:
			case 102:
			case 152:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 53:
			case 103:
			case 153:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 77:
			case 127:
			case 177:
				xy[1] = 4;
				break;

			case 27:
			case 78:
			case 128:
			case 178:
				// N75
				xy[1] = 2;
				break;
			case 28:
				// N85
				xy[1] = 0;
				break;
			}

			break;

		case 1:// BAND 1
				// -75 + (num)*5
			constante = 32;
			if (num < 29) {
				// 180W
				xy[0] = band * 8;
				constante = 32;
			} else if (num < 52) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 57;
			} else if (num < 79) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 82;
			} else if (num < 102) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 107;
			} else if (num < 129) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 132;
			} else if (num < 152) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 157;
			} else if (num < 179) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 182;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 2:
			case 52:
			case 102:
			case 152:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 3:
			case 53:
			case 103:
			case 153:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 27:
			case 77:
			case 127:
			case 177:
				xy[1] = 4;
				break;

			case 28:
			case 78:
			case 128:
			case 178:
				// N75
				xy[1] = 2;
				break;
			case 1:
				// S85
				xy[1] = 34;
				break;
			}

			break;

		case 2: // BAND 2
			// -75 + (num)*5
			constante = 31;
			if (num < 28) {
				// 180W
				xy[0] = band * 8;
				constante = 31;
			} else if (num < 51) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 56;
			} else if (num < 79) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 81;
			} else if (num < 102) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 107;
			} else if (num < 129) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 132;
			} else if (num < 152) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 157;
			} else if (num < 179) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 182;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 51:
			case 102:
			case 152:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 52:
			case 103:
			case 153:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 76:
			case 127:
			case 177:
				xy[1] = 4;
				break;

			case 27:
			case 77:
			case 128:
			case 178:
				// N75
				xy[1] = 2;
				break;
			case 78:
				// S85
				xy[1] = 0;
				break;
			}

			break;

		case 3: // BAND 3
			// -75 + (num)*5
			constante = 31;
			if (num < 28) {
				// 180W
				xy[0] = band * 8;
				constante = 31;
			} else if (num < 51) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 56;
			} else if (num < 79) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 82;
			} else if (num < 102) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 107;
			} else if (num < 129) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 132;
			} else if (num < 152) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 157;
			} else if (num < 179) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 182;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 51:
			case 102:
			case 152:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 52:
			case 103:
			case 153:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 77:
			case 127:
			case 177:
				xy[1] = 4;
				break;

			case 27:
			case 78:
			case 128:
			case 178:
				// N75
				xy[1] = 2;
				break;

			}

			break;

		case 4: // BAND 4
			// -75 + (num)*5
			constante = 31;
			if (num < 28) {
				// 180W
				xy[0] = band * 8;
				constante = 31;
			} else if (num < 51) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 56;
			} else if (num < 78) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 81;
			} else if (num < 101) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 106;
			} else if (num < 129) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 131;
			} else if (num < 152) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 157;
			} else if (num < 179) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 182;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 51:
			case 101:
			case 152:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 52:
			case 102:
			case 153:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 76:
			case 126:
			case 177:
				xy[1] = 4;
				break;

			case 27:
			case 77:
			case 127:
			case 178:
				// N75
				xy[1] = 2;
				break;
			case 128:
				// N85
				xy[1] = 0;
				break;
			}

			break;

		case 5:// BAND 5
				// -75 + (num)*5
			constante = 31;
			if (num < 28) {
				// 180W
				xy[0] = band * 8;
				constante = 31;
			} else if (num < 51) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 56;
			} else if (num < 78) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 81;
			} else if (num < 101) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 106;
			} else if (num < 129) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 132;
			} else if (num < 152) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 157;
			} else if (num < 179) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 182;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 51:
			case 102:
			case 152:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 52:
			case 103:
			case 153:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 76:
			case 127:
			case 177:
				xy[1] = 4;
				break;

			case 27:
			case 77:
			case 128:
			case 178:
				// N75
				xy[1] = 2;
				break;
			case 101:
				// N85
				xy[1] = 34;
				break;
			}

			break;

		case 6: // BAND 6
			// -75 + (num)*5
			constante = 31;
			if (num < 28) {
				// 180W
				xy[0] = band * 8;
				constante = 31;
			} else if (num < 51) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 56;
			} else if (num < 78) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 81;
			} else if (num < 101) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 106;
			} else if (num < 128) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 131;
			} else if (num < 151) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 156;
			} else if (num < 179) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 181;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 51:
			case 101:
			case 151:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 52:
			case 102:
			case 152:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 76:
			case 126:
			case 176:
				xy[1] = 4;
				break;

			case 27:
			case 77:
			case 127:
			case 177:
				// N75
				xy[1] = 2;
				break;
			case 178:
				// N85
				xy[1] = 0;
				break;
			}

			break;

		case 7:// BAND 7
				// -75 + (num)*5
			constante = 31;
			if (num < 28) {
				// 180W
				xy[0] = band * 8;
				constante = 31;
			} else if (num < 51) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 56;
			} else if (num < 78) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 81;
			} else if (num < 101) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 106;
			} else if (num < 128) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 131;
			} else if (num < 151) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 156;
			} else if (num < 179) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 182;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 207;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 51:
			case 101:
			case 152:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 52:
			case 102:
			case 153:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 76:
			case 126:
			case 177:
				xy[1] = 4;
				break;

			case 27:
			case 77:
			case 127:
			case 178:
				// N75
				xy[1] = 2;
				break;
			case 151:
				// N85
				xy[1] = 34;
				break;
			}

			break;

		case 8:// BAND 8
				// -75 + (num)*5
			constante = 31;
			if (num < 28) {
				// 180W
				xy[0] = band * 8;
				constante = 31;
			} else if (num < 51) {
				// 175W
				xy[0] = 1 + band * 8;
				constante = 56;
			} else if (num < 78) {
				// 170W
				xy[0] = 2 + band * 8;
				constante = 81;
			} else if (num < 101) {
				// 165W
				xy[0] = 3 + band * 8;
				constante = 106;
			} else if (num < 128) {
				// 160W
				xy[0] = 4 + band * 8;
				constante = 131;
			} else if (num < 151) {
				// 155W
				xy[0] = 5 + band * 8;
				constante = 156;
			} else if (num < 178) {
				// 150W
				xy[0] = 6 + band * 8;
				constante = 181;
			} else {
				// 145W
				xy[0] = 7 + band * 8;
				constante = 206;
			}

			xy[1] = constante - num;

			// remplace exception cases
			switch (num) {

			case 1:
			case 51:
			case 101:
			case 151:
				// S75 => -75 es la antepenultima fila
				xy[1] = (max_row - 1) - 2;
				break;
			case 2:
			case 52:
			case 102:
			case 152:
				// dos filas antes
				xy[1] = (max_row - 1) - 4;
				break;
			case 26:
			case 76:
			case 126:
			case 176:
				xy[1] = 4;
				break;

			case 27:
			case 77:
			case 127:
			case 177:
				// N75
				xy[1] = 2;
				break;
			}

			break;

		case 9:

			if (num < 73) {
				xy[1] = 5;
				xy[0] = num - 1;

			} else if (num < 109) {
				xy[1] = 4;
				xy[0] = (num - 73) * 2;

			} else if (num < 145) {
				xy[1] = 3;
				xy[0] = (num - 109) * 2;

			} else if (num < 181) {
				xy[1] = 2;
				xy[0] = (num - 145) * 2;

			} else {
				xy[1] = 0;
				xy[0] = (num - 181) * 4;
			}

			break;

		case 10:

			if (num < 73) {
				xy[1] = 29;
				xy[0] = num - 1;

			} else if (num < 109) {
				xy[1] = 30;
				xy[0] = (num - 74) * 2;

			} else if (num < 145) {
				xy[1] = 31;
				xy[0] = (num - 110) * 2;

			} else if (num < 181) {
				xy[1] = 32;
				xy[0] = (num - 147) * 2;

			} else {
				xy[1] = 34;
				xy[0] = (num - 147) * 4;
			}

			break;
		}

		return xy;
	}

	public void Save() {

		List<String> savedate = new ArrayList<String>();
		String line = "";
		for (int i = 0; i < max_row; i++) {
			line = "";
			for (int j = 0; j < max_colum; j++) {
				line += grid[i][j].ToFile();
			}
			savedate.add(line);
		}

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename(file);
		writer.Write(savedate);

	}
	
	public void SaveEGNOS(String date) {

		List<String> saveegnos = new ArrayList<String>();
		String line = "";
		for (int i = 0; i < max_row; i++) {
			line = "";
			for (int j = 24; j < 48; j++) {
				line += grid[i][j].ToFile();
			}
			saveegnos.add(line);
		}

		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename("//grid//"+date+".txt");
		writer.Write(saveegnos);

	}

	public boolean IsValidGridPoint(int i, int j) {

		// check valid time for grid point
		Date referecia = (Date) init.clone();

		referecia = FunctionsExtra.decreaseMinutesToDate(5, referecia);
		referecia = FunctionsExtra.decreaseMinutesToDate(FunctionsExtra.getTimeOutMT26(), referecia);

		if (grid[i][j].getTimestamp() == null) {
			return (false);
		} else {
			// System.out.println("Referencia:"+referecia);
			// System.out.println("punto:"+grid[i][j].getTimestamp());
			if (referecia.compareTo(grid[i][j].getTimestamp()) <= 0) {
				return (true);
			} else {
				grid[i][j].setRms(9999);
				grid[i][j].setVtec(9999);
				grid[i][j].setIsmonitored(false);
				grid[i][j].setTimestamp(null);
				return false;
			}
		}

	}

	public void ToIONEXinput() {

	}

	public static String getFile() {
		return file;
	}

	public static void setFile(String file) {
		MapGrid.file = file;
	}

	public Date getInit() {
		return init;
	}

	public void setInit(Date init) {
		this.init = init;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public int getMatrixversion() {
		return matrixversion;
	}

	public void setMatrixversion(int matrixversion) {
		this.matrixversion = matrixversion;
	}

	public Gridpoint[][] getGrid() {
		return grid;
	}

	public void setGrid(Gridpoint[][] grid) {
		this.grid = grid;
	}

	public static int getMaxRow() {
		return max_row;
	}

	public static int getMaxColum() {
		return max_colum;
	}

	public static int getInterval() {
		return interval;
	}

}
