package rodrigo.sampedro.ems2ionex;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.ErrorLog;
import Model.FunctionsExtra;
import Model.Jget;
import Model.LoadDataFile;
import Model.Throwables;
import Model.WriteCurrentData;
import Model.TypeMessage.MessageType18;
import Model.TypeMessage.MessageType26;
import Model.map.IonexInputFile;
import Model.map.MapGrid;
import Model.map.Reciverorder;
import Model.message.Message;

public class emsdecoder {

	// ------------------------------------------------------
	// ******** VARAIABLES AND PARAMETERS OF PROGRAM ********
	// ------------------------------------------------------

	// DECLARE VARIABLES IN PROGRAM

	// console viewer screen
	private static float porcent = 0;
	private static int count = 0;
	private static int visual = 3600;
	private static boolean show = false;

	// ESA Server parameters
	private static String server = "ftp://ems.estec.esa.int/pub/";
	private static String prn = "PRN120/";

	// Download parameters
	private static short day = 1;
	private static short hour = 0;
	private static short referencehour = 0;
	private static short year = (short) Calendar.getInstance().get(Calendar.YEAR);
	private static short inityear = (short) Calendar.getInstance().get(Calendar.YEAR);
	private static short endyear = (short) Calendar.getInstance().get(Calendar.YEAR);
	private static short initday = 1;
	private static short referenceday = 1;
	private static short endday = 365;
	private static short rangeinitday = 1;
	private static short rangeendday = 365;
	private static short inithour = 0;
	private static short endhour = 23;
	private static int MAXDAY = 365;
	private static int numreintents = 3;
	private static Jget wget;

	// Program modes
	private static short mode = 0;// file mode
	private static ErrorLog log;
	private static boolean humanwrite = false;
	private static boolean rangedata = false;
	private static boolean kmlwrite = false;
	private static String datafile = null;

	// Program output
	private static int intervaloseg = 900;

	// Decode Message variables
	private static Message message = null;
	private static List<String> originalmessage = new ArrayList<String>();
	private static List<String> human = new ArrayList<String>();
	private static List<String> ionosphericdata = new ArrayList<String>();
	private static List<Message> ionosfericmessage = new ArrayList<Message>();
	private static MapGrid mygrid;
	private static Reciverorder reorder;

	// -------------------------------------
	// ******** SCREEN FUNCTIONS ********
	// -------------------------------------

	public static void pasarGarbageCollector() {

		Runtime garbage = Runtime.getRuntime();
		garbage.gc();

	}

	public static void PrintHelp() {
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println(" ***                      EMS DECODER 0.9 BETA   --HELP                           ***");
		System.out.println(" -------------------------------------------------------------------------------------");
		System.out.println("");
		System.out.println(" -ModeFileC  				: Use currentmessage.txt as server of Egnos information.");
		System.out.println(" -ModeFile  [filename]			: Use [filename] as server of Egnos information.");
		System.out.println(" -Show					: Show in the screen the % of download and events.");
		System.out.println(" -PRN120				: Use Egnos Message from PRN120 (by default).");
		System.out.println(" -PRN126				: Use Egnos Message from PRN126.");
		System.out.println(" -TODAY					: Download the EMS message from 23h 2 days ago until 0:00 of today.");
		System.out.println(" -Y [XXXX]				: Specific Year where XXXX is per example 2012.");
		System.out.println(" -D [XXX]				: Specific Day, number of the day 0-365 (366) days of the year.");
		System.out.println(" -H [XX]				: Specific Hour, number of hours 0-23 h.");
		System.out.println(" -whuman				: Specific to write human format file of data message decoded.");
		System.out.println(" -numintent [X]			: Specific number of reintent of ftp dowload.");
		System.out.println(" -kml					: Specific to create a kml file to view date in google earth.");
		System.out.println("");
		System.out.println("---------------------------------------------------------------------------------------");
		System.out.println("");
		System.out.println(" *** Examples:");
		System.out.println(" emsdecoder -Show -PRN126 -TODAY");
		System.out.println(" emsdecoder -Show -PRN126 -Y 2014 -D 324 -H 15");
		System.out.println(" emsdecoder -Show -PRN126 -Y 2014 -D 324 -H 15");
		System.out.println(" emsdecoder -PRN120 -D 120 -Y 2015 ");
		System.out.println(" emsdecoder -PRN120 -D 25 -H 14");
		System.out.println(" emsdecoder -TODAY -whuman -numintent 4");
		System.out.println(" emsdecoder -TODAY -Show");
		System.out.println(" emsdecoder -ModeFileC");
		System.out.println(" emsdecoder -ModeFile data.txt");
	}

