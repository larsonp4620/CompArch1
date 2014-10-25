package compiler;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Assembler {
	boolean bytecodesuccess;
	Scanner scan = null;
	HashMap<String, String[]> byteMap = new HashMap<String, String[]>();

	public Assembler(String BytecodeS0urce) {
		try {
			File byteFile = new File(BytecodeS0urce);
			this.scan = new Scanner(byteFile);
			while (this.scan.hasNext()) {
				String token1 = scan.next();
				String token2 = scan.next();
				String token3 = scan.next();
				String[] token23 = { token2, token3 };
				this.byteMap.put(token1, token23);
			}
			this.bytecodesuccess = true;
		} catch (Exception e) {
			System.err
					.println("Did not import Byte Code definitions correctly.");
			System.err.println("Unable to initialize assembler");
			this.bytecodesuccess = false;
		}
	}
	public String[] getAssemblerSet(){
		String[] toReturn=new String[this.byteMap.size()];
		int i=0;
		for(String s:this.byteMap.keySet()){
			toReturn[i]=s;
			i++;
		}
		return toReturn;	
	}

	public void run(String source, String destination) {

	}

}
