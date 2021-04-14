import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Stocks {
	private static int N, B, L;
	private static ArrayList<Stock> stocks;
	private static int[][][] profit;

	private static void readInput() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("stocks.in"));
			String line = br.readLine();
			String[] ints = line.split(" ");
			N = Integer.parseInt(ints[0]);
			B = Integer.parseInt(ints[1]);
			L = Integer.parseInt(ints[2]);
			stocks = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				line = br.readLine();
				ints = line.split(" ");
				stocks.add(new Stocks.Stock(Integer.parseInt(ints[0]),
						Integer.parseInt(ints[1]), Integer.parseInt(ints[2])));
			}
			profit = new int[N + 1][B + 1][L + 1];
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	public static void createProfitMatrix() {
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= B; j++) {
				for (int k = 0; k <= L; k++) {
					if (i == 0 || j == 0 || k == 0) {
						profit[i][j][k] = 0;
					} else if (j >= stocks.get(i - 1).getCurrentValue()
							&& stocks.get(i - 1).getLoss() <= k) {
						profit[i][j][k] = Math.max(profit[i - 1][j][k],
								profit[i - 1]
										[j - stocks.get(i - 1).getCurrentValue()]
										[k - stocks.get(i - 1).getLoss()]
										+ stocks.get(i - 1).getProfit());
					} else {
						profit[i][j][k] = profit[i - 1][j][k];
					}
				}
			}
		}
	}

	public static int findMax(int[][][] mat) {
		int maxElement = Integer.MIN_VALUE;
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= B; j++) {
				for (int k = 0; k <= L; k++) {
					if (mat[i][j][k] > maxElement) {
						maxElement = mat[i][j][k];
					}
				}
			}
		}
		return maxElement;
	}

	public static void main(String[] args) throws IOException {
		readInput();
		Collections.sort(stocks);
		createProfitMatrix();
		BufferedWriter writer = new BufferedWriter(new FileWriter("stocks.out"));
		writer.write(String.valueOf(findMax(profit)));
		writer.close();
	}

	static class Stock implements Comparable<Stock> {
		private int currentValue, minValue, maxValue;

		Stock(int curr, int min, int max) {
			this.currentValue = curr;
			this.minValue = min;
			this.maxValue = max;
		}

		public int getCurrentValue() {
			return this.currentValue;
		}

		public void setCurrentValue(int currentValue) {
			this.currentValue = currentValue;
		}

		public int getMaxValue() {
			return this.maxValue;
		}

		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}

		public int getMinValue() {
			return this.minValue;
		}

		public void setMinValue(int minValue) {
			this.minValue = minValue;
		}

		public int getLoss() {
			return this.currentValue - this.minValue;
		}

		public int getProfit() {
			return this.maxValue - this.currentValue;
		}

		@Override
		public int compareTo(Stock stock) {
			return this.getCurrentValue() == stock.getCurrentValue()
					? this.getLoss() - stock.getLoss()
					: this.getCurrentValue() - stock.getCurrentValue();
		}
	}

}
