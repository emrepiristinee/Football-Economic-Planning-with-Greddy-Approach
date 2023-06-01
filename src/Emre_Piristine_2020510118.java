import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Emre_Piristine_2020510118 {

	public static void main(String[] args) throws FileNotFoundException {
		int[] yearArray = new int[fileCounter("yearly_player_demand.txt")];
		int[] salaryArray = new int[fileCounter("players_salary.txt")];
		fileOperation("yearly_player_demand.txt", yearArray);
		fileOperation("players_salary.txt", salaryArray);
		int player = 5, coach = 5;

		long startTime = System.nanoTime();
		System.out.println("Greedy Results: " + Greedy(yearArray.length - 1, player, coach, yearArray, salaryArray));
		long estimatedTime = System.nanoTime();
		System.out.println("nanoTime: " + (estimatedTime - startTime));
	}

	public static int Greedy(int n, int p, int c, int[] year, int[] salary) {
		int totalCost = 0;
		int maxPlayer = maxPlayer(year);
		int[][] greedyArray = new int[n + 1][maxPlayer];
		int[][] path = new int[n + 1][maxPlayer];

		for (int i = 1; i <= year[0]; i++) { // first year calculate
			if (i <= p)
				greedyArray[1][i] = 0;
			else if (year[0] > p)
				greedyArray[1][i] = greedyArray[1][i - 1] + c;
			else if (year[0] < p) {
				greedyArray[1][i] = salary[i - year[0] - 1];
			}
		}
		path[1][year[0]] = greedyArray[1][year[0]];

		int i = 2, j = 1;
		for (i = 2; i < n; i++) { // calculate without first year
			int demandPlayer = year[i - 1];
			for (j = 1; j <= demandPlayer; j++) {
				if (j <= p)
					greedyArray[i][j] = 0;
				else if (j >= p && p < demandPlayer) {
					greedyArray[i][j] = greedyArray[i][j - 1] + (1 * c);
				}
			}
			path[i][demandPlayer] = greedyArray[i][demandPlayer];
		}

		// cost calculate with path
		for (int y = 0; y < n; y++) {
			for (int players = 0; players < maxPlayer; players++)
				totalCost = totalCost + path[y][players];
		}
		return totalCost;
	}

	public static void fileOperation(String fileName, int[] array) throws FileNotFoundException {
		String[] splitWords = null;
		File Obj1 = new File(fileName);
		Scanner Reader = new Scanner(Obj1);
		int counter = 0;
		splitWords = Reader.nextLine().split("\t"); // to pass the first line
		while (Reader.hasNextLine()) {
			splitWords = Reader.nextLine().split("\t");
			array[counter] = Integer.parseInt(splitWords[1]);
			counter++;
		}
		Reader.close();
	}

	public static int fileCounter(String fileName) throws FileNotFoundException {
		File Obj1 = new File(fileName);
		Scanner Reader = new Scanner(Obj1);
		int counter = 0;
		Reader.nextLine().split("\t"); // to pass the first line
		while (Reader.hasNextLine()) {
			Reader.nextLine().split("\t");
			counter++;
		}
		Reader.close();
		return counter + 1;
	}

	public static int maxPlayer(int[] year) { // most wanted player counter
		int maxPlayer = 0;
		for (int i = 0; i < year.length; i++)
			if (year[i] > maxPlayer)
				maxPlayer = year[i];

		return maxPlayer + 1;
	}
}
