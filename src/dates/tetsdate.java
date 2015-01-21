package dates;

import java.text.SimpleDateFormat;
import java.util.Date;

public class tetsdate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date now = new Date();
		String format2 = new SimpleDateFormat("yyyy MM dd HH mm ss").format(now);
		
		System.out.println(format2);

	}

}
