package compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Assembler {
	boolean bytecodesuccess;
	Scanner scan = null;
	HashMap<String, String[]> byteMap = new HashMap<String, String[]>();
	String[] assemblyInput;
	String[] flags;
	int instructionInterval;

	public Assembler(String BytecodeS0urce, int instructionInterval) {
		this.instructionInterval=instructionInterval;
		this.loadByteDefinitions(BytecodeS0urce);
	}

	private void loadByteDefinitions(String source) {

		try {
			File byteFile = new File(source);
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

	public String[] getAssemblerSet() {
		String[] toReturn = new String[this.byteMap.size()];
		int i = 0;
		for (String s : this.byteMap.keySet()) {
			toReturn[i] = s;
			i++;
		}
		return toReturn;
	}

	private void loadAssembly(String source) {
		ArrayList<String> rawAssembly = new ArrayList<String>();
		try {
			File assemblyFile = new File(source);
			this.scan = new Scanner(assemblyFile);
			//this.scan.useDelimiter("\\s\n\\s|\\s:\\s");
			String line="";
			Scanner lineScanner;
			
			while (this.scan.hasNext()){
				line=this.scan.nextLine();
				if(line.contains(":")){
					lineScanner=new Scanner(line);
					rawAssembly.add(lineScanner.next());
					if(lineScanner.hasNext())
					rawAssembly.add(lineScanner.next());
					if(lineScanner.hasNext())
					rawAssembly.add(lineScanner.next());
					lineScanner.close();
				} else if(line.length()!=0){
					rawAssembly.add(line);
				}
			}
					
			this.assemblyInput=rawAssembly.toArray(new String[2]);
		} catch (Exception e) {
			System.err
					.println("Did not load assembly code correctly.\nThere could be a syntax error");
		}
	}

	private void extractFlags(){
		String temp;
		for(int i=0;i<assemblyInput.length;i++){
			temp=assemblyInput[i];
			if(temp.contains(":")){
				int position=temp.indexOf(":");
			}
		}
	}
	public void run(String source, String destination) {
		this.loadAssembly(source);
	}

}
