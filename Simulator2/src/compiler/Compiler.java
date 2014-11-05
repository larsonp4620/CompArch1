package compiler;

import java.io.File;
import java.util.Scanner;

public class Compiler {

	public Compiler(){
		
	}
	
	public void loadText(String source){
		try{
			File inputFile = new File(source);
			Scanner fileScanner = new Scanner(inputFile);
			String line="";
			while(fileScanner.hasNext()){
				
			}
			fileScanner.close();
			
		} catch (Exception e){
			System.err.println("There was a problem loading code");
		}
		
	}
	
	private String getNextToken(Scanner scan){

		return "";
	}
	
}
