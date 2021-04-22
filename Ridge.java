import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Ridge {
	static int N;
	static ArrayList<Pair> mountains;
	private static void readInput() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("ridge.in"));
			String line = br.readLine();
			String[] ints = line.split(" ");
			N = Integer.parseInt(ints[0]);
			mountains = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				line = br.readLine();
				ints = line.split(" ");
				mountains.add(new Pair(Integer.parseInt(ints[0]),
												Integer.parseInt(ints[1])));
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	static void minCost() throws IOException {
		long [][] min = new long[N][3];
		min[0][0] = 0;
		min[0][1] = mountains.get(0).getC();
		min[0][2] = 2 * mountains.get(0).getC();
		for (int i = 1; i < N; i++) {
			for (int j = 0; j < 3; j++) {
				long minimum = Long.MAX_VALUE / 1000;
				for (int k = 0; k < 3; k++) {
					if ((mountains.get(i).getH() - j != (mountains.get(i - 1).getH() - k))
						&& (mountains.get(i).getH() - j) >= 0) {
						minimum = Math.min(minimum, min[i - 1][k]);
					}
				}
				min[i][j] = j * mountains.get(i).getC() + minimum;
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("ridge.out"));
		writer.write(String.valueOf(Arrays.stream(min[N - 1]).min().getAsLong()));
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		readInput();
		minCost();
	}

	static class Pair {
		long H, C;
		Pair(long h, long cost) {
			this.H = h;
			this.C = cost;
		}

		public long getC() {
			return C;
		}

		public void setC(int c) {
			C = c;
		}

		public long getH() {
			return H;
		}

		public void setH(int h) {
			H = h;
		}
	}
}
