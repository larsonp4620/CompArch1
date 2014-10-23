
public class Mux implements Clockable {

	private int control;
	private String[] data;
	
	private Readable[] inputs;
	private int[] inputids;
	
	private Writable[] outputs;
	private int[] outputids;
	
	private int maxControlSize;
	
	public Mux(Readable[] inputs, int[] inputids, Writable[] outputs, int[] outputids){
		
		if(inputs.length != inputids.length | outputs.length != outputids.length)
			System.err.println("Mux: A mux was initialized with unmatching input/inputids or output/outputids. Continuing normally for now...");
		
		this.control = 0;
		this.inputs = inputs;
		this.inputids = inputids;
		this.outputs = outputs;
		this.outputids = outputids;
		
		this.maxControlSize = Converter.intToBooleanString(this.inputs.length).length();
	}
	
	public void setControl(String control){
		if(control.length() > this.maxControlSize)
			System.err.println("Mux: The control input '" + control + "' into setControl() was too large to make sense.");
		else
			this.control = Converter.booleanString2Dec(control);
	}
	
	@Override
	public void clockCycle() {
		int i;
		for(i=0; i<this.inputs.length; i++){
			this.data[i] = this.inputs[i].read(this.inputids[i]);
		}
		
		for(int j=0; j<this.outputs.length; j++){
			this.outputs[j].write(this.outputids[j], this.data[this.control]);
		}
	}
	
	public String getData(String control){
		return this.data[Converter.booleanString2Dec(control)];
	}

}
