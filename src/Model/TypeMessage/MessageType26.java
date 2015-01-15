package Model.TypeMessage;

import Model.message.Payload;

public class MessageType26 extends Payload {

	// subclass inside
	public class GridPoint {

		protected float IGP_VerticalDelay = 0;
		protected short GIVEI = 0;
		protected int vtec;
		protected int rms;

		public GridPoint() {

		}

		public GridPoint(int IGPVerticalDelay, int GIVEI) {

			this.IGP_VerticalDelay = (float) (IGPVerticalDelay * 0.125);
			this.GIVEI = (short) GIVEI;
			CalParameters();
		}

		public float getIGP_VerticalDelay() {
			return IGP_VerticalDelay;
		}

		public void setIGP_VerticalDelay(float iGP_VerticalDelay) {
			IGP_VerticalDelay = iGP_VerticalDelay;
		}

		public int getGIVEI() {
			return GIVEI;
		}

		public void setGIVEI(short gIVEI) {
			GIVEI = gIVEI;
		}

		public int getVtec() {
			return vtec;
		}

		public void setVtec(int vtec) {
			this.vtec = vtec;
		}

		public int getRms() {
			return rms;
		}

		public void setRms(int rms) {
			this.rms = rms;
		}

		protected void CalParameters() {
			// calculate VTEC
			// VTEC = IGP * F^2 / (K * C)
			// F (MHZ) K = 1.34E-3 m^2/s
			// F(Hz) K= 40.3 m^2/s

			if (IGP_VerticalDelay < 63.875) {
				this.vtec = (int) Math.round(this.IGP_VerticalDelay * 6.17f);
				if (IGP_VerticalDelay >= 0) {
					// calculate RMS
					this.rms = (int) Math.round(Math.sqrt((double) IGP_VerticalDelay) * 6.17f);
				} else {
					this.rms = -1;
				}
			} else {
				this.vtec = -1;
				this.rms = -1;
			}
		}
	}

	protected static final int BLOCK_GRID_POINTS = 15;

	// Parameters

	protected int bandnumber;
	protected int blockid;
	protected GridPoint[] gridpoints;
	protected int ioid;

	public MessageType26() {
		this.gridpoints = new GridPoint[15];
	}

	public MessageType26(String message) {
		super(message);
	}

	@Override
	public void decode() {

		// preambulo 8 bits
		// type 6
		this.currentbit = 14;
		// DECODE MESSAGE TYPE 26
		// BAND NUMBER 4 BITS
		this.bandnumber = byteToInt(Getbits(4));
		// BLOCK ID
		this.blockid = byteToInt(Getbits(4));

		// 15 block of IGP vertical (9bits) delay & GIVEI (4bits)
		this.gridpoints = new GridPoint[BLOCK_GRID_POINTS];
		for (int i = 0; i < BLOCK_GRID_POINTS; i++) {
			gridpoints[i] = new GridPoint(byteToInt(Getbits(9)), byteToInt(Getbits(4)));
		}

		// IODI (2bits)
		this.ioid = byteToInt(Getbits(2));

		// SPARE (9bits) not used

	}

	@Override
	public String PrintMessage() {

		// Make message decoder
		this.message = "BANDNUMBER: " + this.bandnumber + " BLOCKID: " + this.blockid;
		this.message += " \n ";
		for (int i = 0; i < BLOCK_GRID_POINTS; i++) {
			this.message += " IGP" + (i + 1) + ": " + this.gridpoints[i].getIGP_VerticalDelay() + " GIVEI" + (i + 1) + ": " + this.gridpoints[i].getGIVEI() + "  VTEC" + (i + 1) + ": " + this.gridpoints[i].getVtec() + " RMS" + (i + 1)+ ": " + this.gridpoints[i].getRms() + " |";
			if (i % 5 == 0 && i != 0) {
				this.message += " \n ";
			}
		}

		return this.message;
	}

	public int getBandnumber() {
		return bandnumber;
	}

	public void setBandnumber(int bandnumber) {
		this.bandnumber = bandnumber;
	}

	public int getBlockid() {
		return blockid;
	}

	public void setBlockid(int blockid) {
		this.blockid = blockid;
	}

	public GridPoint[] getGridpoints() {
		return gridpoints;
	}

	public void setGridpoints(GridPoint[] gridpoints) {
		this.gridpoints = gridpoints;
	}

	public int getIoid() {
		return ioid;
	}

	public void setIoid(int ioid) {
		this.ioid = ioid;
	}

}
