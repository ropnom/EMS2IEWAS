package Model.TypeMessage;


public class MessageType5 extends MessageType2 {

	public MessageType5() {

	}

	public MessageType5(String message) {

		super(message);
	}

	@Override
	public void decode() {
		// preambulo 8 bits
		// type 6
		this.currentbit = 14;

		// DECODE MESSAGE TYPE 2-5

		// IODF 2 bits
		this.iodf = byteToInt(Getbits(2));

		// PRC 13 block of 12 bits
		this.prc = new int[BLOCK_PRC];
		for (int i = 0; i < BLOCK_PRC; i++) {
			prc[i] = byteToInt(Getbits(12));
		}

		// UDREI 13 block of 12 bits
		this.udrei = new int[BLOCK_PRC];
		for (int i = 0; i < BLOCK_PRC; i++) {
			udrei[i] = byteToInt(Getbits(12));
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
