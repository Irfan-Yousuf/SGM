/***************************************************
 * Creates a graph with vertices and edges
 **************************************************/
package SGM;
import java.util.ArrayList;


public class ReadGraph implements Cloneable{
	
	public ArrayList <Vertex> Vertices;
	public ArrayList <Edge> Edges;
	
	
	public ReadGraph(){
		Vertices = new ArrayList<Vertex>();
		Edges    = new ArrayList<Edge>();
		
	}
	
	public Object clone(){
		 ReadGraph copy = new ReadGraph();
		 Vertex v;
		 Neighbor nr;
		 for(int i=0; i<this.Vertices.size(); i++){
			 v = new Vertex(this.Vertices.get(i).vertexID);
			 copy.Vertices.add(v);
			 for(int j=0; j<this.Vertices.get(i).NeighborList.size(); j++){
				 nr = new Neighbor(this.Vertices.get(i).NeighborList.get(j).neighborID);
				 copy.Vertices.get(i).NeighborList.add(nr);
			 }
		 }
		 copy.Edges = this.Edges; 
		 return copy;
		
	}
	
	public void DisplayGraph(){
				
		System.out.println("Total Vertices= "+this.Vertices.size());
		System.out.println("Total    Edges= "+this.Edges.size());
		
	}
	
}