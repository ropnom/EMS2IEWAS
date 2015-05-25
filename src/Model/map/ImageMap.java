package Model.map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ImageMap {

	// vtec map with 5ºx5º
	protected BufferedImage imgtec = new BufferedImage(MapGrid.getMaxRow(), MapGrid.getMaxColum(), BufferedImage.TYPE_INT_ARGB_PRE);
	// rms map with 5ºx5º
	protected BufferedImage imgrms = new BufferedImage(MapGrid.getMaxRow(), MapGrid.getMaxColum(), BufferedImage.TYPE_INT_ARGB_PRE);
		
	
	public void GenImage(String date){
		
		String path = Paths.get("").toAbsolutePath().toString();
		try{
		
		ImageIO.write(imgtec, "PNG", new File(path+"//"+date+".PNG"));
		}catch(Exception e){
			
		}
	}
	
	public void InsertValue(int x, int y, float value, boolean type) {

		// given a max and min value
		int red = 0, green = 0, blue = 0, col = 0, a = 0;
		float transparency = 0.3f;
		float range;

		if (type)
			range = 3926.1585f;
		else
			range = 842.3728047f;

		// 9 bit pixel colorbar
		float coeficiente = value / range;
		if (0 <= coeficiente && coeficiente <= 1 / 8) {
			red = 0;
			green = 0;
			blue = (int) Math.round(255 * (4 * coeficiente + 0.5f)); // .5 - 1
																		// // b
																		// = 1/2
			a = (int) (250 * transparency);
		} else if (1 / 8 < coeficiente && coeficiente <= 3 / 8) {
			red = 0;
			green = (int) Math.round(255 * (4 * coeficiente - 0.5f)); // 0 - 1
																		// // b
																		// = -
																		// 1/2
			blue = 1;
			a = (int) (250 * transparency);
		} else if (3 / 8 < coeficiente && coeficiente <= 5 / 8) {
			red = (int) Math.round(255 * (4 * value - 1.5f)); // 0 - 1 // b = -
																// 3/2
			green = 1;
			blue = (int) Math.round(255 * (-4 * coeficiente + 2.5f)); // 1 - 0
																		// // b
																		// = 5/2
			a = (int) (250 * transparency);
		} else if (5 / 8 < coeficiente && coeficiente <= 7 / 8) {
			red = 1;
			green = (int) Math.round(255 * (-4 * coeficiente + 3.5f)); // 1 - 0
																		// // b
																		// = 7/2
			blue = 0;
			a = (int) (250 * transparency);
		} else if (7 / 8 < coeficiente && coeficiente <= 1) {
			red = (int) Math.round(255 * (-4 * coeficiente + 4.5f)); // 1 - .5
																		// // b
																		// = 9/2
			green = 0;
			blue = 0;
			a = (int) (250 * transparency);
		} else { // should never happen - value > 1
			red = 0;
			green = 0;
			blue = 0;
			a = 0;
		}

		col = (a << 24) | (red << 16) | (green << 8) | blue;

		if (type)
			imgtec.setRGB(x, y, col);
		else
			imgrms.setRGB(x, y, col);

	}

}
