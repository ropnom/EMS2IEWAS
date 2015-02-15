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
	private static short inityear = (short) Calendar.getInstance().get(Calendar.YEAR);
	private static short initday = 0;
	private static short endday = 0;
	private static short inithour = 23;
	private static short endhour = 23;
	private static int MAXDAY = 365;
	private static int numreintents = 3;

	// Program modes
	private static short mode = 0;// file mode
	private static boolean debug = false;
	private static ErrorLog log;
	private static boolean humanwrite = false;
	private static boolean kmlwrite = false;

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

	public static void PrintHelp() {
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println(" ***                      EMS DECODER 0.01 ALFA  --HELP                           ***");
		System.out.println(" -------------------------------------------------------------------------------------");
		System.out.println("");
		System.out.println(" --ModeFile  : Use currentmessage.txt as server of Egnos information.");
		System.out.println(" -Show		: Show in the screen the % of download and events.");
		System.out.println(" -PRN120		: Use Egnos Message from PRN120 (by default).");
		System.out.println(" -PRN126		: Use Egnos Message from PRN126.");
		System.out.println(" -TODAY		: Download the EMS message from 23h 2 days ago until 0:00 of today.");
		System.out.println(" -Y XXXX		: Specific Year where XXXX is per example 2012.");
		System.out.println(" -D XXX		: Specific Day, number of the day 0-365 (366) days of the year.");
		System.out.println(" -H			: Specific Hour, number of hours 0-23 h.");
		System.out.println("");
		System.out.println(" Example:");
		System.out.println(" emsdecoder -Show -PRN126 -TODAY");
		System.out.println(" emsdecoder -Show -PRN126 -Y 2014 -D 324 -H 15");
	}

	public static synchronized void Countline() {
		count++;

		if (count % 1800 == 0) {
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

		System.out.print("  +");
		for (int l = 0; l < i; l++) {
			System.out.print("-");
		}

		System.out.print("  " + String.format("%.2f", porcent) + "%    Downloaded");
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
		System.out.println("| Starting EMS decoding program by Ropnom 0.9 lastupdate: 28/01/15 |");
		System.out.println("*******************************************************************");
		System.out.println();
		System.out.println("Starting.... : " + new Date());
		System.out.println();

		// Start Log
		log = ErrorLog.getInstance();

		// Load propierties file

		// code debug
		debug = false;
		mode = 0;
		args = new String[] { "-PRN120", "-TODAY", "-Show" };

		if (debug) {
			// DEMO debug

			// *********** INPUT TEST PROGRAM AND MENUS ***********
			args = new String[] { "-PRN120", "-TODAY" };
			// args = new String[] { "-PRN126", "-TODAY" };
			// args = new String[] { "-PRN120", "-D", "120", "-Y", "2015" };
			// args = new String[] { "-PRN120", "-D" , "25", "-H", "14"};
			// args = new String[] { "-PRN120", "-D", "20" };

			// *************************************************
		}

		// Log input parameters
		System.out.println("Input parameters are : " + ArraytoString(args));
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

		// Check argmument input
		for (int i = 0; i < args.length; i++) {
			// MODE
			if (args[i] == "-ModeFile") {
				mode = 1;

				// check file name??¿¿
			}

			// Show on screen
			if (args[i] == "-Show")
				show = true;

			// PRN satellites
			if (args[i] == "-PRN120")
				prn = "PRN120/";
			if (args[i] == "-PRN122")
				prn = "PRN122/";
			if (args[i] == "-PRN124")
				prn = "PRN124/";
			if (args[i] == "-PRN126")
				prn = "PRN126/";
			if (args[i] == "-PRN131")
				prn = "PRN131/";

			// Today
			if (args[i] == "-TODAY") {
				// Only download yesterday dates from the server
				int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1;
				initday = (short) today;
				endday = (short) today;
				inithour = 0;
				endhour = 23;
			}

			// Year
			try {
				if (args[i] == "-Y") {
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
				if (args[i] == "-D") {
					int today = Short.parseShort(args[i + 1]);
					initday = (short) today;
					endday = (short) today;
					inithour = 0;
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
				if (args[i] == "-H") {
					int h = Short.parseShort(args[i + 1]);
					inithour = (short) h;
					endhour = (short) h;
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

			// check human file
			if (args[i] == "-kml") {
				kmlwrite = true;
			}

		}

		// ----------------------------------------------
		// ********* END ARGUMENT INPUT ********
		// ----------------------------------------------

		// PROCESING PART

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
		if (mode == 0) {
			// Configure Jget
			Jget wget = new Jget();
			// calcule if the day have 365 or 366 days
			if (inityear % 4 == 0 && inityear % 100 != 0 || inityear % 400 == 0) {
				MAXDAY = 366;
			}
			boolean lastday = false;
			boolean lasthour = false;
			for (day = initday; !lastday; day++) {
				lasthour = false;
				for (hour = inithour; !lasthour; hour++) {
					String url = server + prn + "y" + inityear + "/d" + String.format("%03d", day) + "/h" + String.format("%02d", hour) + ".ems";
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

		} else if (mode == 1) {
			// LOAD DATA FROM FILE
			LoadDataFile load = new LoadDataFile();
			originalmessage = load.LoadData();
			if (show)
				System.out.println("** Load data finished...");
		}

		// -----------------------------------
		// ********* DECODING DATA ***********
		// -----------------------------------

		if (show)
			System.out.println("** Filter data procesing...");

		for (int i = 0; i < originalmessage.size(); i++) {
			// filtramos los mensajes tipo 18 y 26
			message = new Message(originalmessage.get(i), i);

			if (message.getMessagetype() > 0) {
				ionosfericmessage.add(message);
				ionosphericdata.add(message.getOriginal());
				if (humanwrite)
					human.addAll(message.WriteHumanFile());
			}

		}

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

		// Cargamos la matrix de datos
		// version 0.9 nose carga se utilizan lso mensajes tal cual se piden.
		if (show)
			System.out.println("** Loading previous matrix and reorder...");
		mygrid = new MapGrid();
		reorder = new Reciverorder();

		// ***** Internal variables to dowload ******

		Date finalizacion;
		Date referencia;
		MessageType26 mt26;
		IonexInputFile makefiles;

		if (ionosfericmessage.size() <= 2) {
			log.AddError("\n Fatal error ionosferic message array is empy.");
			System.err.println("\n Fatal error ionosferic message array is empy.");
			System.err.println();
			System.exit(1);
		}

		// Generate date of reference to init and finish
		finalizacion = (Date) ionosfericmessage.get(1).getTime().clone();
		finalizacion.setMinutes(0);
		finalizacion.setSeconds(0);

		if (show) {
			System.out.println("** Decoding and generate files...");
			System.out.println();
			System.out.println("** Inicio a: " + finalizacion);
		}
		makefiles = new IonexInputFile();
		makefiles.setInit(finalizacion);
		finalizacion = FunctionsExtra.addDayToDate(1, finalizacion);
		if (show)
			System.out.println("** Fin de generacion de archivos : " + finalizacion);

		// generate date for processing in block of time
		referencia = (Date) ionosfericmessage.get(1).getTime().clone();
		referencia.setMinutes(0);
		referencia.setSeconds(0);
		mygrid.setInit(referencia);
		referencia = FunctionsExtra.addSecondsToDate(intervaloseg, referencia);
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
				//new reference block time 
				mygrid.setInit(referencia);
				referencia = FunctionsExtra.addSecondsToDate(intervaloseg, referencia);
				mygrid.setFinish(referencia);
				makefiles.setVersion(j + 1);
				j++;
				//make file
				makefiles.GridToInput(mygrid, initday, inityear, kmlwrite, show);
				i--;
			}

		}

		// guardar la matriz historico reorder historico y los datos
		// write last file, log, save matrix and reorder for future simulations

		mygrid.setInit(referencia);
		referencia = FunctionsExtra.addSecondsToDate(intervaloseg, referencia);
		mygrid.setFinish(referencia);
		makefiles.setVersion(j + 1);
		makefiles.GridToInput(mygrid, initday, inityear, kmlwrite, show);

		makefiles.setVersion(j);
		makefiles.setFinish(ionosfericmessage.get(ionosfericmessage.size() - 1).getTime());
		// makefiles.setFinish(ionosfericmessage.get(i-1).getTime());

		if(show)
			System.out.println("** Create Info File of files");
		makefiles.GenParametersInfoFile(inityear, initday, intervaloseg);
		mygrid.Save();
		reorder.Save();
		log.WriteLog();

		System.out.println("FIN **");

	}
}
