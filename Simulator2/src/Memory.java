import java.util.Arrays;

/**
 * Should represent the block memory of the processor
 * 
 * @author knispeja
 *		   Created 10/21/2014
 */
public class Memory extends Object implements Clockable{
	
	private final int blockSize = 1;
	
	private int[] memArray;
	private int minAddr;
	private int maxAddr;
	
	private int readAddr;
	private int writeAddr;
	private int din;
	private String readval;
	
	private boolean memwrite;
	
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
		this.memwrite = false;
	}
	
	public void clockCycle(){
		if(this.readAddr >= this.minAddr && this.readAddr <= this.maxAddr)
			this.readval = Converter.intToBooleanString(this.memArray[this.readAddr/this.blockSize]);
		else
			System.err.println("Memory: Something passed an invalid address into readAddr");
		
		if(this.memwrite){
			if(this.writeAddr >= this.minAddr && this.readAddr <= this.maxAddr)
				this.memArray[this.writeAddr/this.blockSize] = this.din;
			else
				System.err.println("Memory: Something passed an invalid address into writeAddr");
		}
			
	}
	
	public void din(String data16bit){
		this.din = Converter.booleanString2Dec(data16bit);
	}
	
	public void readAddr(String addr16bit){
		this.readAddr = Converter.booleanString2Dec(addr16bit);
	}
	
	public void writeAddr(String addr16bit){
		this.writeAddr = Converter.booleanString2Dec(addr16bit);
	}
	
	public String read(){
		return this.readval;
	}
	
}
