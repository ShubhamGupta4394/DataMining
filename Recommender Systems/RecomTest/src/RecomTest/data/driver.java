package RecomTest.data;


public class driver {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileParser fp = new FileParser("train_all_txt.txt","output.txt");
		int[][] matrix = fp.ReadMatrix();
		Similar sim = new Similar(matrix,fp,fp.out);
		sim.CosSimilar(matrix);
		sim.show();
	}


}
