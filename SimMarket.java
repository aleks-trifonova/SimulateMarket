//Computer Science 182
// Instructor: Professor Barnes
//Project 1
//Aleksandra Trifonova, Carlos Hernandez
//SimMarket.java

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class SimMarket {

	// loads text file into model

	private static void load(Sample m) {
		Scanner inFile = null;
		String fileName = "sp500weekly.txt";
		do {

			try { // to attach a file to inFile
				inFile = new Scanner(new FileInputStream(fileName));
			} catch (FileNotFoundException e) { // handle file not found
				System.out.println("error: file \"" + fileName);
			}
		} while (inFile == null);
		String line = "";
		while (inFile.hasNextLine()) {
			line += inFile.nextLine() + " ";

		}
		parseLine(line, m);
	}

	private static void parseLine(String line, Sample m) {
		int i;

		String[] word = line.split("\\s");

		for (i = 0; i < word.length; i++) {
			m.addData(Double.parseDouble(word[i]));
		}

	}

	// changed this to void so it got rid of the return statement so that it doesn't return a double and stores data into dataList.
	public static void simulate(Sample m, Sample e) {
		double inv = 100.00;
		double value = 0.0;
		Random r = new Random();
		for (int j = 0; j < 1000; j++) {
			inv = 100.00;
			for (int i = 0; i < 3548; i++) {
				value = m.getData(r.nextInt(m.dataList.size()));
				inv = (value * inv) + inv;
			}

			e.addData(inv);
		}
	}

	public static void main(String[] args) {


		Sample equity = new Sample();
		Sample model = new Sample();

		load(model);

		model.computeStats();

		System.out.println(model + "\n");
		for(int i = 0; i < 10; i++) {
			simulate(model, equity);
			equity.computeStats();
			System.out.println(equity + "\n");
			equity = new Sample();

		}
	}
}