	public static void Countline() {
		count++;

		if (count % 600 == 0) {
			int i = ((100 * count) / visual);
			float newporcent = (100 * (float) count) / visual;

			if (newporcent - porcent > 0.01) {
				porcent = newporcent;
				ViewonScreen(i);
			}
		}

	}

	public static void ViewonScreen(int i) {

		try {
			Process exitCode;
			Runtime r = Runtime.getRuntime();
			if (System.getProperty("os.name").startsWith("Window")) {
				exitCode = r.exec("cls");
			} else {
				exitCode = r.exec("clear");
			}
			System.out.println(exitCode);
			System.out.println("\n");
			System.out.println("  ******************************************");
			System.out.println("  | DONWLOADING FILES FROM ESA SERVER ...  |");
			System.out.println("  ******************************************");
			System.out.println("\n");

		} catch (Exception e) {

			// for (int j = 0; j < 200; j++) {
			// System.out.println();
			// }
			// // e.printStackTrace();
		}

		System.out.print("Dowloading:  +");
		for (int l = 0; l < i; l++) {
			System.out.print("-");
		}

		System.out.print("  " + String.format("%.2f", porcent) + "% ");
		System.out.println();

		if (i >= 100) {
			System.out.print("+  " + String.format("%.2f", porcent) + "% COMPLETE");
			System.out.println("END");
		}
	}

	private static boolean ValidTimeToProcess(int i, Date referencia) {

		if (i < ionosfericmessage.size()) {
			return (referencia.compareTo(ionosfericmessage.get(i).getTime()) > 0);
		} else {
			return false;
		}
	}

	public static String ArraytoString(String[] array) {
		String value = "";

		for (int i = 0; i < array.length; i++) {
			value += array[i] + " ";
		}

		return value;

	}

	// *********************************************************************
	// ------------------------------ MAIN --------------------------------
	// *********************************************************************

	public static void main(String[] args) {

		System.out.println();
		System.out.println("*******************************************************************");
		System.out.println("| Starting EMS decoding program by Ropnom 1.0 lastupdate: 15/04/15 |");
		System.out.println("*******************************************************************");
		System.out.println();
		System.out.println("Starting.... : " + new Date());
		System.out.println();

		// Start Log
		log = ErrorLog.getInstance();

		// code debug
		mode = 0;

		// args = new String[] { "-TODAY", "-Show" };
		args = new String[] {"-RYearD1D2", "2015", "5", "6", "-Show"};

		// // *********** INPUT TEST PROGRAM AND MENUS ***********
		// args = new String[] { "-PRN120", "-TODAY" };
		// args = new String[] { "-PRN126", "-TODAY" };
		// args = new String[] { "-PRN120", "-D", "120", "-Y", "2015" };
		// args = new String[] { "-PRN120", "-D" , "25", "-H", "14"};
		// args = new String[] { "-PRN120", "-D", "20" };
		//
		// // *************************************************

		// Log input parameters
		System.out.println("Input parameters are : " + ArraytoString(args));
		System.out.println();
		log.AddError(" INPUT PARAMETERS: '" + ArraytoString(args) + "' \n");

		// ---------------------------------------- //
		// ********* STAR ARGUMENT INPUT ********
		// ---------------------------------------- //

		// Check input arguments
		if ((args.length == 0)) {
			PrintHelp();
			System.err.println("\n Input Argument Error use --HELP");
			log.AddError("\n Input Argument Error use --HELP");
			log.WriteLog();
			System.exit(1);
			// Close execution of the program
		}

		// 3 Type of input
		// Today Date
		// Concrete Dates (over internet or over file)
		// Range of days
		Checkargument(args);
		pasarGarbageCollector();
		// PROCESING PART
		mygrid = new MapGrid();
		// cargamos la matriz
		// Cargamos la matrix de datos
		if (show)
			System.out.println("** Loading previous matrix and reorder...");
		reorder = new Reciverorder();

		if (mode == 0) {

			if (rangedata) {
				if (rangeendday == 1) {
					inityear = (short) (inityear - 1);
					if (inityear % 4 == 0 && inityear % 100 != 0 || inityear % 400 == 0) {
						initday = 366;
					} else
						initday = 365;

					endday = 1;
				} else {

					initday = (short) (rangeinitday - 1);
					endday = (short) (rangeinitday);
				}
				inithour = 23;
				endhour = 23;
				referenceday = initday;
				referencehour = 0;

				for (year = inityear; year <= endyear; year++) {
					while (initday <= rangeendday) {
						originalmessage = new ArrayList<String>();
						human = new ArrayList<String>();
						ionosphericdata = new ArrayList<String>();
						ionosfericmessage = new ArrayList<Message>();
						DowloadData();
						pasarGarbageCollector();
						Filter();
						WriteData();
						Decoding();

						initday++;
						referenceday = initday;
						endday = initday;

					}
				}
			} else {
				DowloadData();
				pasarGarbageCollector();
				Filter();
				WriteData();
				Decoding();
			}
		} else if (mode == 1) {
			// LOAD DATA FROM FILE
			LoadDataFile load = new LoadDataFile();
			if (datafile != null)
				load.setFile(datafile);
			originalmessage = load.LoadData();
			if (show)
				System.out.println("** Load data finished...");
		}

		pasarGarbageCollector();
		System.out.println();
		System.out.println("END PROGRAMM... **");
		System.out.println();
		System.out.println("** PLEASE SEE 'log_error.txt' OF PROGRAM AND 'missingdata_YEAR_DAY'.txt' **");

	}

