import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TestDataReader {
	public ArrayList<int[][]> read(File f) {
		ArrayList<int[][]> prob = new ArrayList<int[][]>();
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				int no = sc.nextInt();
				int[][] arr = new int[no][no];
				for (int i = 0; i < no; i++) {
					for (int j = 0; j < no; j++) {
						int a = sc.nextInt();
						int b = sc.nextInt();
						arr[i][j] = sc.nextInt();
					}
				}
				prob.add(arr);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return prob;

	}
}

