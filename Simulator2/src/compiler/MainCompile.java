package compiler;

public class MainCompile {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Assembler as1 = new Assembler("src/compiler/ByteSourceCode.txt",2);
		as1.run("src/compiler/test1assembly.txt", "destination");
		System.out.println();
		

	}

}
