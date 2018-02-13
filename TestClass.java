/***********************************************
 * Test Class to start SGM
 * Can change maximum node and beta value here
 **********************************************/


package SGM;

public class TestClass{
	
	 
	  public static int maxNodes = 1000; 
	  public static double beta = 1.2; 
	  
	  public static void main(String[]args) throws java.io.IOException{		   		    
		    SGM sgm = new SGM();				
		    sgm.startModel(beta, maxNodes);
		    System.out.println("Done...");
	   } 
}