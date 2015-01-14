package Model.message;

public class Payload {

	protected String message;
	protected byte[] binarymessage;
	protected int currentbit = 1;

	public Payload() {

	}

	public Payload(String message) {
		this.binarymessage = Payload.toByteArray(message);
		decode();
	}

	public void decode() {

		this.message = "";
		for (int i = 0; i < binarymessage.length; i++) {
			message = message + toBinaryString(binarymessage[i]);
		}
	}

	public String PrintMessage() {

		if (message == null)
			decode();
		message = "BINARY: " + message;
		return message;

	}

	public static byte[] toByteArray(String s) {
		byte data[] = new byte[(int) Math.ceil(s.length() / 2.0)];
		int i;

		for (i = 0; i < (s.length() - 1); i += 2) {
			data[i / 2] = (Integer.decode("0x" + s.charAt(i) + s.charAt(i + 1))).byteValue();
		}
		if (s.length() % 2 == 1)
			data[data.length - 1] = (Integer.decode("0x" + s.charAt(i) + 0)).byteValue();

		return data;
	}

	public static String toBinaryString(byte n) {

		return String.format("%8s", Integer.toBinaryString((n + 256) % 256)).replace(' ', '0');
	}

	public byte[] Getbits(int numbits) {

		// calculate initbit and number of bytes that we need
		int initbits = currentbit % 8;
		int numbytes = 1 + (numbits) / 8;

		int rambytes = (int) Math.ceil((float) (initbits + numbits) / 8.0);

		byte required[] = new byte[numbytes];
		byte ram[] = new byte[rambytes];

		int movidos = ((8 - (numbits % 8)) % 8);

		for (int i = 0; i < rambytes; i++) {
			// get byte from message where are the bits
			ram[i] = binarymessage[currentbit / 8 + i];
			// fist iteration
			// if (i == 0) {
			// move bit to left to make all compact.
			// if (numbits <= 8 & initbits > 0 & numbytes == 1) {
			// required[i] = (byte) (ram[i] << initbits);
			// }
			// }
		}

		// to compact byte response
		if (initbits == 0 & numbytes == 1) {
			//System.out.println(toBinaryString(ram[0]));
			required[0] = (byte) ((ram[0] & 0xff) >>> (8 - numbits));
			//System.out.println(toBinaryString(required[0]));
		} else if (initbits == 0 & numbytes > 1) {
			// igual solo que hay que mover en todos el desfase hacia la derecha
			for (int i = ram.length - 1; i >= 0; i--) {
				// si es el ultimo solo tenemos en cuenta ese byte
				if (i == 0) {
					required[i] = (byte) ((ram[i] & 0xFF) >>> movidos);
				} else {
					required[i] = (byte) ((byte) ((ram[i] & 0xFF) >>> movidos) + (byte) (ram[i - 1] << (8 - movidos)));
				}
			}

		} else if (initbits > 0 && numbytes == 1) {
			if (rambytes == 1) {
				// primero ajustamos depsues hacemos lo mismo que antes
				 //System.out.println(toBinaryString(ram[0]));
				ram[0] = (byte) (ram[0] << initbits);
				// System.out.println(toBinaryString(ram[0]));
				required[0] = (byte) ((ram[0] & 0xFF) >>> movidos);
				// System.out.println(toBinaryString(required[0]));
			} else {
				int bytederecha = 16 - initbits - numbits;
				ram[0] = (byte) (ram[0] << initbits);
				required[0] = (byte) ((byte) ((ram[1] & 0xFF) >>> bytederecha) + (((byte) ram[0] & 0xFF) >>> 8 - numbits));

			}
		} else if (initbits > 0 && numbytes > 1) {

			int mover2 = numbytes * 8 - initbits - numbits;
			for (int i = ram.length - 1; i >= 0; i--) {
				// si es el ultimo solo tenemos en cuenta ese byte
				if (i == 0) {
					ram[i] = (byte) (ram[i] << initbits);
					required[i] = (byte) ((ram[i] & 0xFF) >>> (initbits + mover2));
				} else {

					required[i] = (byte) ((byte) ((ram[i] & 0xFF) >>> mover2) + (byte) (ram[i - 1] << (8 - mover2)));
				}
			}

		}

		//System.out.println();
		//System.out.println("Mensaje decodificado: ");
		currentbit += numbits;
		//for (int i = 0; i < required.length; i++) {
		//	System.out.println(toBinaryString(required[i]));
		//}

		return required;
	}

	public static final int byteToInt(byte[] b) {

		int l = 0;
		for (int i = 0; i < b.length; i++) {
			l |= b[i] & 0xFF;
			if (i < b.length - 1)
				l <<= 8;
		}
		return l;
	}
	
	public static final boolean byteToBoolean(byte[] b) {

		if (b[0]>0){
			return true;
		}
		else
		{
			return false;
		}
	}

	// ******** GETS y SETS ****

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getBinarymessage() {
		return binarymessage;
	}

	public void setBinarymessage(byte[] binarymessage) {
		this.binarymessage = binarymessage;
	}

}
