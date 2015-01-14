package Model;

import java.util.Date;

public class FunctionsExtra {

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

}
