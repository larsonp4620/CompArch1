
public class Wire implements Clockable{

	private Readable input;
	private int inputid;
	
	private Writable outputs[];
	private int outputids[];
	
	private String data;
	
	public Wire(Readable input, int inputid, Writable outputs[], int outputids[]){
		this.input = input;
		this.inputid = inputid;
		this.outputs = outputs;
		this.outputids = outputids;
		this.data = "";
	}
	
	@Override
	public void clockCycle() {
		this.data = this.input.read(this.inputid);
		for(int i=0; i<this.outputs.length; i++){
			this.outputs[i].write(this.outputids[i], this.data);
		}
	}

	/**
	 * Should only be used for testing purposes.
	 * Returns the data currently stored on the wire.
	 * @return
	 */
	public String getData(){
		return this.data;
	}
}
