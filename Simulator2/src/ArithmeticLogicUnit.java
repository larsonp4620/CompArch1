
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
		 /* Also, use of ALUop:
			 * 000: and
			 * 001: or
			 * 010: add
			 * 110: sub
			 * 111: slt
			 */
		
		switch(this.ALUOp){
		// WE NEED AND, OR, ETC... case "000": this.ALUOut
		}
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
