package Model.TypeMessage;

import Model.message.Payload;

public class MessageType26 extends Payload {

	// subclass inside
	public class GridPoint {

		protected float IGP_VerticalDelay = 0;
		protected short GIVEI = 0;

		public GridPoint() {

		}

		public GridPoint(int IGPVerticalDelay, int GIVEI) {

			this.IGP_VerticalDelay = (float) (IGPVerticalDelay * 0.125);
			//System.out.println(IGPVerticalDelay);
			//System.out.println(this.IGP_VerticalDelay );
			this.GIVEI = (short) GIVEI;
			//System.out.println(GIVEI);
			//System.out.println(this.GIVEI);
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
		this.message = "BANDNUMBER: "+this.bandnumber+" BLOCKID: "+this.blockid;
		for(int i = 0; i<BLOCK_GRID_POINTS; i++){
			this.message += " IGP"+i+": " + this.gridpoints[i].getIGP_VerticalDelay()+ " GIVEI"+i+": "+this.gridpoints[i].getGIVEI()+" |";
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
