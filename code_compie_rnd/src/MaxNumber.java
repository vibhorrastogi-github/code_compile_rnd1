import java.util.Scanner;

public class MaxNumber {
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);
		final int n = in.nextInt();
		in.nextLine();
		for (int i = 0; i < n; i++) {
			String nbrStr = in.nextLine();
			String[] nbrStrArr = nbrStr.split(" ");
			int[] nbrArr = new int[nbrStrArr.length];
			int j = 0;
			for (String s : nbrStrArr) {
				nbrArr[j] = Integer.valueOf(s).intValue();
				j++;
			}
			int maxNbr = getMax(nbrArr);
			System.out.println(maxNbr);
		}
		in.close();
	}

	private static int getMax(final int[] nbrArr) {
		int maxNbr = nbrArr[0];
		for (int i : nbrArr) {
			if (i > maxNbr) {
				maxNbr = i;
			}
		}
		return maxNbr;
	}
}