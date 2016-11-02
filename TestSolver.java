import java.awt.List;
import java.util.HashMap;
import java.util.Map;

public class TestSolver {
	int problemID;
	int[][] problem;
	int size;

	long starttime, endtime, timetaken;
	
	long mincost = Long.MAX_VALUE;
	long maxcost = Long.MIN_VALUE;
	static int count=1;
	Map<int[], Long> detcost = new HashMap<int[], Long>();
	
	int[] tourorder, tour;

	TestSolver(int[][] problem, int problemID) {
		this.problemID = problemID;
		this.problem = problem;
		size = problem.length;
		tourorder = new int[size];
		tour = new int[size];
		for (int i = 0; i < size; i++) {
			tourorder[i] = i;
		}

	}
     
	public void solve() {
		starttime = System.currentTimeMillis();
		solve(size, 0);
		endtime = System.currentTimeMillis();
		timetaken = endtime - starttime;
	}
	public void solve(int a, int start) {
		int k;
		long cost = 0;
		if (start < a) {
			
			for (int i = start; i < a; i++) {
				int tem = tourorder[start];
				tourorder[start] = tourorder[i];
				tourorder[i] = tem;
				solve(a, start + 1);
				tem = tourorder[start];
				tourorder[start] = tourorder[i];
				tourorder[i] = tem;
			}
		} else {			
			for (k = 1; k < a; k++) {
				int value = problem[tourorder[k-1]][tourorder[k]];
				cost = cost + value;
			}
		     	cost = cost + problem[tourorder[a-1]][tourorder[0]];
		if (cost > maxcost) {
			maxcost = cost;
		}

		if (cost < mincost) {
			mincost = cost;
			for (int i = 0; i < a; i++) {
     				tour[i] = tourorder[i];
			}
		}
	}

}

	public String toString() {
		String stats = problemID + " " + size + " " + mincost + " " + maxcost + " " + timetaken + System.getProperty("line.separator");
		for(int i=0;i<tour.length;i++) {
			stats = stats + tour[i] + System.getProperty("line.separator");
		}
		return stats;
 	}

}
