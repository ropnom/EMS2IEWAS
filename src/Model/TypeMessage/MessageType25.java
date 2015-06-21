package Model.TypeMessage;

import Model.message.Payload;

public class MessageType25 extends Payload {

	// subclass inside
	public class ErrorCorrections {

		protected int prnmasknumber;
		protected int issueofdata;
		protected int dx;
		protected int dy;
		protected int dz;
		protected int daf0;

		protected int dxrc;
		protected int dyrc;
		protected int dzrc;
		protected int daf1;
		protected int t0;

		public ErrorCorrections(int prnmasknumber, int issueofdata, int dx, int dy, int dz, int daf0) {

			this.prnmasknumber = prnmasknumber;
			this.issueofdata = issueofdata;
			this.dx = dx;
			this.dy = dy;
			this.dz = dz;
			this.daf0 = daf0;
		}

		public ErrorCorrections(int dxrc, int dyrc, int dzrc, int daf1, int t0) {

			this.dxrc = dxrc;
			this.dyrc = dyrc;
			this.dzrc = dzrc;
			this.daf1 = daf1;
			this.t0 = t0;

		}

		public ErrorCorrections() {

		}
		
		

		public int getDxrc() {
			return dxrc;
		}

		public void setDxrc(int dxrc) {
			this.dxrc = dxrc;
		}

		public int getDyrc() {
			return dyrc;
		}

		public void setDyrc(int dyrc) {
			this.dyrc = dyrc;
		}

		public int getDzrc() {
			return dzrc;
		}

		public void setDzrc(int dzrc) {
			this.dzrc = dzrc;
		}

		public int getDaf1() {
			return daf1;
		}

		public void setDaf1(int daf1) {
			this.daf1 = daf1;
		}

		public int getT0() {
			return t0;
		}

		public void setT0(int t0) {
			this.t0 = t0;
		}

		public int getPrnmasknumber() {
			return prnmasknumber;
		}

		public void setPrnmasknumber(int prnmasknumber) {
			this.prnmasknumber = prnmasknumber;
		}

		public int getIssueofdata() {
			return issueofdata;
		}

		public void setIssueofdata(int issueofdata) {
			this.issueofdata = issueofdata;
		}

		public int getDx() {
			return dx;
		}

		public void setDx(int dx) {
			this.dx = dx;
		}

		public int getDy() {
			return dy;
		}

		public void setDy(int dy) {
			this.dy = dy;
		}

		public int getDz() {
			return dz;
		}

		public void setDz(int dz) {
			this.dz = dz;
		}

		public int getDaf0() {
			return daf0;
		}

		public void setDaf0(int daf0) {
			this.daf0 = daf0;
		}

	}

	// Parameters
	protected boolean velocicode;
	protected ErrorCorrections[] errorcorrections;
	protected int iodp;
	protected byte[] secondhalfofmessage;

	public MessageType25() {

	}

	public MessageType25(String message) {
		super(message);
	}

	@Override
	public void decode() {

		// preambulo 8 bits
		// type 6
		this.currentbit = 14;
		// DECODE MESSAGE TYPE 25

		// Veloci Code 1 bit
		// ESTA FUNCION ES BETA PROBAR
		this.velocicode = byteToBoolean(Getbits(1));

		errorcorrections = new ErrorCorrections[2];
		// Error Corrections message
		for (int i = 0; i < 2; i++) {
			
			if(velocicode){
				// PRN MASK NUMBER 6 bits, ISSUEOFDATA 8 bits, DX 9 bits, DY 9 bits,  DZ 9 bits, DAF0 10 bits
				errorcorrections[i] = new ErrorCorrections(byteToInt(Getbits(6)), byteToInt(Getbits(8)), byteToInt(Getbits(9)), byteToInt(Getbits(9)), byteToInt(Getbits(9)), byteToInt(Getbits(10)));		
			}
			else{
				if(i==0){
					// PRN MASK NUMBER 6 bits, ISSUEOFDATA 8 bits, DX 11 bits, DY 11 bits,  DZ 11 bits, DAF0 11 bits
					errorcorrections[i] = new ErrorCorrections(byteToInt(Getbits(6)), byteToInt(Getbits(8)), byteToInt(Getbits(11)), byteToInt(Getbits(11)), byteToInt(Getbits(11)), byteToInt(Getbits(11)));	
				}
				else
				{
					// DXRC 11 bits, DYRC 11 bits,  DZRC 11 bits, DAF1 11 bits, T0 13bits
					errorcorrections[i] = new ErrorCorrections (byteToInt(Getbits(11)), byteToInt(Getbits(11)), byteToInt(Getbits(11)), byteToInt(Getbits(11)), byteToInt(Getbits(13)));		
				}
			}
				
		}
		
		// IODP 2 bits
		this.iodp = byteToInt(Getbits(2));
		
		//SPARE
		this.currentbit += 1;
		
		this.secondhalfofmessage = Getbits((251-currentbit));

	}

	@Override
	public String PrintMessage() {

		return this.message;
	}

}
