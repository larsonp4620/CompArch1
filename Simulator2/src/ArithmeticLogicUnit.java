
public class ArithmeticLogicUnit implements Clockable, Writable, Readable{

	private String ALUOp;
	private String inputA;
	private String inputB;

	private String ALUOut;
	private String zero;
	
	public ArithmeticLogicUnit(){
		this.ALUOp = "0";
		this.inputA = "0";
		this.inputB = "0";
		this.ALUOut = "0";
		this.zero = "1";
	}

	@Override
	public void clockCycle() {
		switch(this.ALUOp){
			case "000": this.ALUOut = and(this.inputA, this.inputB);
						break;
			case "001": this.ALUOut = or(this.inputA, this.inputB);
						break;
			case "010": this.ALUOut = add(this.inputA, this.inputB);
						break;
			case "110": this.ALUOut = sub(this.inputA, this.inputB);
						break;
			case "111": this.ALUOut = slt(this.inputA, this.inputB);
						break;
			default: this.ALUOut = "0";
					 break;
		}
		
		if(Converter.booleanString2Dec(this.ALUOut) == 0)
			this.zero = "1";
		else
			this.zero = "0";
	}

	public static String and(String A, String B){
		int aLength = A.length();
		int bLength = B.length();
		
		String output = "";
		
		for(int i=0; i<Math.min(aLength, bLength); i++){
			if(A.charAt(aLength - 1 - i) == '1' && B.charAt(bLength - 1 - i) == '1')
				output = "1" + output;
			else
				output = "0" + output;
		}
		
		return output;
	}
	
	public static String or(String A, String B){
		int aLength = A.length();
		int bLength = B.length();
		
		String output = "";
		
		for(int i=0; i<Math.min(aLength, bLength); i++){
			if(A.charAt(aLength - 1 - i) == '1' | B.charAt(bLength - 1 - i) == '1')
				output = "1" + output;
			else
				output = "0" + output;
		}
		
		return output;
	}
	
	public static String add(String A, String B){
		return Converter.intToBooleanString(Converter.booleanString2Dec(A) + Converter.booleanString2Dec(B));
	}
	
	public static String sub(String A, String B){
		return Converter.intToBooleanString(Converter.booleanString2Dec(A) - Converter.booleanString2Dec(B));
	}
	
	public static String slt(String A, String B){
		if(Converter.booleanString2Dec(A) < Converter.booleanString2Dec(B))
			return "1";
		else
			return "0";
	}
	
	/**
	 * Use of id:
	 * 0: ALUout
	 * 1: zero
	 */
	@Override
	public String read(int id) {
		switch(id){
		case 0: return this.ALUOut;
		case 1: return this.zero;
		default: System.err.println("ALU: Invalid read id passed in.");
				 return "0";
		}
	}

	/**
	 * Use of id:
	 * 0: ALUop
	 * 1: inputA
	 * 2: inputB
	 * 
	 * Also, use of ALUop:
	 * 000: and
	 * 001: or
	 * 010: add
	 * 110: sub
	 * 111: slt
	 */
	@Override
	public void write(int id, String data) {
		switch(id){
			case 0: this.ALUOp = data;
					break;
			case 1: this.inputA = data;
					break;
			case 2: this.inputB = data;
					break;
			default: System.err.println("ALU: Invalid write id passed in.");
					break;
		}
	}
}
