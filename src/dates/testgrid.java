package dates;

import java.util.Date;

import Model.FunctionsExtra;

public class testgrid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// MapGrid mygridmap = new MapGrid();
		// mygridmap.Save();
		//
		// Reciverorder reciverorder = new Reciverorder();
		// reciverorder.Save();

		Date referencia = new Date();
		Date myDate = new Date(referencia.getYear(), referencia.getMonth() - 1, referencia.getDay());
		System.out.println("My Date is" + myDate);
		System.out.println("Today Date is" + referencia);
		
		myDate = FunctionsExtra.addSecondsToDate(20, myDate);
		System.out.println("My Date modify 20s " + myDate);
		myDate = FunctionsExtra.addSecondsToDate(50, myDate);
		System.out.println("My Date modify 70s " + myDate);
		
		if (referencia.compareTo(myDate) < 0)
			System.out.println("referencia Date is Lesser than my Date");
		else if (referencia.compareTo(myDate) > 0)
			System.out.println("referencia Date is Greater than my date");
		else
			System.out.println("Both Dates are equal");

	}

}
