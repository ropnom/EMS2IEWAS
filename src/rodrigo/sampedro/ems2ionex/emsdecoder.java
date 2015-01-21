package rodrigo.sampedro.ems2ionex;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.ErrorLog;
import Model.FunctionsExtra;
import Model.Jget;
import Model.LoadDataFile;
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
	private static short day = 12;
	private static short hour = 0;

	// Download parameters
	private static short inityear = (short) Calendar.getInstance().get(Calendar.YEAR);
	private static short initday = 3;
	private static short endday = 3;
	private static short inithour = 23;
	private static short endhour = 23;
	private static int MAXDAY = 365;

	// Program modes
	private static short mode = 0;// file mode
	private static boolean debug = false;
	private static ErrorLog log;

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
		if (show) {
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

		} catch (Exception e) {

			for (int j = 0; j < 200; j++) {
				System.out.println();
			}
			// e.printStackTrace();
		}

		System.out.println("\n");
		System.out.println("  ******************************************");
		System.out.println("  | DONWLOADING FILES FROM ESA SERVER ...  |");
		System.out.println("  ******************************************");
		System.out.println("\n");
		System.out.print("  +");
		for (int l = 0; l < i; l++) {
			System.out.print("-");
		}

		System.out.print("  " + String.format("%.3f", porcent) + "% Downloading...");

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

	// *********************************************************************
	// ------------------------------ MAIN --------------------------------
	// *********************************************************************

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		// Start Log
		log = ErrorLog.getInstance();

		// Load propierties file

		// code debug
		debug = true;
		mode = 1;

		if (debug) {
			// DEMO debug

			// *********** INPUT TEST PROGRAM AND MENUS ***********
			// args = new String[] { "-PRN120", "-TODAY" };
			// args = new String[] { "-PRN126", "-TODAY" };
			args = new String[] { "-PRN120", "-D", "127", "-Y", "2014" };
			// args = new String[] { "-PRN120", "-D" , "25", "-H", "14"};

			// *************************************************
		}

		// Log input parameters
		log.AddError(" INPUT PARAMETERS: '" + args.toString() + "' \n");

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
		}

		// Check argmument input
		for (int i = 0; i < args.length; i++) {
			// MODE
			if (args[i] == "-ModeFile")
				mode = 1;
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
				//Not implemented yet
//				int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1;
//				if (today == 0) {
//					inityear = (short) (inityear - 1);
//					initday = 365;
//					if (inityear % 4 == 0 && inityear % 100 != 0 || inityear % 400 == 0) {
//						initday = 366;
//					}
//					endday = 1;
//				} else {
//					initday = (short) (today - 1);
//					endday = (short) today;
//				}
				int today = Short.parseShort(args[i + 1]);
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
				log.AddError(e.getStackTrace().toString());
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
				log.AddError(e.getStackTrace().toString());

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
				log.AddError(e.getStackTrace().toString());

				System.err.println("\n Input format was Wrong.");
				System.err.println("\n Use -help.");
				System.err.println("Trying to parse string to short, variable hour: '" + args[i + 1] + "'");
				System.err.println(e.getStackTrace());
				System.exit(1);
			}
		}

		// ----------------------------------------------
		// ********* END ARGUMENT INPUT ********
		// ----------------------------------------------

		// PROCESING PART

		if (show) {
			// Download algoritm item pre-calculate quantities
			if (endday > initday)
				visual = visual * ((24 - inithour) + endhour + 24 * (endday - initday));
			else
				visual = visual * (endhour - inithour);
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
						originalmessage.addAll(wget.Dowload(url));
					} catch (Exception e) {
						log.AddError("\n Input format was Wrong.");
						log.AddError("\n Use -help.");
						log.AddError("\n Traying to dowload from url: " + url);
						log.AddError(e.getStackTrace().toString());

						System.err.println("\n Input format was Wrong.");
						System.err.println("\n Use -help.");
						System.err.println("\n Traying to dowload from url: " + url);
						System.err.println(e.getStackTrace());
						System.exit(1);
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

			// Write Current Data
			WriteCurrentData writer = new WriteCurrentData();
			writer.Write(originalmessage);

		} else if (mode == 1) {
			// LOAD DATA FROM FILE
			LoadDataFile load = new LoadDataFile();
			originalmessage = load.LoadData();
		}

		// -----------------------------------
		// ********* DECODING DATA ***********
		// -----------------------------------

		for (int i = 0; i < originalmessage.size(); i++) {
			// filtramos los mensajes tipo 18 y 26
			message = new Message(originalmessage.get(i), i);

			if (message.getMessagetype() > 0) {
				ionosfericmessage.add(message);
				ionosphericdata.add(message.getOriginal());
				human.addAll(message.WriteHumanFile());
			}

		}

		// Write Current Data
		WriteCurrentData writer = new WriteCurrentData();
		writer.setFilename("ionohumanmessage.txt");
		writer.Write(human);

		writer.setFilename("ionosphericdates.txt");
		writer.Write(ionosphericdata);

		// Cargamos la matrix de datos

		mygrid = new MapGrid();
		reorder = new Reciverorder();
		
		
		Date finalizacion;
		Date referencia;
		MessageType26 mt26;
		IonexInputFile makefiles;

		// generamos el date de referencia inicio
		finalizacion = (Date) ionosfericmessage.get(0).getTime().clone();
		finalizacion.setHours(0);
		finalizacion.setMinutes(0);
		finalizacion.setSeconds(0);

		System.out.println("Inicio a: " + finalizacion);
		finalizacion = FunctionsExtra.addDayToDate(1, finalizacion);
		System.out.println("Fin de generacion de archivos : " + finalizacion);
		System.out.println();
		System.out.println();

		// generamos el date de referencia inicio
		referencia = (Date) ionosfericmessage.get(0).getTime().clone();
		referencia.setMinutes(0);
		referencia.setSeconds(0);

		System.out.println("Archivo inicia a: " + referencia);
		referencia = FunctionsExtra.addSecondsToDate(intervaloseg, referencia);
		System.out.println("intervalo hasta : " + referencia);
		System.out.println();

		int j = 0;
		for(int i = 0; ValidTimeToProcess(i, finalizacion); i++) {

			if (ValidTimeToProcess(i, referencia)) {

				// Procesamos el mensaje
				if (ionosfericmessage.get(i).getMessagetype() == 18) {
					// procesamos el mensaje

					reorder.ProcessMT18(((MessageType18) ionosfericmessage.get(i).getPayload()), ionosfericmessage.get(i).getTime());

				} else {
					// miramos el iodi y fecha para saber si es valido
					mt26 = (MessageType26) ionosfericmessage.get(i).getPayload();
					if (reorder.IsValidMessage(ionosfericmessage.get(i).getTime(), mt26.getIoid(), mt26.getBandnumber())) {

						// obtenemos la lista de valores a obtener
						List<Integer> bandnumbers = reorder.getMatrix()[mt26.getIoid()][mt26.getBandnumber()].getBandX();
						for (int m = 15 * mt26.getBlockid(); m < bandnumbers.size(); m++) {
							// procesamos en la matriz el punto
							mygrid.PutPoint(mt26.getBandnumber(), bandnumbers.get(m), mt26.getGridpoints()[m % 14 + 1].getVtec(), mt26.getGridpoints()[m % 14 + 1].getRms(), ionosfericmessage.get(i).getTime());
						}

					}

				}
			} else{
				referencia = FunctionsExtra.addSecondsToDate(intervaloseg, referencia);				
				makefiles = new IonexInputFile();
				makefiles.setVersion(j);
				j++;
				makefiles.GridToInput(mygrid, initday, inityear);
				System.out.println("nuevo intervalo hasta : " + referencia);				
			}
				

		}

		// guardar la matriz historico reorder historico y los datos
	
		mygrid.Save();
		reorder.Save();

		System.out.println("FIN **");

	}
}
