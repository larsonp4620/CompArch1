package compiler;

import java.util.LinkedList;

public class MainCompile {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		Assembler as1 = new Assembler("src/compiler/ByteSourceCode.txt",2,0);
//		as1.run("src/compiler/assemblyfile1");
//		String[] s=as1.getAssemblerSet();
//		System.out.print("");
		Compiler comp1 = new Compiler();
		comp1.parser("src/compiler/Delimiters", "src/compiler/Sample_C_Code");
		LinkedList<String> result =comp1.semicolonify();
		System.out.println(result.toString());
		System.out.println("");
	}
}