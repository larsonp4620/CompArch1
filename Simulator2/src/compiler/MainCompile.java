package compiler;

public class MainCompile {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Assembler as1 = new Assembler("src/compiler/ByteSourceCode.txt",2,0);
		as1.run("src/compiler/test1assembly.txt", "destination");
		String[] s=as1.getAssemblerSet();
		System.out.println();
		as1.extractCalculateFlags();
		System.out.println();
		

	}

}