	public static void Checkargument(String[] args) {

		// Check argmument input
		for (int i = 0; i < args.length; i++) {
			// MODE
			if (args[i].equals("-ModeFileC")) {
				mode = 1;
				datafile = args[i + 1];
			}

			// Show on screen
			if (args[i].equals("-Show"))
				show = true;

			// PRN satellites
			if (args[i].equals("-PRN120"))
				prn = "PRN120/";
			if (args[i].equals("-PRN122"))
				prn = "PRN122/";
			if (args[i].equals("-PRN124"))
				prn = "PRN124/";
			if (args[i].equals("-PRN126"))
				prn = "PRN126/";
			if (args[i].equals("-PRN131"))
				prn = "PRN131/";
			if (args[i].equals("-PRN136"))
				prn = "PRN131/";

			// Today
			if (args[i].equals("-TODAY")) {
				referenceday = (short) (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1);
				if (referenceday == 0) {
					inityear = (short) (inityear - 1);
					initday = 365;
					if (inityear % 4 == 0 && inityear % 100 != 0 || inityear % 400 == 0) {
						initday = 366;
					}
					endday = 1;
				} else {
					initday = (short) (referenceday - 1);
					endday = (short) referenceday;
				}
				inithour = 23;
				endhour = 23;
			}

			// Year
			try {
				if (args[i].equals("-Y")) {
					inityear = Short.parseShort(args[i + 1]);
				}
			} catch (Exception e) {

				log.AddError("\n Input format was Wrong.");
				log.AddError("\n Use -help.");
				log.AddError("Trying to parse string to short, variable inityear: '" + args[i + 1] + "'");
				log.AddError(Throwables.getStackTraceAsString(e));
				log.WriteLog();

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable inityear: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}

			// Day
			try {
				if (args[i].equals("-D")) {
					referenceday = Short.parseShort(args[i + 1]);
					initday = (short) referenceday;
					endday = (short) referenceday;
					if (referenceday == 1) {
						inityear = (short) (inityear - 1);

						if (inityear % 4 == 0 && inityear % 100 != 0 || inityear % 400 == 0) {
							initday = 366;
						} else
							initday = 365;

						endday = 1;
					}
					inithour = 23;
					endhour = 23;
				}
			} catch (Exception e) {
				log.AddError("\n Input format was Wrong.");
				log.AddError("\n Use -help.");
				log.AddError("Trying to parse string to short, variable today: '" + args[i + 1] + "'");
				log.AddError(Throwables.getStackTraceAsString(e));

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable today: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}

			// Hour
			try {
				if (args[i].equals("-H")) {
					referencehour = Short.parseShort(args[i + 1]);
					if (referencehour == 0)
						inithour = 23;
					else {
						inithour = (short) (referencehour - 1);
						endhour = (short) referencehour;
					}
				}
			} catch (Exception e) {
				log.AddError("\n Input format was Wrong.");
				log.AddError("\n Use -help.");
				log.AddError("Trying to parse string to short, variable hour: '" + args[i + 1] + "'");
				log.AddError(Throwables.getStackTraceAsString(e));

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable hour: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}

			// Range
			try {
				if (args[i].equals("-RYear")) {
					inityear = Short.parseShort(args[i + 1]);
					rangeendday = 1;
					if (inityear % 4 == 0 && inityear % 100 != 0 || inityear % 400 == 0)
						rangeendday = 366;
					else
						rangeendday = 365;

					rangedata = true;
				}
			} catch (Exception e) {

				log.AddError("\n Input format was Wrong.");
				log.AddError("\n Use -help.");
				log.AddError("Trying to parse string to short, variable rangeyear: '" + args[i + 1] + "'");
				log.AddError(Throwables.getStackTraceAsString(e));
				log.WriteLog();

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable rangeyear: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}

			// Range day
			try {
				if (args[i].equals("-RYearD1D2")) {
					inityear = Short.parseShort(args[i + 1]);
					endyear = Short.parseShort(args[i + 1]);
					rangeinitday = Short.parseShort(args[i + 2]);
					rangeendday = Short.parseShort(args[i + 3]);
					rangedata = true;
				}
			} catch (Exception e) {

				log.AddError("\n Input format was Wrong.");
				log.AddError("\n Use -help.");
				log.AddError("Trying to parse string to short, variable rangeyear d1 and d2: '" + args[i + 1] + "'");
				log.AddError(Throwables.getStackTraceAsString(e));
				log.WriteLog();

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable rangeyear d1 and d2: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}

			// Range year and day
			try {
				if (args[i].equals("-RY1D1Y2D2")) {
					inityear = Short.parseShort(args[i + 1]);
					initday = Short.parseShort(args[i + 2]);
					endyear = Short.parseShort(args[i + 3]);
					endday = Short.parseShort(args[i + 4]);
					rangedata = true;
				}
			} catch (Exception e) {

				log.AddError("\n Input format was Wrong.");
				log.AddError("\n Use -help.");
				log.AddError("Trying to parse string to short, variable rangeyear1 d1 and year2 d2: '" + args[i + 1] + "'");
				log.AddError(Throwables.getStackTraceAsString(e));
				log.WriteLog();

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable rangeyear1 d1 and year2 d2: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}

			// check human file
			if (args[i].equals("-kml")) {
				kmlwrite = true;
			}

			// check human file
			if (args[i].equals("-whuman")) {
				humanwrite = true;
			}

			// Num intents
			try {
				if (args[i].equals("-numintent")) {
					numreintents = Integer.parseInt(args[i + 1]);
				}
			} catch (Exception e) {
				log.AddError("\n Input format was Wrong.");
				log.AddError("\n Use -help.");
				log.AddError("Trying to parse string to short, variable hour: '" + args[i + 1] + "'");
				log.AddError(Throwables.getStackTraceAsString(e));

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable hour: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}

		}

		// ----------------------------------------------
		// ********* END ARGUMENT INPUT ********
		// ----------------------------------------------/check argument

	}

