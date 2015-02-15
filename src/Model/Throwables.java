package Model;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Throwables {

	public static String getStackTraceAsString(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

}
