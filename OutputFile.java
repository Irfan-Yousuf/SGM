/********************************************************
 * Stores the snapshots of an SGM graph in files
 *******************************************************/
package SGM;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


class OutputFile{
	
	BufferedWriter outPutFile = null;

	
	static String inPutPath;
	static String outPutPath;
	
	
	public OutputFile(){
		
		 outPutPath = "D:\\SGM\\";
		
	}
	
	public void openFile(String name){
		
		
		  try {
				outPutFile = new BufferedWriter(new FileWriter(name));
			  } catch (IOException e) {			
				e.printStackTrace();
			  }
	  }
	  
	  public void PrintEdges(ArrayList <Edge> data){
		  Object obj=null;	
		  try{						
			  for(int x=0; x<data.size(); x++){															
					obj = data.get(x).from+" "+data.get(x).to;
					outPutFile.append(obj.toString());							
					outPutFile.newLine();
				} 		
			 }catch(IOException e){
				 System.out.println("Can not Write to File:");
				 e.printStackTrace();
			 }
	  }
	  
	  public void closeFile(){
		  
		  try {
				outPutFile.close();
			} catch (IOException e) {			
				e.printStackTrace();
			} 
		  
	  }
}
