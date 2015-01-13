package Model.map;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.LoadDataFile;
import Model.WriteCurrentData;
import Model.TypeMessage.MessageType18;
import Model.TypeMessage.MessageType26;
import Model.message.Message;

public class MapGrid {

	// static variables
	public final static int max_row = 35;
	public final static int max_colum = 72;
	public final static int interval = 5;
	public static String file = "gridmap.txt";

	// map with 5�x5�
	Gridpoint[][] grid = new Gridpoint[max_row][max_colum];

	public MapGrid() {
		CreateMap();
	}

	private void CreateMap() {

		int lat = 85;
		int lon = 180;

		for (int i = 0; i < max_row; i++) {

			lon = 180;
			for (int j = 0; j < max_colum; j++) {
				grid[i][j] = new Gridpoint(lat, lon);
				lon -= interval;
			}
			lat -= interval;
		}

	}

	private void LoadMap() {
		// load grid forma file
		List<String> lines = new ArrayList<String>();
		LoadDataFile load = new LoadDataFile();
		lines = load.LoadData();

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
						grid[i][j] = new Gridpoint(Integer.parseInt(variables[1]), Integer.parseInt(variables[0]), Float.parseFloat(variables[2]), null);
					else {
						DateFormat df = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
						grid[i][j] = new Gridpoint(Integer.parseInt(variables[1]), Integer.parseInt(variables[0]), Float.parseFloat(variables[2]), df.parse(variables[3]));
					}

				}
			}
		} catch (Exception e) {

			// Error de formato!

		}

	}

	// FUNCIONES DE TRADUCCION DE COORDENADAS
	private void PutPoint(int band, int iodi, float vtec) {

	}

	private int[] getXY(int band, int num) {

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

		case 4:	// BAND 4
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
				// S85
				xy[1] = 0;
				break;
			}

			break;

		case 5:

			break;

		case 6:

			break;

		case 7:

			break;

		case 8:

			break;

		case 9:

			break;

		case 10:

			break;
		}

		return xy;
	}

	public void ProcessMessage(Message message) {

		switch (message.getTypemessage()) {

		case 18:
			// Procesamos mascaras
			MessageType18 mt18 = (MessageType18) message.getPayload();
			// obtener las mascaras para cada banda.

			break;

		case 26:
			// procesamos delays
			MessageType26 mt26 = (MessageType26) message.getPayload();

			break;

		}

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

}
