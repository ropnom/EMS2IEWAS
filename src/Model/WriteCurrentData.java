package Model;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteCurrentData extends Thread {

	private PrintWriter writer;
	private String filename;
	private String formatfile;
	private String subFolder;

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

	public void CheckSubFolder() {

		File theDir = new File(subFolder);
		ErrorLog log = ErrorLog.getInstance();
		
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				log.AddError(se.getStackTrace().toString());
			}
			if (result) {
				log.AddError(("Creating directory: " + theDir.getName()));
				
			}
		}

	}

	public void Write(List<String> messages) {

		CheckSubFolder();
		try {
			writer = new PrintWriter(subFolder + "//" + filename, formatfile);
			for (int i = 0; i < messages.size(); i++) {
				writer.println(messages.get(i));
			}
			writer.close();
		} catch (Exception e) {
			System.err.println("\n Write Current data FAIL");
			System.err.println(e.getMessage());
			System.exit(1);

		}
	}

	public void run(List<String> messages) {
		Write(messages);
	}

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
		this.subFolder = Paths.get("").toAbsolutePath().toString() + "//Data//"+subFolder;
	}

}
