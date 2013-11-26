class Sample {
	static String fileName = "sample";

	public static void main(String[] args) {
		try {
			print();
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