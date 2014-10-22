public class Register implements Clockable{

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
	
	public void setInput(String input){
		this.input = input;
	}
	
	public String getOutput(){
		return this.output;
	}
	
	public void regwrite(String regwrite){
		this.regwrite = regwrite;
	}
	
}
