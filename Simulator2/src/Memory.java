import java.util.Arrays;

/**
 * Represents the block memory of the processor
 * 
 * @author knispeja
 *		   Created 10/21/2014
 */
public class Memory implements Clockable, Readable, Writable{
	
	private final int blockSize = 1;
	
	private int[] memArray;
	private int minAddr;
	private int maxAddr;
	
	private int readAddr;
	private int writeAddr;
	private int din;
	private String readval;
	private String memwrite;
	
	/**
	 * Constructor initializes memory at default values
	 */
	public Memory(){
		this(0, 32);
	}
	
	/**
	 * Constructor takes the minimum and maximum addresses (in decimal) to be used for memory
	 * @param min
	 * @param max
	 */
	public Memory(int min, int max){
		this.memArray = new int[max - min];
		Arrays.fill(this.memArray, -17);
		this.minAddr = min;
		this.maxAddr = max;
		this.memwrite = "0";
	}
	
	public void clockCycle(){
		if(Converter.booleanString2Dec(this.memwrite) != 0){
			if(this.writeAddr >= this.minAddr && this.readAddr <= this.maxAddr)
				this.memArray[this.writeAddr/this.blockSize] = this.din;
			else
				System.err.println("Memory: Something passed an invalid address into writeAddr");
		}
		
		// Currently only outputs 16 bits (1 address at a time), this should be fixed promptly u idiotz
		if(this.readAddr >= this.minAddr && this.readAddr <= this.maxAddr)
			this.readval = Converter.intToBooleanString(this.memArray[this.readAddr/this.blockSize]);
		else
			System.err.println("Memory: Something passed an invalid address into readAddr");
	}

	/**
	 * Use of id:
	 * 0: First 16 bits of output
	 * 1: Second 16 bits of output
	 */
	@Override
	public String read(int id) {
		switch(id){
		case 0: return this.readval.substring(0, 15);
		case 1: return this.readval.substring(16, 31);
		default: System.err.println("Memory: Invalid read id passed in.");
				 return "0";
		}
	}

	/**
	 * Use of id:
	 * 0: raddr
	 * 1: wraddr
	 * 2: din
	 * 3: memwrite
	 */
	@Override
	public void write(int id, String data) {
		int idata = Converter.booleanString2Dec(data);
		switch(id){
			case 0: this.readAddr = idata;
					break;
			case 1: this.writeAddr = idata;
					break;
			case 2: this.din = idata;
					break;
			case 3: this.memwrite = data;
					break;
			default: System.err.println("Memory: Invalid write id passed in.");
					break;
		}
	}
	
}
