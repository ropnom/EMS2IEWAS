package Model;

import java.util.ArrayList;
import java.util.List;

public class ErrorLog {

	// Singlenton Log
	private static ErrorLog instance = null;

	// Singleton get Instance or get objet
	public static ErrorLog getInstance() {
		synchronized (ErrorLog.class) {
			if (instance == null)
				instance = new ErrorLog();
			return instance;
		}
	}

	public static void ReInstanceDay() {
		instance.FileErroClear();
	}

	// PArameters
	String file = "log_error.txt";
	WriteCurrentData writer;
	List<String> errors;
	List<String> fileerror;

	// Constructor
	public ErrorLog() {
		this.file = "log_error.txt";
		this.errors = new ArrayList<String>();
		this.fileerror = new ArrayList<String>();
	}

	public ErrorLog(String file) {
		this.file = file;
		this.errors = new ArrayList<String>();
	}

	// Functions
	public void AddFileError(String error) {

		this.fileerror.add(error);
	}

	public void AddError(String error) {

		this.errors.add(error);
	}

	public void WriteLog() {
		writer = new WriteCurrentData();
		writer.setFilename(file);
		writer.Write(errors);
	}

	public void WriteFileError(String day) {
		writer = new WriteCurrentData();
		writer.setSubFolder("error");
		writer.CheckSubFolder();
		writer.setFilename("missingdata_" + day + ".txt");
		if (fileerror.size() > 1)
			writer.Write(fileerror);
	}
	
	public void FileErroClear(){
		this.fileerror = new ArrayList<String>();
	}

	// Gets and Sets
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public WriteCurrentData getWriter() {
		return writer;
	}

	public void setWriter(WriteCurrentData writer) {
		this.writer = writer;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
