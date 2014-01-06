/**
 * 
 */
package com.xyz.code.compile.rnd.common;

/**
 * @author vrasto1
 * 
 */
public class CodeCompileManagerTest {

	private static String sourceCode1 = "import java.io.File; import java.io.FileOutputStream; import java.io.PrintStream; class Sample1 { public static void main(String[] args) { try { print(); System.out.println(get(\"vibhor\")); } catch (Throwable t) { t.printStackTrace(); } } static void print() { System.out.println(\"hello print vibhor\"); } static String get(String name) { return \"hello get \" + name; } }";

	private static String sourceCode2 = "import java.util.Scanner; public class Sample2 { public static void main(String[] args) { final Scanner in = new Scanner(System.in); final int n = in.nextInt(); in.nextLine(); for (int i = 0; i < n; i++) { String nbrStr = in.nextLine(); String[] nbrStrArr = nbrStr.split(\" \"); int[] nbrArr = new int[nbrStrArr.length]; int j = 0; for (String s : nbrStrArr) { nbrArr[j] = Integer.valueOf(s).intValue(); j++; }	int maxNbr = getMax(nbrArr); System.out.println(maxNbr); } in.close(); } private static int getMax(final int[] nbrArr) { int maxNbr = nbrArr[0]; for (int i : nbrArr) { if (i > maxNbr) { maxNbr = i; } } return maxNbr;} }";

	private static String helloC = "#include<stdio.h> #include<conio.h>\nvoid main() { int i=10/0; printf(\"Hello C World\"); }";

	private static String inputC = "#include<stdio.h>\nint main(void) { int a; scanf(\"%d\", &a); printf(\"%d\", a); return 0; }";

	public static void main(String[] args) {
		test2();
	}

	static void test2() {
		final String result = ExecuteCommand.codeCompileAndExecute(sourceCode2,
				"Sample2", "java");
		System.out.println(result);
	}

	static void test1() {
		final String result = ExecuteCommand.codeCompileAndExecute(sourceCode1,
				"Sample1", "java");
		System.out.println(result);
	}

	static void cTest() {
		final String result = ExecuteCommand.codeCompileAndExecute(helloC,
				"hello", "c");
		System.out.println(result);
	}

	static void cTest2() {
		final String result = ExecuteCommand.codeCompileAndExecute(inputC,
				"a", "c");
		System.out.println(result);
	}
}
