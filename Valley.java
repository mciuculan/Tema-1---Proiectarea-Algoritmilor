import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Valley {
	public static int N;
	public static long minHours = 0;
	public static ArrayList<Integer> sir = new ArrayList<>();

	private static void readInput() throws IOException {
		try {
			String strCurrentLine;
			BufferedReader objReader = new BufferedReader(new FileReader("valley.in"));
			int i = 0;
			while ((strCurrentLine = objReader.readLine()) != null) {
				if (i == 0) {
					N = Integer.parseInt(strCurrentLine);
					i++;
				} else {
					String[] ints = strCurrentLine.split(" ");
					for (String elem : ints) {
						sir.add(Integer.parseInt(elem));
					}
				}
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	public static boolean checkEqual() {
		for (int i = 1; i < N; i++) {
			if (!sir.get(i).equals(sir.get(0))) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkValley(ArrayList<Integer> sir) {
		if (checkEqual()) {
			return true;
		}
		int min = Collections.min(sir);
		int minIndex = sir.indexOf(min);
		for (int i = 0; i < minIndex; i++) {
			if (sir.get(i) < sir.get(i + 1)) {
				return false;
			}
		}
		for (int i = minIndex; i < N - 1; i++) {
			if (sir.get(i) > sir.get(i + 1)) {
				return false;
			}
		}
		if ((minIndex == 0) || (minIndex == N - 1)) {
			ascDsc();
			return true;
		}
		return true;
	}

	public static void ascDsc() {
		if (sir.get(0) - sir.get(1) < 0) {
			if ((sir.get(1) - sir.get(0)) < (sir.get(N - 1) - sir.get(N - 2))) {
				minHours += sir.get(1) - sir.get(0);
			} else {
				minHours += sir.get(N - 1) - sir.get(N - 2);
			}
		} else {
			if ((sir.get(0) - sir.get(1)) < (sir.get(N - 2) - sir.get(N - 1))) {
				minHours += sir.get(0) - sir.get(1);
			} else {
				minHours += sir.get(N - 2) - sir.get(N - 1);
			}
		}
	}

	public static void divide() {
		int localMin = Collections.min(sir);
		int localMinIndex = sir.indexOf(localMin);
		prefix(localMinIndex);
		sufix(localMinIndex);
	}

	public static void prefix(int minGlobalIndex) {
		int localMin = sir.get(0);
		if (minGlobalIndex == N - 1) {
			minHours += sir.get(N - 2) - sir.get(minGlobalIndex);
			sir.set(N - 2, sir.get(minGlobalIndex));
		}
		for (int i = 0; i <= minGlobalIndex; i++) {
			if (localMin > sir.get(i)) {
				localMin = sir.get(i);
			} else {
				minHours += sir.get(i) - localMin;
				sir.set(i, localMin);
			}
		}
	}

	public static void sufix(int minGlobalIndex) {
		int localMax = sir.get(sir.size() - 1);
		if (minGlobalIndex == 0) {
			minHours += sir.get(1) - sir.get(minGlobalIndex);
			sir.set(1, sir.get(minGlobalIndex));
		}
		for (int i = sir.size() - 1; i >= minGlobalIndex; i--) {
			if (localMax > sir.get(i)) {
				localMax = sir.get(i);
			} else {
				minHours += sir.get(i) - localMax;
				sir.set(i, localMax);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		readInput();
		BufferedWriter writer = new BufferedWriter(new FileWriter("valley.out"));
		if (!checkValley(sir)) {
			divide();
		}
		writer.write(String.valueOf(minHours));
		writer.close();
	}
}