	public static void DowloadData() {

		if (show) {
			// Download algoritm item pre-calculate quantities
			if (endday > initday) {
				visual = visual * ((24 - inithour) + endhour + 24 * (endday - initday));
			} else {
				visual = visual * (endhour - inithour);
			}
			System.out.println("Total EGNOS message to download: " + visual + " EMS");
		}

		// PREPARE DOWNLOAD LIST FILES AND GET IT

		// Configure Jget
		wget = new Jget();
		wget.setIntentos(numreintents);
		wget.setShow(show);
		// calcule if the day have 365 or 366 days
		if (inityear % 4 == 0 && inityear % 100 != 0 || inityear % 400 == 0) {
			MAXDAY = 366;
		}
		boolean lastday = false;
		boolean lasthour = false;
		

		for (day = initday; !lastday; day++) {
			lasthour = false;
			
			for (hour = inithour; !lasthour; hour++) {
				String url = server + prn + "y" + year + "/d" + String.format("%03d", day) + "/h" + String.format("%02d", hour) + ".ems";
				// MAKE DOWLOAD
				try {

					if (show)
						System.out.println("Dowloading: " + url);

					originalmessage.addAll(wget.Dowload(url));
					

				} catch (Exception e) {
					log.AddError("\n FTP download was Wrong.");
					log.AddError("\n ");
					log.AddError("\n ***************************** ");
					log.AddError("\n Traying to dowload from url: " + url);
					log.AddError("\n ***************************** ");
					log.AddError(Throwables.getStackTraceAsString(e));
					log.AddFileError(year+" "+ day +" "+ url);

					System.err.println("\n FTP download was Wrong.");
					System.err.println("\n Traying to dowload from url: " + url);
					System.err.println();
					System.err.println(Throwables.getStackTraceAsString(e));

				}
				if (hour == endhour || hour == 23) {
					lasthour = true;
				}
				
			}
			inithour = 0;
			if (day == endday) {
				lastday = true;
			}
			if (day == MAXDAY) {
				day = 1;
			}
		}

	}

