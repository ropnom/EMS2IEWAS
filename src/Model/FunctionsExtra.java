package Model;

import java.util.Date;

public class FunctionsExtra {

	private final static float[] matrixgivei  = { 0.0084f, 0.0333f, 0.0749f, 0.1331f, 0.2079f, 0.2994f, 0.4075f, 0.5322f, 0.6735f, 0.8315f, 1.1974f, 1.8709f, 3.3260f, 20.7870f, 187.0826f, 9999 };
	
	public static Date addMinutesToDate(int minutes, Date beforeTime) {

		long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
		return afterAddingMins;
	}

	public static Date addSecondsToDate(int seconds, Date beforeTime) {

		long ONE_MINUTE_IN_MILLIS = 1000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (seconds * ONE_MINUTE_IN_MILLIS));
		return afterAddingMins;
	}

	public static float getGIVEITABLE(int a){

		return(matrixgivei[a]);
	}
}
