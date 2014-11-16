package compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompilerAlpha {
	ArrayList<String> delimiters = new ArrayList<String>();
	ArrayList<String> rawInput = new ArrayList<String>();
	LinkedList<String> semiCified;
	ArrayList<SyntaxNode> functionList;
	SyntaxNode root;

	public CompilerAlpha() {

	}

	private void loadDefinitions(String source) {
		try {
			File inputFile = new File(source);
			Scanner fileScanner = new Scanner(inputFile);
			String line = fileScanner.nextLine();
			Scanner subScanner = new Scanner(line);
			while (subScanner.hasNext())
				delimiters.add(subScanner.next());
			subScanner.close();
			while (fileScanner.hasNext()) {
				//
			}
			fileScanner.close();

		} catch (Exception e) {
			System.err.println("There was a problem loading definitions");
		}
	}

	private void loadText(String source) {
		try {
			File inputFile = new File(source);
			Scanner fileScanner = new Scanner(inputFile);
			String line = "";
			while (fileScanner.hasNext()) { // while there is a line that has
											// not been scanned yet.
				this.rawInput.add(fileScanner.nextLine());
			}
			fileScanner.close();

		} catch (Exception e) {
			System.err.println("Invalid file path.");
		}

	}

	private void removeCommentsEmptyLinesTrim() {
		int length = this.rawInput.size();
		for (int i = 0; i < length; i++) {
			String line = this.rawInput.get(i);
			int indexSlash = line.indexOf("//");
			if (indexSlash != -1) {
				line = line.substring(0, indexSlash); // only can ignore
														// comments like this
				line = line.trim();
				this.rawInput.set(i, line);
			}
			line = line.trim();
			if (line.length() == 0) {
				this.rawInput.remove(i);
				i--;
				length--;
			} else{
			this.rawInput.set(i, line);
			}
		}
	}

	public LinkedList<String> semicolonify() { // creates a new array with shallow semicolon
									// delimination.
		LinkedList<String> parsedInput = new LinkedList<String>();
		LinkedList<String> rawLL = new LinkedList<String>();
		rawLL.addAll(rawInput);
		String line = "";
		while (true) {
			if (line.length() == 0) {
				if (rawLL.isEmpty())
					break;
				line = rawLL.removeFirst();
			}
			int firstparenIndex = line.indexOf("(");
			int firstBracketIndex = line.indexOf("{");
			int secondBracketIndex = line.indexOf("}");
			int secondparenIndex = line.indexOf(")");
			int semicolonIndex = line.indexOf(";");

			int index = 1000000; // Unrealistically large. If this bites me i'll
									// cry
			String id = ":D";
			if (firstparenIndex != -1) {
				index = firstparenIndex;
				id = "(";
			}
			if ((firstBracketIndex != -1) && (firstBracketIndex < index)) {
				index = firstBracketIndex;
				id = "{";
			}
			if ((secondBracketIndex != -1) && (secondBracketIndex < index)) {
				index = secondBracketIndex;
				id = "}";
			}
			if ((secondparenIndex != -1) && (secondparenIndex < index)) {
				index = secondparenIndex;
				id = ")";
			}
			if ((semicolonIndex != -1) && (semicolonIndex < index)) {
				index = semicolonIndex;
				id = ";";
			}
			if (index != 1000000) {
				if (id.equals("{")) {
					String front = line.substring(0, index);
					if (front.length() != 0) {
						System.err.println("important?: " + front);
					}
					parsedInput.add("{");

				} else if (id.equals("(")) {
					parsedInput.add(line.substring(0, index + 1));

				} else if (id.equals(")")) {
					String last = parsedInput.removeLast();
					last = last + line.substring(0, index + 1);
					parsedInput.add(last);

				} else if (id.equals("}")) {
					parsedInput.add("}");
					if (index != 0) {
						System.err.println("important?: " + line.substring(0, index));
					}

				} else if (id.equals(";")) {
					if(index==0){
						String last=parsedInput.removeLast();
						last=last+";";
						parsedInput.add(last);
					} else
					parsedInput.add(line.substring(0, index + 1));
				}
				if (index + 1 == line.length())
					line = "";
				else
					line = line.substring(index + 1);
			}
			else
			System.out.println("What is this: "+line);
		}
		return parsedInput;
	}

	public void lexer(String definitionsource, String codeSource) {
		System.out.println("lexing? Yay!");
		loadDefinitions(definitionsource);
		loadText(codeSource);
		removeCommentsEmptyLinesTrim();
		this.semiCified=semicolonify();
		functionList=getFunctionNodes(semiCified);
		
		

	}

	public SyntaxNode generateFullTree(LinkedList<String> input) {
		 ArrayList<SyntaxNode> functionList= new ArrayList<SyntaxNode>();
		 
		 SyntaxNode main=nodeGenerator(input);//change
		 
		 return main;
	 }

	public SyntaxNode nodeGenerator(LinkedList<String> input){
		SyntaxNode block=new SyntaxNode("block");
		
		return block;
	}

	private ArrayList<SyntaxNode> getFunctionNodes(LinkedList<String> sCInput){
		ArrayList<SyntaxNode> nodeList =new ArrayList<SyntaxNode>();
		Pattern functionPattern =Pattern.compile("\\w+\\s(\\w+\\(.*\\){1,})");
		Pattern wordpattern =Pattern.compile("\\w+");
		boolean inFunctionDefinition=false;
		int bracketDepth=0;
		LinkedList<String> codeBody = null;
		Matcher nameMatcher = null;
		for(int i=0;i<sCInput.size();i++){
			Matcher functionMatcher = functionPattern.matcher(sCInput.get(i));

			if(inFunctionDefinition){
				if(sCInput.get(i).equals("}"))
					bracketDepth--;
				if(sCInput.get(i).equals("{"))
					bracketDepth++;
				if(bracketDepth==0){
					nameMatcher.find();
					nameMatcher.find();
					inFunctionDefinition=false;
					SyntaxNode function =nodeGenerator(codeBody);
					function.name=nameMatcher.group();
					function.type="function";
					nodeList.add(function);
					
				}else{
					codeBody.add(sCInput.get(i));
				}
					
					
			}
			if(functionMatcher.find()){
				if(i+1<sCInput.size()&&sCInput.get(i+1).equals("{")){
					nameMatcher =wordpattern.matcher(sCInput.get(i));
					inFunctionDefinition=true;
					i++;
					bracketDepth++;;
					codeBody=new LinkedList<String>();
				}
			}
			
		}
		return nodeList;
	}
	 
	private String getNextToken(Scanner scan) {

		return "";
	}

	private class SyntaxNode {
		String type;
		String returnType;
		String name;
		ArrayList<SyntaxNode> arguments = new ArrayList<SyntaxNode>();
		ArrayList<SyntaxNode> nested = new ArrayList<SyntaxNode>();
		private SyntaxNode(String type) {
			this.type = type;
		}

		private SyntaxNode(String type, ArrayList<SyntaxNode> arguments) {
			this.arguments = arguments;
		}
	}
}
