package RecomTest.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Similar {
	FileParser fp = null;
	public int[][] matrix;
	private Map<Integer,ArrayList<Integer>> list;
	int us=0;
	int it=0;
	int ne = 60;
	public String out;
	double cosdistance[][];

	/*
	 * Constructor
	 */
	
	public class NodeL implements Comparable{
		double val = 0;
		int pos = 0;
		
		public NodeL(int pos, double val) {
			this.pos = pos;
			this.val = val;
		}

		@Override
		public int compareTo(Object o) {
			NodeL n = (NodeL) o;
			double comp = ((NodeL)n).val;
			double result = comp - this.val;
			if(result > 0.00001) return 1;
			if(result < -0.00001) return -1;
			return 0;
		}

		

	}

	



	public Similar(int[][] matrix, FileParser fp, String out) {
		this.matrix = matrix;
		list = new LinkedHashMap<Integer,ArrayList<Integer>>();
		this.it = fp.it;
		this.us = fp.us;
		this.out = out;
		cosdistance = new double[us][us];
	}
	



	/*
	 * To find Similarity and Neighbors of users
	 */
	public void CosSimilar(int[][]  matrix){
		
			for(int j=0;j<us;j++){
				for(int i=0;i<it;i++)
				{
				if(matrix[i][j] == 0)
				{
					if(!list.containsKey(j+1)){
						list.put(j+1, new ArrayList<Integer>());
						list.get(j+1).add(i+1);
					}
					else
						list.get(j+1).add(i+1);
				}
			}
		}

		CosDistance();

		//Finding nearest neighbours
		Map<Integer,ArrayList<NodeL>> listNei = new LinkedHashMap<Integer,ArrayList<NodeL>>(); ;
		NodeL nl=null;
		for(int i=0;i<us;i++)
		{
			for(int j=0;j<us;j++)
			{
					nl= new NodeL(j, cosdistance[i][j]);
					if(!listNei.containsKey(i)){
						listNei.put(i,new ArrayList<NodeL>());
						listNei.get(i).add(nl);
					}
					else{
						listNei.get(i).add(nl);
					}
			}
		}
		
		//Finding Nearest Neighbours
		Map<Integer,ArrayList<Integer>> listNef = new LinkedHashMap<Integer,ArrayList<Integer>>();
		for (Map.Entry<Integer, ArrayList<NodeL>> entry : listNei.entrySet()) {
			Integer k = entry.getKey();
			ArrayList<NodeL> val = entry.getValue();
			Collections.sort(val);
			ArrayList<Integer>  listNet = new ArrayList<Integer>();
			Iterator<NodeL> iter = val.iterator();
			while(iter.hasNext())
			{
			    NodeL n = iter.next();
			    listNet.add(n.pos);
			}

			ArrayList<Integer> listNel = new ArrayList<>(listNet.subList(0, ne));
			listNef.put(k, new ArrayList<Integer>());
			
			for(int m=0;m < listNel.size();m++){
				listNef.get(k).add(listNel.get(m));
			}
			ArrayList<Integer> value1 = listNef.get(k);
		}
			
		form(listNef,cosdistance);
	}
	/*
	 *To calculate the prediction for the user-item pair by performing a weighted average of deviations from the neighbor's mean 
	 */
	public void form(Map<Integer,ArrayList<Integer>> listNef, double cosdistance[][]){
		
		//Average rating for each user
		double[] avgratallUs =new double[us];
		int c =0;
				for(int i=0;i<us;i++)
				{
					int s =0;
					c=0;
					for(int j=0;j<it;j++)
					{
						if(matrix[j][i] != 0){
						s = s + matrix[j][i];
						c++;
						}
					}
					avgratallUs[i] = s/c;
				}
		
		//Weighted Formula for prediction	
		for (Map.Entry<Integer, ArrayList<Integer>> ent : list.entrySet()) {
			double Rat=0;
			double Num=0;
			double weightsum = 0;
			double predval = 0;
			Integer ke = ent.getKey();
			ArrayList<Integer> val = ent.getValue();
			for(Integer t : val){
				double Ratmul=0;
				ArrayList<Integer> totneikey = new ArrayList<Integer>();
				totneikey  = listNef.get(ke-1);
				for(Integer temp : totneikey){

					if(matrix[t-1][temp]!=0){
						Rat= matrix[t-1][temp] - avgratallUs[temp];
						Ratmul += Rat* cosdistance[ke-1][temp];
						weightsum += cosdistance[ke-1][temp];
					}
					
				}
				Num = Ratmul;
				predval  =  avgratallUs[ke-1] + Num/weightsum;
				matrix[t-1][ke-1] = (int) Math.round(predval);
			}

		} 
			
	}

	/*
	 * To find Cosined distances between users
	 */
	public void CosDistance(){
		for(int i=0;i<us;i++){
			for(int j=0;j<us;j++){
				cosdistance[i][j] = -1;
			}
		}
		
		//Calculate Numerator
		double num[] = new double[us];
		for(int i=0;i<us;i++){
			num[i] = 0;
			for(int j=0;j<it;j++){
				num[i] += matrix[j][i] * matrix[j][i];
			}
			num[i] = Math.sqrt(num[i]);
		}

		//Calculate Denominator and final Cosined distance
		double dprod=0;
		for(int i=0;i<us;i++)
		{
			for(int j=i+1;j<us;j++)
			{
				dprod  = 0;
				for(int z=0;z<it;z++)
				{
					if(cosdistance[i][j] == -1)
						dprod += matrix[z][i] * matrix[z][j]; 
				}

				double val = (num[i] * num[j]);
				cosdistance[i][j] = dprod/val;
				cosdistance[j][i] = dprod/val;
				
			}
		}
	}

	/*
	 * To display the output to the file
	 */
	public void show(){
		BufferedWriter bw =null;
		try {
			bw = new BufferedWriter(new FileWriter(out));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			for(int i=0;i<us;i++){
				for(int j = 0;j<it;j++){
				try {
					String u = Integer.toString(i+1);
					String itm = Integer.toString(j+1);
					int temp = matrix[j][i];
					if(temp==0)
						temp=1;
					String v = Integer.toString(temp);
					bw.write(u);
					bw.write(" ");
					bw.write(itm);
					bw.write(" ");
					bw.write(v);
					bw.newLine();
					bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

	}
}