	public static void Filter() {
		if (show)
			System.out.println("** Filter data procesing...");

		for (int i = 0; i < originalmessage.size(); i++) {
			// filtramos los mensajes tipo 18 y 26
			message = new Message(originalmessage.get(i), i);

			if (message.getMessagetype() > 0) {
				ionosfericmessage.add(message);
				ionosphericdata.add(message.getOriginal());
			}
			if (humanwrite)
				human.addAll(message.WriteHumanFile());

		}
	}

	public static void WriteData() {

		if (show)
			System.out.println("** Writing current data...");
		// Write Current Data
		WriteCurrentData writer = new WriteCurrentData();
		writer.Write(ionosphericdata);
		if (humanwrite) {
			if (show)
				System.out.println("** Writing human decode dates ...");
			writer = new WriteCurrentData();
			writer.setFilename("ionohumanmessage.txt");
			writer.Write(human);
		}

	}

	public static void Decoding() {

		// -----------------------------------
		// ********* DECODING DATA ***********
		// -----------------------------------

		// ***** Internal variables to dowload ******

		Date finalizacion;
		Date referencia;
		MessageType26 mt26;
		IonexInputFile makefiles;

		if (ionosfericmessage.size() <1) {
			log.AddError("\n Fatal error ionosferic message array is empy.");
			System.err.println("\n Fatal error ionosferic message array is empy.");
			System.err.println();
			//System.exit(1);
		}

		// Generate date of reference to init and finish

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, inityear);
		calendar.set(Calendar.DAY_OF_YEAR, referenceday);
		calendar.set(Calendar.HOUR_OF_DAY, referencehour);
		referencia = calendar.getTime();
		System.out.println(referencia);

		if (show) {
			System.out.println("** Decoding and generate files...");
			System.out.println();
			System.out.println("** Inicio a: " + referencia);
		}
		makefiles = new IonexInputFile();
		makefiles.setInit(referencia);
		// generate date for processing in block of time
		finalizacion = FunctionsExtra.addDayToDate(1, referencia);

		if (show)
			System.out.println("** Fin de generacion de archivos : " + finalizacion);

		mygrid.setInit(ionosfericmessage.get(1).getTime());
		mygrid.setFinish(referencia);

		int j = 0;
		for (int i = 0; ValidTimeToProcess(i, finalizacion); i++) {

			// Is valid time of message??
			if (ValidTimeToProcess(i, referencia)) {

				// Procesing message
				if (ionosfericmessage.get(i).getMessagetype() == 18) {
					// type 18
					reorder.ProcessMT18(((MessageType18) ionosfericmessage.get(i).getPayload()), ionosfericmessage.get(i).getTime());

				} else {
					// see iodi and is valid date?
					mt26 = (MessageType26) ionosfericmessage.get(i).getPayload();
					if (reorder.IsValidMessage(ionosfericmessage.get(i).getTime(), mt26.getIoid(), mt26.getBandnumber())) {

						// get list of value to insert into matrix
						List<Integer> bandnumbers = reorder.getMatrix()[mt26.getIoid()][mt26.getBandnumber()].getBandX();
						for (int m = 15 * mt26.getBlockid(); (m < (15 * (1 + mt26.getBlockid())) && m < bandnumbers.size()); m++) {

							mygrid.PutPoint(mt26.getBandnumber(), bandnumbers.get(m), mt26.getGridpoints()[m % 15].getVtec(), mt26.getGridpoints()[m % 15].getRms(), ionosfericmessage.get(i).getTime());

						}

					}

				}
			} else {
				// make file
				makefiles.setVersion(j + 1);
				makefiles.GridToInput(mygrid, referenceday, inityear, kmlwrite, show);
				mygrid.SaveEGNOS(inityear+"_"+referenceday+"_"+(j+1));
				
				// new reference block time
				mygrid.setInit(referencia);
				referencia = FunctionsExtra.addSecondsToDate(intervaloseg, referencia);
				mygrid.setFinish(referencia);
				j++;
				i--;
			}

		}

		// guardar la matriz historico reorder historico y los datos
		// write last file, log, save matrix and reorder for future simulations

		makefiles.setVersion(j + 1);
		makefiles.GridToInput(mygrid, referenceday, inityear, kmlwrite, show);
		makefiles.setFinish(ionosfericmessage.get(ionosfericmessage.size() - 1).getTime());

		if (show)
			System.out.println("** Create Info File of files");
		makefiles.GenParametersInfoFile(inityear, referenceday, intervaloseg);
		mygrid.Save();
		reorder.Save();
		log.WriteLog();
		log.WriteFileError((inityear + "_" + day));

	}

}
