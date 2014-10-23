import java.util.Random;

/**
 * r0 is zero register
 * 
 * @author larsonp. Created Oct 21, 2014.
 */
public class RegFile implements Clockable {
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
		int dout1 = -13;
		int dout2 = -13;
		int raddr1 = -13;
		int raddr2 = -13;
		int waddr = -13;
		int din = -13;
		int regWrite = -13;

		this.registerList[0]=0;
		for (int i = 1; i < 8; i++) {
			registerList[i] = this.randElement.nextInt(65535);
		}

	}

	public void setwrite1(String s) {
		this.raddr1 = Converter.booleanString2Dec(s);
	}

	public void setwrite2(String s) {
		this.raddr2 = Converter.booleanString2Dec(s);
	}

	public void setwriteW(String s) {
		this.waddr = Converter.booleanString2Dec(s);
	}

	public void setdatain(String s) {
		this.din = Converter.booleanString2Dec(s);
	}

	public void setRegWrite(String s) {
		this.regWrite = Converter.booleanString2Dec(s);
	}

	public String getData1() {
		return Converter.intToBooleanString(this.dout1);
	}

	public String getData2() {
		return Converter.intToBooleanString(this.dout2);
	}

	@Override
	public void clockCycle() {
		if (raddr1 == -13 || raddr2 == -13 || waddr == -13 || din == -13
				|| regWrite == -13)
			System.err
					.println("Error: In the register file. "
							+ "an imput to regFile was unspeficied. \nCycle not completed");
		
		this.dout1=this.registerList[this.raddr1];
		this.dout2=this.registerList[this.raddr2];
		
		if(this.regWrite==1&&this.waddr!=0)
			this.registerList[this.waddr]=this.din;
	}
}
