package Model.TypeMessage;

import java.util.ArrayList;
import java.util.List;

import Model.message.Payload;

public class MessageType18 extends Payload {

	// parameters
	private int numberofbands = -1;
	private int bandnumber = -1;
	private int iodi = -1;
	private byte[] igpmask;
	private List<Integer> orden;

	public MessageType18() {

		this.orden = new ArrayList<Integer>();
	}

	public MessageType18(String message) {

		this.binarymessage = Payload.toByteArray(message);
		decode();
	}

	@Override
	public void decode() {

		// preambulo 8 bits
		// type 6
		this.currentbit = 14;
		// DECODE MESSAGE TYPE 18
		// Number of Bands (4 bits)
		this.numberofbands = byteToInt(Getbits(4));
		// Band Number (4 bits)
		this.bandnumber = byteToInt(Getbits(4));
		// IODI (2 bits)
		this.iodi = byteToInt(Getbits(2));
		// 201 bits MASK FIELD
		this.igpmask = Getbits(201);
		this.orden = new ArrayList<Integer>();
		getordenmask();

	}

	private void getordenmask() {

		byte info;
		byte ref = 0x01;

		for (int i = 0; i < 201; i++) {
			// obtener byte que lo contien
			info = igpmask[i / 8];
			info = (byte) (info << i % 8);
			info = (byte) ((info & 0xFF) >>> 7);
			if (info == ref) {
				orden.add(i + 1);
			}

		}
	}

	@Override
	public String PrintMessage() {

		// Make message decoder
		this.message = " NUM. of BANDS: " + this.numberofbands + " BANDNUMBER: " + this.bandnumber + " IODI: " + this.iodi + " \n IGPMASK: ";
		for (int i = 0; i < igpmask.length; i++) {
			this.message += toBinaryString(igpmask[i]);
		}
		this.message += " \n ORDEN: ";
		for (int i = 0; i < orden.size(); i++) {
			this.message += " " + this.orden.get(i) + ",";
		}

		return this.message;
	}

	public int getNumberofbands() {
		return numberofbands;
	}

	public void setNumberofbands(int numberofbands) {
		this.numberofbands = numberofbands;
	}

	public int getBandnumber() {
		return bandnumber;
	}

	public void setBandnumber(int bandnumber) {
		this.bandnumber = bandnumber;
	}

	public int getIodi() {
		return iodi;
	}

	public void setIodi(int iodi) {
		this.iodi = iodi;
	}

	public byte[] getIgpmask() {
		return igpmask;
	}

	public void setIgpmask(byte[] igpmask) {
		this.igpmask = igpmask;
	}

	public List<Integer> getOrden() {
		return orden;
	}

	public void setOrden(List<Integer> orden) {
		this.orden = orden;
	}

}