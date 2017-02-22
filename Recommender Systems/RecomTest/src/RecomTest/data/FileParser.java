package RecomTest.data;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class FileParser {
		private String inp;
		public String out;
		public int it = 1682;
		public int us = 943;
		int matrix[][] = new int [it][us];
		
		
		public FileParser(String inp,String out)
		{
			this.inp = inp; 		
			this.out = out;
			
		}
		/*
		 * To read from file
		 */

		public int[][] ReadMatrix()
		{
			String temp[] =null; 
			File f = new File(this.inp);
			if(f.length()==0)
			{
				System.out.println("File is empty");
			}
			else
			{
			Scanner sc=null;
	    	FileReader fr=null;	
			try{
					fr =new FileReader(f);
					sc = new Scanner(fr);
					String Line = "";
			
					for(int i=0;i<it;i++)
					{
						for(int j=0;j<us;j++)
						{
							matrix[i][j] = 0;
						}
					}
					
					while(sc.hasNextLine()){
						Line = sc.nextLine();
						temp = Line.split(" ");
						matrix[Integer.parseInt(temp[1]) - 1][Integer.parseInt(temp[0]) - 1] = Integer.parseInt(temp[2]);
					}
			}catch(Exception E){
					E.printStackTrace();
					System.out.println("Exception: file operation");
				}
			   finally
			   {
				   sc.close();
			   }
			}
			return matrix;
		}
	}

