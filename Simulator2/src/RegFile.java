import java.util.Random;

/**
 * Write id: 0-raddr1 1-raddr2 2-waddr 3-din 4-regWrite
 * 
 * Read Id: 0-dout1 1-dout2
 * 
 * @author larsonp. Created Oct 21, 2014.
 */
public class RegFile implements Clockable, Readable, Writable {
	private int[] registerList;
	Random randElement;
	private int dout1;
	private int dout2;

	private int raddr1;
	private int raddr2;
	private int waddr;
	private int din;
	private int regWrite;

	public RegFile() {
		this.randElement = new Random();

		registerList = new int[8];
		this.dout1 = -13;
		this.dout2 = -13;
		this.raddr1 = -13;
		this.raddr2 = -13;
		this.waddr = -13;
		this.din = -13;
		this.regWrite = -13;

		this.registerList[0] = 0;
		for (int i = 1; i < 8; i++) {
			registerList[i] = this.randElement.nextInt(65535);
		}

	}

//	public void setwrite1(String s) {
//		this.raddr1 = Converter.booleanString2Dec(s);
//	}
//
//	public void setwrite2(String s) {
//		this.raddr2 = Converter.booleanString2Dec(s);
//	}
//
//	public void setwriteW(String s) {
//		this.waddr = Converter.booleanString2Dec(s);
//	}
//
//	public void setdatain(String s) {
//		this.din = Converter.booleanString2Dec(s);
//	}
//
//	public void setRegWrite(String s) {
//		this.regWrite = Converter.booleanString2Dec(s);
//	}
//
//	public String getData1() {
//		return Converter.intToBooleanString(this.dout1);
//	}
//
//	public String getData2() {
//		return Converter.intToBooleanString(this.dout2);
//	}

	@Override
	public void clockCycle() {
		if (raddr1 == -13 || raddr2 == -13 || waddr == -13 || din == -13
				|| regWrite == -13)
			System.err
					.println("Error: In the register file. "
							+ "an imput to regFile was unspeficied. \nCycle not completed");

		this.dout1 = this.registerList[this.raddr1];
		this.dout2 = this.registerList[this.raddr2];

		if (this.regWrite == 1 && this.waddr != 0)
			this.registerList[this.waddr] = this.din;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Readable#read(int)
	 */
	@Override
	public String read(int id) {
		switch (id) {
		case (0):
			return Converter.intToBooleanString(this.dout1);
		case (1):
			return Converter.intToBooleanString(this.dout2);
		default:
			System.err.println("Invalide id value to the Register File");
			return "";
		}

	}

	@Override
	public void write(int id, String data) {
		switch(id){
		case(0):
			this.raddr1=Converter.booleanString2Dec(data);
			return;
		case(1):
			this.raddr2=Converter.booleanString2Dec(data);
			return;
		case(2):
			this.waddr=Converter.booleanString2Dec(data);
			return;
		case(3):
			this.din=Converter.booleanString2Dec(data);
			return;
		case(4):
			this.regWrite=Converter.booleanString2Dec(data);
			return;
		
		}

	}
}
