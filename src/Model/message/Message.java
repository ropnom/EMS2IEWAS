package Model.message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.TypeMessage.MessageType18;
import Model.TypeMessage.MessageType26;

public class Message {

	//parameters
	protected int num = 0;
	protected short prn = 0;
	protected short year = 0;
	protected short month = 0;
	protected short day = 0;
	protected short hour = 0;
	protected short minute = 0;
	protected short seconds = 0;
	protected short messagetype = 0;
	protected String egnosmessage = "";
	protected String linefeedd = "";
	protected Payload payload;
	protected Date time;
	protected String original;



	//constructors
	public Message() {

	}

	

	public Message(String messageline, int num) {
		String[] columns = messageline.split(" ");

		this.original = messageline;
		try {
			this.num = num;
			this.prn = Short.parseShort(columns[0]);
			this.year = Short.parseShort(columns[1]);
			this.month = Short.parseShort(columns[2]);
			this.day = Short.parseShort(columns[3]);
			this.hour = Short.parseShort(columns[4]);
			this.minute = Short.parseShort(columns[5]);
			this.seconds = Short.parseShort(columns[6]);
			this.messagetype = Short.parseShort(columns[7]);
			this.egnosmessage = columns[8].substring(0, columns[8].length() - 3);
			this.linefeedd = columns[8].substring(columns[8].length() - 3);

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, (int) month - 1);
			cal.set(Calendar.DATE, (int) day);
			cal.set(Calendar.YEAR, 2000 + (int) year);
			cal.set(Calendar.HOUR_OF_DAY, (int) hour);
			cal.set(Calendar.MINUTE, (int) minute);
			cal.set(Calendar.SECOND, (int) seconds);
			this.time = cal.getTime();			
			getDecodeMessage();

		} catch (Exception e) {
			System.err.println("\n Egnos Message is wrong format. Parche FAIL");
			System.err.println("\n EgnosMessage:");
			System.err.println("\n messageline");
			System.err.println("\n Error:");
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
			
		}

	}

	//Functions
	public List<String> WriteHumanFile() {
		List<String> humanmessage = new ArrayList<String>();
		humanmessage.add("\n");
		humanmessage.add("|-------------------------------------------------------------------------------------------------|");
		humanmessage.add(" Message: " + num + ", PRN: " + prn + " Time: " + time + " Type: " + GetTypeMessage(messagetype));
		humanmessage.add(" HEX: " + egnosmessage + "   " + linefeedd);
		humanmessage.add(" " + payload.PrintMessage());
		humanmessage.add("|-------------------------------------------------------------------------------------------------|");
		return humanmessage;
	}
	
	private void getDecodeMessage(){
		
		switch (this.messagetype) {
				
		case 18:
			//type = "IONOSPHERIC grid point mask";
			this.payload = new MessageType18(egnosmessage);

			break;
		
		case 26:
			//type = "IONOSPHERIC DELAY CORRECTIONS";
			this.payload = new MessageType26(egnosmessage);

			break;
		
		default:
			this.payload = new Payload(egnosmessage);
			this.messagetype = -1;
			break;
		}
		
	}
		
	public static String GetTypeMessage(int men) {
		String type = "*********";

		switch (men) {
		case 0:
			type = "SBAS TEST";
			break;
		case 1:
			type = "PRN MASK";
			break;
		case 2:
		case 3:
		case 4:
		case 5:
			type = "FAST CORRECTIONS";
			break;
		case 6:
			type = "INTEGRITY INF";
			break;
		case 7:
			type = "FAST CORRECTIONS DEGRADATION FACTOR";
			break;
		case 9:
			type = "GEO NAV MESSAGE";
			break;
		case 10:
			type = "DEGRADATION PARAMETERS";
			break;
		case 12:
			type = "SBAS NETWORK TIME, UTC offset";
			break;
		case 17:
			type = "GEO satellite almanacs";
			break;
		case 18:
			type = "IONOSPHERIC grid point mask";
			break;
		case 24:
			type = "FAST & LONG TEMR ERROR CORRECTIONS";
			break;
		case 25:
			type = "LONG TERM ERROR CORRECTIONS";
			break;
		case 26:
			type = "IONOSPHERIC DELAY CORRECTIONS";
			break;
		case 27:
			type = "SBAS SERVICE MESSAGE";
			break;
		case 28:
			type = "CLOCK EPHEMERIS COVARIANCE MATRIX";
			break;
		case 62:
			type = "INTERNAL TEST MESSAGE";
			break;
		case 63:
			type = "NULL MESSAGE";
			break;
		default:
			type = "RESERVER FOR FUTURE";
			break;
		}

		return type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public short getPrn() {
		return prn;
	}

	public void setPrn(short prn) {
		this.prn = prn;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public short getMonth() {
		return month;
	}

	public void setMonth(short month) {
		this.month = month;
	}

	public short getDay() {
		return day;
	}

	public void setDay(short day) {
		this.day = day;
	}

	public short getHour() {
		return hour;
	}

	public void setHour(short hour) {
		this.hour = hour;
	}

	public short getMinute() {
		return minute;
	}

	public void setMinute(short minute) {
		this.minute = minute;
	}

	public short getSeconds() {
		return seconds;
	}

	public void setSeconds(short seconds) {
		this.seconds = seconds;
	}

	public short getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(short messagetype) {
		this.messagetype = messagetype;
	}

	public String getEgnosmessage() {
		return egnosmessage;
	}

	public void setEgnosmessage(String egnosmessage) {
		this.egnosmessage = egnosmessage;
	}

	public String getLinefeedd() {
		return linefeedd;
	}

	public void setLinefeedd(String linefeedd) {
		this.linefeedd = linefeedd;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}
	
	
	
	
}
