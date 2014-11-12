package compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Compiler {
	ArrayList<String> delimiters=new ArrayList<String>();
	ArrayList<String> RawInput=new ArrayList<String>();
	public Compiler(){
		
	}
	
	public void loadDefinitions(String source){
		try{
			File inputFile = new File(source);
			Scanner fileScanner = new Scanner(inputFile);
			String line=fileScanner.nextLine();
			Scanner subScanner= new Scanner(line);
			while(subScanner.hasNext())
			delimiters.add(subScanner.next());
			subScanner.close();
			while(fileScanner.hasNext()){
				//
			}
			fileScanner.close();
			
		} catch (Exception e){
			System.err.println("There was a problem loading definitions");
		}
	}
	
	public void loadText(String source){
		try{
			File inputFile = new File(source);
			Scanner fileScanner = new Scanner(inputFile);
			String line="";
			while(fileScanner.hasNext()){ // while there is a line that has not been scanned yet. 
				line=fileScanner.nextLine();       //get line
				ArrayList<String> lineArray=new ArrayList<String>(); 
				lineArray.add(line);  
				for(int i=0;i<this.delimiters.size();i++){
					
				}
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
