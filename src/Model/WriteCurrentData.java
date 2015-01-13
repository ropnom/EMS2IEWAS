package Model;

import java.io.PrintWriter;
import java.util.List;

public class WriteCurrentData extends Thread{

	private PrintWriter writer;
	private String filename;
	private String formatfile;

	public WriteCurrentData() {
		this.setFilename("currentmessage.txt");
		this.setFormatfile("UTF-8");
	}

	public WriteCurrentData(String filename, String formatfile) {
		this.setFilename(filename);
		this.setFormatfile(formatfile);
	}

	public void Write(List<String> messages) {
		try {
			writer = new PrintWriter(filename, formatfile);
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
	
	public void run(List<String> messages){
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

}
