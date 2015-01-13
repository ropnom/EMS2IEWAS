package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LoadDataFile {

	private BufferedReader br;
	private String line;
	private List<String> messages;
	private String file;

	public LoadDataFile() {

		this.messages = new ArrayList<String>();
		this.file = "currentmessage.txt";
	}

	public LoadDataFile(String file) {

		this.messages = new ArrayList<String>();
		this.file = file;
	}

	public List<String> LoadData() {
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();

			while (line != null) {
				line = br.readLine();
				if (line != null)
					messages.add(line);
			}

		} catch (Exception e) {
			System.err.println("\n Load file data FAIL");
			System.err.println(e.getMessage());
			System.exit(1);

		}

		return messages;
	}

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
		this.file = file;
	}
}
