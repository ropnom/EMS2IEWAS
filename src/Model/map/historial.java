package Model.map;

import java.util.ArrayList;
import java.util.List;

public class historial {

	// data
	protected List<int[][]> historialvtec = new ArrayList<int[][]>();
	protected List<int[][]> historialrms = new ArrayList<int[][]>();
	protected List<ImageMap> historialimages = new ArrayList<ImageMap>();

	public void GenImageMap(MapGrid matrix, String date) {

		int[][] vtec = new int[MapGrid.getMaxRow()][MapGrid.getMaxColum()];
		int[][] rms = new int[MapGrid.getMaxRow()][MapGrid.getMaxColum()];
		ImageMap image = new ImageMap();

		for (int i = 0; i < MapGrid.getMaxRow(); i++) {

			for (int j = 0; j < MapGrid.getMaxColum(); j++) {

				matrix.getGrid()[i][j].ToFile();
				vtec[i][j] = matrix.getGrid()[i][j].getVtec();
				rms[i][j] = matrix.getGrid()[i][j].getRms();
				image.InsertValue(i, j, matrix.getGrid()[i][j].getVtec(), true);
				image.InsertValue(i, j, matrix.getGrid()[i][j].getRms(), false);
			}

		}
		historialvtec.add(vtec);
		historialrms.add(rms);
		image.GenImage(date);
		historialimages.add(image);
	}

}
