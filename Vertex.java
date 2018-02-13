/**************************************************
 * A node is identified with its ID
 * we also attach a list of its neighbors
 ************************************************/
package SGM;

import java.util.ArrayList;

public class Vertex{

	   int vertexID;	   	   
	   public ArrayList <Neighbor> NeighborList;
	   
	   public Vertex(int ID){
		   vertexID = ID;		
		   NeighborList = new ArrayList<Neighbor>();		   
	   }
}