package Model.TypeMessage;

import Model.message.Payload;

public class MessageType10 extends Payload {

	protected float B_RRC = 0;
	protected float C_LTC_LSB = 0;
	protected float C_LTC_v1 = 0;
	protected int I_LTC_v1 = 0;
	protected float C_LT_v0 = 0;
	protected int I_LTC_v0 = 0;
	protected float C_GEO_LSV = 0;
	protected float C_GEO_V = 0;
	protected int I_GEO = 0;
	protected float C_ER = 0;
	protected float C_IONO_STEP = 0;
	protected int I_IONO = 0;
	protected float C_IONO_RAMP = 0;
	//revisar criterios de signos
	protected short RSS_UDRE = 0;
	protected short RSS_IONO = 0;

	public MessageType10() {

	}

	public MessageType10(String message) {
		super(message);
	}

	@Override
	public void decode() {

		// preambulo 8 bits
		// type 6
		this.currentbit = 14;

		// DECODE MESSAGE TYPE 10

		// B_RRC 10 bits
		this.B_RRC = (float) (byteToInt(Getbits(10)) * 0.002);
		// C_LTC_LSB 10 bits
		this.C_LTC_LSB = (float) (byteToInt(Getbits(10)) * 0.002);
		// C_LTC_v1 10 bits
		this.C_LTC_v1 = (float) (byteToInt(Getbits(10)) * 0.00005);
		// I_LTC_v1 9 bits
		this.I_LTC_v1 = byteToInt(Getbits(9));
		// C_LT_v0 10 bits
		this.C_LT_v0 = (float) (byteToInt(Getbits(10)) * 0.002);
		// I_LTC_v0 9 bits
		this.I_LTC_v0 = byteToInt(Getbits(9));
		// C_GEO_LSV 10 bits
		this.C_GEO_LSV = (float) (byteToInt(Getbits(10)) * 0.00005);
		// C_GEO_V 10 bits
		this.C_GEO_V = (float) (byteToInt(Getbits(10)) * 0.00005);
		// I_GEO 9 bits
		this.I_GEO = byteToInt(Getbits(9));
		// C_ER 6 bits
		this.C_ER = (float) (byteToInt(Getbits(6)) * 0.5);
		// C_IONO_STEP 10 bits
		this.C_IONO_STEP = (float) (byteToInt(Getbits(10)) * 0.001);
		// I_IONO 9 bits
		this.I_IONO = byteToInt(Getbits(9));
		// C_IONO_RAMP 10 bits
		this.C_IONO_RAMP = (float) (byteToInt(Getbits(10)) * 0.000005);
		// RSS_UDRE 1 bits
		this.RSS_UDRE = (short) (byteToInt(Getbits(10)) );
		// RSS_IONO 1 bits
		this.RSS_IONO = (short) (byteToInt(Getbits(10)) );
		

		// spare

	}

	@Override
	public String PrintMessage() {

		// Make message decoder
		this.message = "B_RRC: " + this.B_RRC + "C_LTC_LSB: " + this.C_LTC_LSB + " C_LTC_v1: ";
		this.message += this.C_LTC_v1 + " \n I_LTC_v1: " + this.I_LTC_v1 + "C_LT_v0: " + this.C_LT_v0;
		this.message += " I_LTC_v0: " + this.I_LTC_v0 + " \n C_GEO_LSV: " + this.C_GEO_LSV + "C_GEO_V: " + this.C_GEO_V + "I_GEO: " + this.I_GEO + " \n C_ER: " + this.C_ER;
		this.message += " C_IONO_STEP: "+ this.C_IONO_STEP + "  I_IONO: " + this.I_IONO + " \n C_IONO_RAMP: " + this.C_IONO_RAMP+" \n RSS_UDRE: " + this.RSS_UDRE+" \n RSS_IONO: " + this.RSS_IONO;
		
		return this.message;
	}

	public float getB_RRC() {
		return B_RRC;
	}

	public void setB_RRC(float b_RRC) {
		B_RRC = b_RRC;
	}

	public float getC_LTC_LSB() {
		return C_LTC_LSB;
	}

	public void setC_LTC_LSB(float c_LTC_LSB) {
		C_LTC_LSB = c_LTC_LSB;
	}

	public float getC_LTC_v1() {
		return C_LTC_v1;
	}

	public void setC_LTC_v1(float c_LTC_v1) {
		C_LTC_v1 = c_LTC_v1;
	}

	public int getI_LTC_v1() {
		return I_LTC_v1;
	}

	public void setI_LTC_v1(int i_LTC_v1) {
		I_LTC_v1 = i_LTC_v1;
	}

	public float getC_LT_v0() {
		return C_LT_v0;
	}

	public void setC_LT_v0(float c_LT_v0) {
		C_LT_v0 = c_LT_v0;
	}

	public int getI_LTC_v0() {
		return I_LTC_v0;
	}

	public void setI_LTC_v0(int i_LTC_v0) {
		I_LTC_v0 = i_LTC_v0;
	}

	public float getC_GEO_LSV() {
		return C_GEO_LSV;
	}

	public void setC_GEO_LSV(float c_GEO_LSV) {
		C_GEO_LSV = c_GEO_LSV;
	}

	public float getC_GEO_V() {
		return C_GEO_V;
	}

	public void setC_GEO_V(float c_GEO_V) {
		C_GEO_V = c_GEO_V;
	}

	public int getI_GEO() {
		return I_GEO;
	}

	public void setI_GEO(int i_GEO) {
		I_GEO = i_GEO;
	}

	public float getC_ER() {
		return C_ER;
	}

	public void setC_ER(float c_ER) {
		C_ER = c_ER;
	}

}
