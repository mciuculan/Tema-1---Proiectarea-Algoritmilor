import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Crypto {

	public static int N, B;
	public static ArrayList<Pair> pairs;
	public static int totalCost = 0, finalPower;

	private static void readInput() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("crypto.in"));
			String line = br.readLine();
			String[] ints = line.split(" ");
			N = Integer.parseInt(ints[0]);
			B = Integer.parseInt(ints[1]);
			pairs = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				line = br.readLine();
				ints = line.split(" ");
				pairs.add(new Pair(Integer.parseInt(ints[0]),
						Integer.parseInt(ints[1])));
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	public static void sort() {
		pairs.sort(Comparator.comparingInt(Pair::getPower));
		finalPower = pairs.get(0).getPower();
	}

	public static void increment() {
		int costPartial = 0;
		while (totalCost < B) {
			for (int i = 0; i < N; i++) {
				if (pairs.get(i).getPower() == finalPower) {
					costPartial += pairs.get(i).getUpgradeCost();
					pairs.get(i).setPower(pairs.get(i).getPower() + 1);
				} else {
					break;
				}
			}
			if ((totalCost + costPartial) <= B) {
				finalPower += 1;
				totalCost += costPartial;
				costPartial = 0;
			} else {
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		readInput();
		sort();
		increment();
		BufferedWriter writer = new BufferedWriter(new FileWriter("crypto.out"));
		writer.write(String.valueOf(finalPower));
		writer.close();
	}

	static class Pair {
		private int power;
		private int upgradeCost;

		Pair(int power, int upgradeCost) {
			this.power = power;
			this.upgradeCost = upgradeCost;
		}

		public int getPower() {
			return power;
		}

		public void setPower(int power) {
			this.power = power;
		}

		public int getUpgradeCost() {
			return upgradeCost;
		}

		public void setUpgradeCost(int upgradeCost) {
			this.upgradeCost = upgradeCost;
		}

		@Override
		public boolean equals(Object o) {
			Pair p = (Pair) o;
			return power == p.getPower() && upgradeCost == p.getUpgradeCost();
		}
	}
}

