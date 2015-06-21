package Model.TypeMessage;

import Model.message.Payload;

public class MessageType6 extends Payload {

	protected static final int BLOCK_UDREIS = 51;

	protected int iodf2;
	protected int iodf3;
	protected int iodf4;
	protected int iodf5;
	protected int[] udreis;

	public MessageType6() {

	}

	public MessageType6(String message) {
		super(message);
	}

	@Override
	public void decode() {

		// preambulo 8 bits
		// type 6
		this.currentbit = 14;

		// DECODE MESSAGE TYPE 2-5

		// IODF2 2 bits
		this.iodf2 = byteToInt(Getbits(2));
		// IODF3 2 bits
		this.iodf3 = byteToInt(Getbits(2));
		// IODF4 2 bits
		this.iodf4 = byteToInt(Getbits(2));

		// UDREIS 51 block of 4 bits
		this.udreis = new int[BLOCK_UDREIS];
		for (int i = 0; i < BLOCK_UDREIS; i++) {
			udreis[i] = byteToInt(Getbits(4));
		}
		
		// parity

	}

	@Override
	public String PrintMessage() {

		// // Make message decoder
		// this.message = "NUM. of BANDS: " + this.numberofbands +
		// "BANDNUMBER: " + this.bandnumber + " IODI: " + this.iodi +
		// " \n IGPMASK: ";
		// for (int i = 0; i < igpmask.length; i++) {
		// this.message += toBinaryString(igpmask[i]);
		// }

		return this.message;
	}

}
