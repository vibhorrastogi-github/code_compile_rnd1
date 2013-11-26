import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

class OldSample {
	static String fileName = "sample";

	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream(new FileOutputStream(new File(fileName + ".out"))));
			System.out.println("start");
			print();
			int a=1/0;
			System.out.println(get("vibhor"));
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	static void print() {
		System.out.println("hello print vibhor");
	}

	static String get(String name) {
		return "hello get " + name;
	}
}