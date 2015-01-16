package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FunctionsExtra {

	private final static float[] matrixgivei = { 0.0084f, 0.0333f, 0.0749f, 0.1331f, 0.2079f, 0.2994f, 0.4075f, 0.5322f, 0.6735f, 0.8315f, 1.1974f, 1.8709f, 3.3260f, 20.7870f, 187.0826f, 9999 };

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

	public static float getGIVEITABLE(int a) {

		return (matrixgivei[a]);
	}

	public static List<String> ToKml(String name, List<String> lines) {

		List<String> kml = new ArrayList<String>();

		kml.addAll(getFilehead(name));

		String line[];
		String vtec;
		String lat;
		String lon;

		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i).split(" ");
			vtec = line[2];
			lat = line[3];
			lon = line[4];

			if (vtec == "9999") {
				kml.add("<Placemark>");
				kml.add("<name>" + vtec + "</name>");
				kml.add("#msn_ylw-pushpin");
				kml.add("<Point>");
				kml.add("<coordinates>" + lon + "," + lat + ",300000</coordinates>");
				kml.add("</Point>");
				kml.add("</Placemark>");

			} else {
				kml.add("<Placemark>");
				kml.add("<name>" + vtec + "</name>");
				kml.add("<styleUrl>#m_ylw-pushpin</styleUrl>");
				kml.add("<Point>");
				kml.add("<coordinates>" + lon + "," + lat + ",300000</coordinates>");
				kml.add("</Point>");
				kml.add("</Placemark>");
			}
		}

		kml.add("</Folder>");
		kml.add("</kml>");

		return kml;
	}

	private static List<String> getFilehead(String name) {
		List<String> file = new ArrayList<>();
		file.add("<?xml version='1.0' encoding='UTF-8'?>");
		file.add("<kml xmlns='http://www.opengis.net/kml/2.2' xmlns:gx='http://www.google.com/kml/ext/2.2' xmlns:kml='http://www.opengis.net/kml/2.2' xmlns:atom='http://www.w3.org/2005/Atom'>");
		file.add("<Document>");
		file.add("<name>" + name + ".kmz</name>");
		file.add("<open>1</open>");
		file.add("<Style id='sn_ylw-pushpin'>");
		file.add("<IconStyle>");
		file.add("<color>ff0000ff</color>");
		file.add("<scale>2</scale>");
		file.add("<Icon>");
		file.add("<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>");
		file.add("</Icon>");
		file.add("<hotSpot x='20' y='2' xunits='pixels' yunits='pixels'/>");
		file.add("</IconStyle>");
		file.add("<LabelStyle>");
		file.add("<color>ff0000ff</color>");
		file.add("<scale>2</scale>");
		file.add("</LabelStyle>");
		file.add("</Style>");
		file.add("<StyleMap id='m_ylw-pushpin'>");
		file.add("<Pair>");
		file.add("<key>normal</key>");
		file.add("<styleUrl>#s_ylw-pushpin</styleUrl>");
		file.add("</Pair>");
		file.add("<Pair>");
		file.add("<key>highlight</key>");
		file.add("<styleUrl>#s_ylw-pushpin_hl</styleUrl>");
		file.add("</Pair>");
		file.add("</StyleMap>");
		file.add("<Style id='s_ylw-pushpin'>");
		file.add("<IconStyle>");
		file.add("<color>ff00ff00</color>");
		file.add("<scale>2</scale>");
		file.add("<Icon>");
		file.add("<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>");
		file.add("</Icon>");
		file.add("<hotSpot x='20' y='2' xunits='pixels' yunits='pixels'/>");
		file.add("</IconStyle>");
		file.add("<LabelStyle>");
		file.add("<color>ff00ff00</color>");
		file.add("<scale>2</scale>");
		file.add("</LabelStyle>");
		file.add("</Style>");
		file.add("<Style id='sh_ylw-pushpin'>");
		file.add("<IconStyle>");
		file.add("<color>ff0000ff</color>");
		file.add("<scale>2.36364</scale>");
		file.add("<Icon>");
		file.add("<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>");
		file.add("</Icon>");
		file.add("<hotSpot x='20' y='2' xunits='pixels' yunits='pixels'/>");
		file.add("</IconStyle>");
		file.add("<LabelStyle>");
		file.add("<color>ff0000ff</color>");
		file.add("<scale>2</scale>");
		file.add("</LabelStyle>");
		file.add("</Style>");
		file.add("<Style id='s_ylw-pushpin_hl'>");
		file.add("<IconStyle>");
		file.add("<color>ff00ff00</color>");
		file.add("<scale>2.36364</scale>");
		file.add("<Icon>");
		file.add("<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>");
		file.add("</Icon>");
		file.add("<hotSpot x='20' y='2' xunits='pixels' yunits='pixels'/>");
		file.add("</IconStyle>");
		file.add("<LabelStyle>");
		file.add("<color>ff00ff00</color>");
		file.add("<scale>2</scale>");
		file.add("</LabelStyle>");
		file.add("</Style>");
		file.add("<StyleMap id='msn_ylw-pushpin'>");
		file.add("<Pair>");
		file.add("<key>normal</key>");
		file.add("<styleUrl>#sn_ylw-pushpin</styleUrl>");
		file.add("</Pair>");
		file.add("<Pair>");
		file.add("<key>highlight</key>");
		file.add("<styleUrl>#sh_ylw-pushpin</styleUrl>");
		file.add("</Pair>");
		file.add("</StyleMap>");

		return file;
	}
}
