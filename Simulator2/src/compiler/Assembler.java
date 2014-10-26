package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Assembler {
	boolean bytecodesuccess;
	Scanner scan = null;
	HashMap<String, String[]> byteMap = new HashMap<String, String[]>();
	ArrayList<String> assemblyInput;
	ArrayList<Flag> flagList = new ArrayList<Flag>();
	int instructionInterval;
	int baseAddress;
	String[] byteCodeArray;

	/*
	 * takes byteSource, Instruction Interval, and Base Address.
	 */
	public Assembler(String BytecodeS0urce, int instructionInterval,
			int baseAddress) {
		this.instructionInterval = instructionInterval;
		this.loadByteDefinitions(BytecodeS0urce);
		this.baseAddress = baseAddress;
	}

	private void loadByteDefinitions(String source) {

		try {
			File byteFile = new File(source);
			this.scan = new Scanner(byteFile);
			while (this.scan.hasNext()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				ArrayList<String> tokens = new ArrayList<String>();
				while (lineScanner.hasNext())
					tokens.add(lineScanner.next());
				lineScanner.close();
				this.byteMap.put(tokens.remove(0),
						tokens.toArray(new String[2]));
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
			// this.scan.useDelimiter("\\s\n\\s|\\s:\\s");
			String line = "";
			Scanner lineScanner;

			while (this.scan.hasNext()) {
				line = this.scan.nextLine();
				if (line.contains(":")) {
					lineScanner = new Scanner(line);
					rawAssembly.add(lineScanner.next());
					String rest = "";
					while (lineScanner.hasNext())
						rest = rest + lineScanner.next();
					rawAssembly.add(rest);
					lineScanner.close();
				} else if (line.length() != 0) {
					rawAssembly.add(line);
				}
			}

			this.assemblyInput = rawAssembly;
		} catch (Exception e) {
			System.err
					.println("Did not load assembly code correctly.\nThere could be a syntax error");
		}
	}

	public void extractCalculateFlags() {
		String token;
		int size = assemblyInput.size();
		for (int i = 0; i < size; i++) {
			token = assemblyInput.get(i);
			if (token.contains(":")) {
				int position = token.indexOf(":");
				String name = token.substring(0, position);
				Flag f = new Flag(i, name);
				f.address = this.baseAddress + f.line
						* this.instructionInterval;
				flagList.add(f);
				assemblyInput.remove(i);
				i--;
				size--;
			}
		}
	}

	public void run(String source, String destination) {
		this.loadAssembly(source);
		this.extractCalculateFlags();
		this.generateByteCode();
		this.writeToFile(destination);

	}

	public void run(String source) {
		this.loadAssembly(source);
		this.extractCalculateFlags();
		this.generateByteCode();
		this.writeToFile(source.substring(0,source.length()-4)+".BC.txt");

	}

	private void generateByteCode() {

		int arraySize = this.assemblyInput.size();
		this.byteCodeArray = new String[arraySize];
		
		for (int index = 0; index < arraySize; index++) {
			String assembleCode = this.assemblyInput.get(index);
			this.scan = new Scanner(assembleCode);
			ArrayList<String> argList = new ArrayList<String>();

			for (int argNumber = 1; this.scan.hasNext(); argNumber++) { 
				argList.add(this.scan.next());
			}

			this.scan.close();
			String[] argArray=argList.toArray(new String[2]);
			String[] formatArray=this.byteMap.get(argArray[0]);

				this.byteCodeArray[index]=formatArray[0];
				
			for(int subIndex=1;subIndex<argArray.length;subIndex++){
				
				String argI=argArray[subIndex];
				String formatI=formatArray[subIndex];
				
				if(formatI.equals("x"))
					this.byteCodeArray[index]+=AssemblerConverter.intToBooleanString_withLength(0,11);
				
				else if(formatI.equals("i")&&subIndex==1){
					try{
					this.byteCodeArray[index]+=AssemblerConverter.intToBooleanString_withLength(Integer.parseInt(argI),11);
					} catch (Exception e){
						this.byteCodeArray[index]+=AssemblerConverter.intToBooleanString_withLength(0,11);
					}
				}
			
				// Add more types here-----------------------------------------------------------------------------------
			}
			
			
			
		}

	}

	private void writeToFile(String destination){
		PrintWriter writer;
		try {
			writer = new PrintWriter(destination, "UTF-8");
			for(int i=0;i<this.byteCodeArray.length;i++)
				writer.println(this.byteCodeArray[i]);
			writer.close();
		} catch (Exception exception) {
			System.err.println("Issue Writing");
		}
		

	}
	
	private class Flag {
		int line;
		@SuppressWarnings("unused")
		String name;
		@SuppressWarnings("unused")
		int address = -1;

		private Flag(int line, String name) {
			this.line = line;
			this.name = name;
		}

	}
}
