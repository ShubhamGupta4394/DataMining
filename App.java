import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class App {
			
	public static void main(String[] args) throws IOException {
		if(args.length == 0) {
			System.out.println("Run the program as : java App <inputfile>");
			return;
		}
                String outputFile = "output.txt";
                if(args.length>1) {
			outputFile = args[1];
		}	
		File f = new File(args[0]);
		TestDataReader testdatareader = new TestDataReader();
		ArrayList<int[][]> prblm = testdatareader.read(f);
		PrintWriter out = new PrintWriter(outputFile,"UTF-8" );
		int i = 1;
		for (int[][] temp : prblm) {
			TestSolver testsolver = new TestSolver(temp, i);
			testsolver.solve();
			i++;
			System.out.print(testsolver.toString());
                 out.print( testsolver.toString() );
    		out.flush();
		}
		
		out.close();
	}
}
