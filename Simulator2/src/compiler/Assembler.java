package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is an Assembler. Use it for Assembling things. Comes with no
 * instructions, (But many helpful methods)
 * 
 * @author larsonp. Created Oct 26, 2014.
 */
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
					if (rest.length() != 0)
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
		this.writeToFile(source.substring(0, source.length() - 4) + ".Bin.txt");
		this.hexWriteToFile(source.substring(0, source.length() - 4) + ".Hex.txt");

	}

	private void generateByteCode() {

		int arraySize = this.assemblyInput.size();
		this.byteCodeArray = new String[arraySize+1];

		for (int index = 0; index < arraySize; index++) {
			String assembleCode = this.assemblyInput.get(index);
			this.scan = new Scanner(assembleCode);
			ArrayList<String> argList = new ArrayList<String>();

			for (int argNumber = 1; this.scan.hasNext(); argNumber++) {
				argList.add(this.scan.next());
			}

			this.scan.close();

			String[] argArray = argList.toArray(new String[2]);
			String[] formatArray = this.byteMap.get(argArray[0]);

			this.byteCodeArray[index] = getByteCodeLine(formatArray, argArray,
					this.baseAddress + index);

		}
		this.byteCodeArray[arraySize]="1111111111111111";

	}

	private String getByteCodeLine(String[] formatArray,
			String[] argumentArray, int instrAddress) {
		try {
			String lineOfByteCode = formatArray[0];

			for (int subIndex = 1; subIndex < argumentArray.length; subIndex++) {
				String argI = argumentArray[subIndex];
				String formatI = formatArray[subIndex];

				if (formatI.equals("x")) {
					try {
						lineOfByteCode += ARConverter
								.intToBooleanString_withLength(0, 11);
					} catch (Exception e) {
						System.err
								.println("Not totall sure how you screwed this one up, but "
										+ argumentArray[0] + " didn't work");
					}
				} else if (formatI.equals("i")
						&& (argumentArray[0].equals("sbne") || argumentArray[0]
								.equals("sbeq")|| argumentArray[0]
										.equals("jal"))) {
					try{
					Flag f = null;
					for (int i = 0; i < this.flagList.size(); i++)
						if (this.flagList.get(i).name.equals(argI))
							f = this.flagList.get(i);
					if (f == null) {
						System.err.println("Could not find flag on: "
								+ argumentArray[0] + " " + argumentArray[1]);
						return "-17-17-17-17-17-17-17-17-17";
					}
					int address = f.address;
					int difference = address - instrAddress - 1; // to
																	// compensate
																	// for that
																	// thing.
																	// Because
																	// automatic
																	// incrementing.
					String binaryaddress = ARConverter
							.intToBooleanString_withLength(difference, 11);
					lineOfByteCode += binaryaddress;
					}catch (Exception e){
						System.err.println("Problem on "+argumentArray[0] + " " + argumentArray[1]);
						return "-17-17-17-17-17-17-17";
					}

				} else if (formatI.equals("i")
						&& (argumentArray[0].equals("j"))) {
					if (argI == null) {
						lineOfByteCode += ARConverter
								.intToBooleanString_withLength(0, 11);
					} else {
						Flag f = null;
						for (int i = 0; i < this.flagList.size(); i++)
							if (this.flagList.get(i).name.equals(argI))
								f = this.flagList.get(i);
						if (f == null) {
							System.err
									.println("Could not find flag on: "
											+ argumentArray[0] + " "
											+ argumentArray[1]);
							return "-17-17-17-17-17-17-17-17-17";
						}
						lineOfByteCode += ARConverter
								.intToBooleanString_withLength(0, 6);
						lineOfByteCode += ARConverter
								.intToBooleanString_withLength(f.address, 16)
								.substring(0, 5);

					}
				} else if (formatI.equals("i") && subIndex == 1) {

					try {
						lineOfByteCode += ARConverter
								.intToBooleanString_withLength(
										Integer.parseInt(argI), 11);
					} catch (Exception e) {
						try {
							if (argI == null) {						
								if (argumentArray[0].equals("pop"))
								lineOfByteCode +=ARConverter
								.intToBooleanString_withLength(1, 11);
								else
								lineOfByteCode += ARConverter
										.intToBooleanString_withLength(0, 11);
							} else {
								Flag f = null;
								for (int i = 0; i < this.flagList.size(); i++)
									if (this.flagList.get(i).name.equals(argI))
										f = this.flagList.get(i);
								if (f == null) {
									System.err
											.println("Could not find flag on: "
													+ argumentArray[0] + " "
													+ argumentArray[1]);
									return "-17-17-17-17-17-17-17-17-17";
								}
								lineOfByteCode += ARConverter
										.intToBooleanString_withLength(
												f.address, 11);
							}
						} catch (Exception j) {
							System.err.println("Parse error on line: "
									+ argumentArray[0]);
							lineOfByteCode += ARConverter
									.intToBooleanString_withLength(0, 11);
						}
					}
				}

				// Add more types
				// here-----------------------------------------------------------------------------------
			}
			return lineOfByteCode;
		} catch (Exception e) {
			System.err
					.println("Error compiling line: "+argumentArray[0] + " " + argumentArray[1]);
			return "There was some crap here";
		}
	}

	private void writeToFile(String destination) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(destination, "UTF-8");
			for (int i = 0; i < this.byteCodeArray.length; i++)
				writer.println(this.byteCodeArray[i]);
			writer.close();
		} catch (Exception exception) {
			System.err.println("Issue Writing");
		}

	}

	private void hexWriteToFile(String destination){
		PrintWriter writer;
		try {
			writer = new PrintWriter(destination, "UTF-8");
			for (int i = 0; i < this.byteCodeArray.length; i++)
				writer.println(ARConverter.booleanStringToHex(this.byteCodeArray[i])+",");
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
