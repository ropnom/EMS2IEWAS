package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FunctionsExtra {

	// Anex A matrix
	private final static float[] matrixgiveisigma = { 0.0084f, 0.0333f, 0.0749f, 0.1331f, 0.2079f, 0.2994f, 0.4075f, 0.5322f, 0.6735f, 0.8315f, 1.1974f, 1.8709f, 3.3260f, 20.7870f, 187.0826f, 9999f };
	private final static float[] matrixgivei = { 0.3f, 0.6f, 0.9f, 1.2f, 1.5f, 1.8f, 2.1f, 2.4f, 2.7f, 3.0f, 3.6f, 4.5f, 6.0f, 15.0f, 45.0f, 9999f };
	private final static int[] timeoutmatrix = { 600, 12, 240, 240, 240, 240, 1200, 600, 86400, 240, 86400 };

	public static Date addMinutesToDate(int minutes, Date beforeTime) {

		long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
		return afterAddingMins;
	}

	public static Date decreaseMinutesToDate(int minutes, Date beforeTime) {

		long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs - (minutes * ONE_MINUTE_IN_MILLIS));
		return afterAddingMins;
	}
	
	public static Date decreaseSecondsToDate(int seconds, Date beforeTime) {

		long ONE_SECOND_IN_MILLIS = 1000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs - (seconds * ONE_SECOND_IN_MILLIS));
		return afterAddingMins;
	}

	public static Date addDayToDate(int days, Date beforeTime) {

		long ONE_MINUTE_IN_MILLIS = 86400000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (days * ONE_MINUTE_IN_MILLIS));
		return afterAddingMins;
	}

	public static Date addSecondsToDate(int seconds, Date beforeTime) {

		long ONE_SECOND_IN_MILLIS = 1000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (seconds * ONE_SECOND_IN_MILLIS));
		return afterAddingMins;
	}

	public static float getGIVEITABLE(int a) {

		return (float) (Math.sqrt(matrixgiveisigma[a]));
	}
	
	public static int getTimeOutMT18() {

		return (timeoutmatrix[6]);
	}
	public static int getTimeOutMT26() {

		return (timeoutmatrix[7]);
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
			lat = line[4];
			lon = line[3];

			if (Integer.parseInt(vtec) == 9999) {

				kml.add("			<Placemark>");
				kml.add("				<name>" + vtec + "</name>");
				kml.add("				<LookAt>");
				kml.add("					<longitude>" + lon + "</longitude>");
				kml.add("					<latitude>" + lat + "</latitude>");
				kml.add("					<altitude>0</altitude>");
				kml.add("					<heading>5.368372822123405</heading>");
				kml.add("					<tilt>0</tilt>");
				kml.add("					<range>3252232.201554738</range>");
				kml.add("					<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>");
				kml.add("				</LookAt>");
				kml.add("				<styleUrl>#VERDE</styleUrl>");
				kml.add("				<Point>");
				kml.add("					<gx:drawOrder>1</gx:drawOrder>");
				kml.add("					<coordinates>" + lon + "," + lat + ",0</coordinates>");
				kml.add("				</Point>");
				kml.add("			</Placemark>");

			} else {
				kml.add("			<Placemark>");
				kml.add("				<name>" + vtec + "</name>");
				kml.add("				<LookAt>");
				kml.add("					<longitude>" + lon + "</longitude>");
				kml.add("					<latitude>" + lat + "</latitude>");
				kml.add("					<altitude>0</altitude>");
				kml.add("					<heading>5.368372822123405</heading>");
				kml.add("					<tilt>0</tilt>");
				kml.add("					<range>3252232.201554738</range>");
				kml.add("					<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>");
				kml.add("				</LookAt>");
				kml.add("				<styleUrl>#ROJO</styleUrl>");
				kml.add("				<Point>");
				kml.add("					<gx:drawOrder>1</gx:drawOrder>");
				kml.add("					<coordinates>" + lon + "," + lat + ",0</coordinates>");
				kml.add("				</Point>");
				kml.add("			</Placemark>");
			}
		}

		kml.add("		</Folder>");
		kml.add("	</Document>");
		kml.add("</kml>");

		return kml;
	}

	private static List<String> getFilehead(String name) {
		List<String> file = new ArrayList<String>();
		file.add("<?xml version='1.0' encoding='UTF-8'?>");
		file.add("<kml xmlns='http://www.opengis.net/kml/2.2' xmlns:gx='http://www.google.com/kml/ext/2.2'");
		file.add("	xmlns:kml='http://www.opengis.net/kml/2.2' xmlns:atom='http://www.w3.org/2005/Atom'>");
		file.add("	<Document>");
		file.add("		<name>" + name + ".kml</name>");
		file.add("		<Style id='s_ylw-pushpin'>");
		file.add("			<IconStyle>");
		file.add("				<color>ff0000ff</color>");
		file.add("				<scale>2</scale>");
		file.add("				<Icon>");
		file.add("					<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png");
		file.add("					</href>");
		file.add("				</Icon>");
		file.add("				<hotSpot x='20' y='2' xunits='pixels' yunits='pixels' />");
		file.add("			</IconStyle>");
		file.add("			<LabelStyle>");
		file.add("				<color>ff0000ff</color>");
		file.add("				<scale>2</scale>");
		file.add("			</LabelStyle>");
		file.add("		</Style>");
		file.add("		<Style id='sn_ylw-pushpin'>");
		file.add("			<IconStyle>");
		file.add("				<color>ff00aa00</color>");
		file.add("				<scale>2</scale>");
		file.add("				<Icon>");
		file.add("					<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png");
		file.add("					</href>");
		file.add("				</Icon>");
		file.add("				<hotSpot x='20' y='2' xunits='pixels' yunits='pixels' />");
		file.add("			</IconStyle>");
		file.add("			<LabelStyle>");
		file.add("				<color>ff00aa00</color>");
		file.add("				<scale>2</scale>");
		file.add("			</LabelStyle>");
		file.add("		</Style>");
		file.add("		<StyleMap id='ROJO'>");
		file.add("			<Pair>");
		file.add("				<key>normal</key>");
		file.add("				<styleUrl>#sn_ylw-pushpin</styleUrl>");
		file.add("			</Pair>");
		file.add("			<Pair>");
		file.add("				<key>highlight</key>");
		file.add("				<styleUrl>#sh_ylw-pushpin</styleUrl>");
		file.add("			</Pair>");
		file.add("		</StyleMap>");
		file.add("		<Style id='sh_ylw-pushpin'>");
		file.add("			<IconStyle>");
		file.add("				<color>ff00aa00</color>");
		file.add("				<scale>2.36364</scale>");
		file.add("				<Icon>");
		file.add("					<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png");
		file.add("					</href>");
		file.add("				</Icon>");
		file.add("				<hotSpot x='20' y='2' xunits='pixels' yunits='pixels' />");
		file.add("			</IconStyle>");
		file.add("			<LabelStyle>");
		file.add("				<color>ff00aa00</color>");
		file.add("				<scale>2</scale>");
		file.add("			</LabelStyle>");
		file.add("		</Style>");
		file.add("		<Style id='s_ylw-pushpin_hl'>");
		file.add("			<IconStyle>");
		file.add("				<color>ff0000ff</color>");
		file.add("				<scale>2.36364</scale>");
		file.add("				<Icon>");
		file.add("					<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png");
		file.add("					</href>");
		file.add("				</Icon>");
		file.add("				<hotSpot x='20' y='2' xunits='pixels' yunits='pixels' />");
		file.add("			</IconStyle>");
		file.add("			<LabelStyle>");
		file.add("				<color>ff0000ff</color>");
		file.add("				<scale>2</scale>");
		file.add("			</LabelStyle>");
		file.add("		</Style>");
		file.add("		<StyleMap id='VERDE'>");
		file.add("			<Pair>");
		file.add("				<key>normal</key>");
		file.add("				<styleUrl>#s_ylw-pushpin</styleUrl>");
		file.add("			</Pair>");
		file.add("			<Pair>");
		file.add("				<key>highlight</key>");
		file.add("				<styleUrl>#s_ylw-pushpin_hl</styleUrl>");
		file.add("			</Pair>");
		file.add("		</StyleMap>");
		file.add("		<Folder>");
		file.add("			<name>" + name + "</name>");
		file.add("			<open>1</open>");
		file.add("			<Style>");
		file.add("				<ListStyle>");
		file.add("					<listItemType>check</listItemType>");
		file.add("					<ItemIcon>");
		file.add("						<state>open</state>");
		file.add("						<href>:/mysavedplaces_open.png</href>");
		file.add("					</ItemIcon>");
		file.add("					<ItemIcon>");
		file.add("						<state>closed</state>");
		file.add("						<href>:/mysavedplaces_closed.png</href>");
		file.add("					</ItemIcon>");
		file.add("					<bgColor>00ffffff</bgColor>");
		file.add("					<maxSnippetLines>2</maxSnippetLines>");
		file.add("				</ListStyle>");
		file.add("			</Style>");
		return file;
	}
}
