package Model;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class WriteCurrentData extends Thread {

	// Parameter
	private String filename;
	private String formatfile;
	private String subFolder;
	// Objet request
	private ErrorLog log;
	private PrintWriter writer;

	// Constructors
	public WriteCurrentData() {
		this.setFilename("currentmessage.txt");
		this.setFormatfile("UTF-8");
		this.subFolder = Paths.get("").toAbsolutePath().toString() + "//Data";
		CheckSubFolder();

	}

	public WriteCurrentData(String filename, String formatfile) {
		this.setFilename(filename);
		this.setFormatfile(formatfile);
		this.subFolder = Paths.get("").toAbsolutePath().toString() + "//Data";
		CheckSubFolder();
	}

	// FUNCTIONS

	// This function check if exit the directory and create if don't exist
	public void CheckSubFolder() {

		File theDir = new File(subFolder);
		log = ErrorLog.getInstance();

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (Exception e) {
				log.AddError("Segurity error, can't make folder");
				log.AddError(theDir.toPath().toString());
				log.AddError(Throwables.getStackTraceAsString(e));
				System.err.println("Segurity error, can't make folder");
				System.err.println(theDir.toPath().toAbsolutePath().toString());
				System.err.println(Throwables.getStackTraceAsString(e));
			}
			if (result) {
				log.AddError(("Creating directory: " + theDir.getName()));
				System.out.println("Creating directory: " + theDir.getName());

			}
		}

	}

	// This function create the file if don't exist, write lines and save.
	public void Write(List<String> messages) {

		log = ErrorLog.getInstance();
		// check if directory exits
		CheckSubFolder();
		try {
			writer = new PrintWriter(subFolder + "//" + filename, formatfile);
			for (int i = 0; i < messages.size(); i++) {
				writer.println(messages.get(i));
			}
			writer.close();
		} catch (Exception e) {
			System.err.println("\n Write Current data FAIL");
			System.err.println("\n Path to write: " + subFolder + "//" + filename + " " + formatfile);
			System.err.println(Throwables.getStackTraceAsString(e));
			log.AddError("\n Write Current data FAIL");
			log.AddError(("\n Path to write: " + subFolder + "//" + filename + " " + formatfile));
			log.AddError(Throwables.getStackTraceAsString(e));
			log.WriteLog();
			System.exit(1);

		}
	}

	// MAke file
	public void run(List<String> messages) {
		Write(messages);
	}

	// Gets and Sets
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFormatfile() {
		return formatfile;
	}
	public void setFormatfile(String formatfile) {
		this.formatfile = formatfile;
	}
	public PrintWriter getWriter() {
		return writer;
	}
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
	public String getSubFolder() {
		return subFolder;
	}
	public void setSubFolder(String subFolder) {
		this.subFolder = Paths.get("").toAbsolutePath().toString() + "//Data//" + subFolder;
	}

}
