package dates;

import Model.map.MapGrid;
import Model.map.Reciverorder;

public class testgrid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MapGrid mygridmap = new MapGrid();		
		mygridmap.Save();
		
		Reciverorder reciverorder = new Reciverorder();
		reciverorder.Save();

	}

}
