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
		this.writeToFile(source.substring(0, source.length() - 4) + ".BC.txt");

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

			String[] argArray = argList.toArray(new String[2]);
			String[] formatArray = this.byteMap.get(argArray[0]);

			this.byteCodeArray[index] = getByteCodeLine(formatArray, argArray);

		}

	}

	private String getByteCodeLine(String[] formatArray, String[] argumentArray) {
		try {
			String lineOfByteCode = formatArray[0];

			for (int subIndex = 1; subIndex < argumentArray.length; subIndex++) {
				String argI = argumentArray[subIndex];
				String formatI = formatArray[subIndex];

				if (formatI.equals("x"))
					lineOfByteCode += ARConverter
							.intToBooleanString_withLength(0, 11);

				else if (formatI.equals("i")
						&& (argumentArray[0].equals("sbne")
								|| argumentArray[0].equals("sbeq") || argumentArray[0]
									.equals("j"))) {
					Flag f = null;
					for (int i = 0; i < this.flagList.size(); i++)
						if (this.flagList.get(i).name.equals(argI))
							f = this.flagList.get(i);
					if (f == null) {
						System.err
								.println("Error compiling. Check flags used for branches");
						return "-17-17-17-17-17-17-17-17-17";
					}
					int address = f.address;
					int extra = (int) (Math
							.log((double) this.instructionInterval) / Math
							.log(2.0));
					String binaryaddress = ARConverter
							.intToBooleanString_withLength(0, extra);
					binaryaddress += ARConverter
							.intToBooleanString_withLength(address, 11);
					binaryaddress = binaryaddress.substring(0,
							binaryaddress.length() - extra);
					lineOfByteCode += binaryaddress;

				} else if (formatI.equals("i") && subIndex == 1) {

					try {
						lineOfByteCode += ARConverter
								.intToBooleanString_withLength(
										Integer.parseInt(argI), 11);
					} catch (Exception e) {
						lineOfByteCode += ARConverter
								.intToBooleanString_withLength(0, 11);
					}
				}

				// Add more types
				// here-----------------------------------------------------------------------------------
			}
			return lineOfByteCode;
		} catch (Exception e) {
			System.err
					.println("Error compiling. Check flags used for branches");
			return ("this line error'd");
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
