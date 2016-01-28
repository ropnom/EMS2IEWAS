package dates;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class tetsdate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date now = new Date();
		String format2 = new SimpleDateFormat("yyyy MM dd HH mm ss").format(now);
		
		System.out.println(format2);
		
		System.out.println("EEEEEEEE");
		
		Calendar calendar = Calendar.getInstance();
		
	System.out.println( Calendar.getInstance().get(Calendar.DAY_OF_YEAR));

	}

}
