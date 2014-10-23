public class Register implements Clockable, Readable, Writable{

	private String input;
	private String output;
	private String regwrite;
	
	public Register(){
		this.input = "";
		this.output = "";
		this.regwrite = "1";
	}
	
	@Override
	public void clockCycle() {
		if(Converter.booleanString2Dec(this.regwrite) != 0)
			this.output = this.input;
	}

	/**
	 * id is ignored -- there is only 1 value to read.
	 */
	@Override
	public String read(int id) {
		return this.output;
	}

	/**
	 * Use of id:
	 * 0: input/src value
	 * 1: regwrite value
	 */
	@Override
	public void write(int id, String data) {
		switch(id){
			case 0: this.input = data;
					break;
			case 1: this.regwrite = data;
					break;
			default: System.err.println("Reg: Invalid write id passed in.");
					break;
		}
	}
	
}
