package Model.TypeMessage;

import Model.message.Payload;

public class MessageType7 extends Payload {

	protected static final int BLOCK_UDRE = 51;

	protected int tlat;
	protected int iodp;
	protected int spare;
	protected int[] udre;

	public MessageType7() {

	}

	public MessageType7(String message) {
		super(message);
	}

	@Override
	public void decode() {

		// preambulo 8 bits
		// type 6
		this.currentbit = 14;

		// DECODE MESSAGE TYPE 2-5

		// tlat 4 bits
		this.tlat = byteToInt(Getbits(4));
		// IODFD 2 bits
		this.iodp = byteToInt(Getbits(2));
		//SPARE 2 bits
		this.spare = byteToInt(Getbits(2));

		// UDRE 51 block of 4 bits degradation factor
		this.udre = new int[BLOCK_UDRE];
		for (int i = 0; i < BLOCK_UDRE; i++) {
			udre[i] = byteToInt(Getbits(4));
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
