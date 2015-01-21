package dates;

import Model.map.MapGrid;

public class testbands {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MapGrid grid = new MapGrid();
		String line = "";
//		for(int band = 0;band <11;band++){
//			System.out.println();
//			System.out.println();
//			System.out.println("****************");
//			System.out.println("Band Number: "+band);
//			for(int number = 1; number<202;number++){
//				line = " number: " + number +"  " + grid.getXY(band, number)[0]+";"+grid.getXY(band, number)[1];
//				System.out.println(line);
//			}
//		}
		
		int band = 6;
		int number = 178;
		System.out.println("Banda:"+band+" Numero:"+number);
		line = " number: " + number +"  " + grid.getXY(band, number)[0]+";"+grid.getXY(band, number)[1];
		System.out.println(line);
	}

}
