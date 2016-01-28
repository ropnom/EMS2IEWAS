package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadDataFile {

	// Parameters
	private String line;
	private List<String> messages;
	private String file;
	// request objet
	private BufferedReader br;

	// Constructors
	public LoadDataFile() {

		this.messages = new ArrayList<String>();
		this.file = Paths.get("").toAbsolutePath().toString() + "//Data//currentmessage.txt";
	}

	public LoadDataFile(String file) {

		this.messages = new ArrayList<String>();
		this.file = file;
	}

	// Load data from file
	public List<String> LoadData() {
		// create Error log.
		ErrorLog log = ErrorLog.getInstance();
		try {

			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();

			while (line != null) {
				messages.add(line);
				line = br.readLine();
			}

		} catch (Exception e) {
			System.err.println("\n Load file data FAIL");
			System.err.println("\n Path to Load: " + file);
			System.err.println(Throwables.getStackTraceAsString(e));
			log.AddError("\n Load file data FAIL");
			log.AddError("\n Path to Load: " + file);
			log.AddError(Throwables.getStackTraceAsString(e));
			log.WriteLog();
			System.exit(1);

		}

		return messages;
	}

	// Gets and Sets
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = Paths.get("").toAbsolutePath().toString() + "//Data//" + file;
	}
}
