/***************************************
 * Main implementation of SGM
 * Node Step and Edge Step
 * Saves a snapshot every 5000 time stamps
 ************************************************/
package SGM;

import java.util.ArrayList;
import java.util.Random;


class SGM{
	
	public ReadGraph initGraph(ReadGraph myGraph){
		int startID=1;
		for(int i=0; i<3; i++){
			myGraph.Vertices.add(new Vertex(startID));
			startID++;
		}
		myGraph.Edges.add(new Edge(myGraph.Vertices.get(0).vertexID, myGraph.Vertices.get(1).vertexID));
		myGraph.Edges.add(new Edge(myGraph.Vertices.get(0).vertexID, myGraph.Vertices.get(2).vertexID));
		myGraph.Edges.add(new Edge(myGraph.Vertices.get(1).vertexID, myGraph.Vertices.get(2).vertexID));

		//make Neighbors
		makeNeighbors(myGraph, myGraph.Vertices.get(0).vertexID, myGraph.Vertices.get(1).vertexID);
		makeNeighbors(myGraph, myGraph.Vertices.get(0).vertexID, myGraph.Vertices.get(2).vertexID);
		makeNeighbors(myGraph, myGraph.Vertices.get(1).vertexID, myGraph.Vertices.get(2).vertexID);
		
		myGraph.DisplayGraph();
		return myGraph;
	}
	
	
	public void startModel(double beta, int maxNodes){
		ReadGraph myGraph = new ReadGraph();
		myGraph = initGraph(myGraph);
		OutputFile out = new OutputFile();
		int maxEdges=0, e=0, v=0, graphNum=1;
		int nodeID = myGraph.Vertices.size();
		maxNodes = maxNodes - 3; //subtract initial graph size
		int interval  = 500;
		
		for(int i=0; i<maxNodes; i++){
			nodeID = nodeID + 1;
			e = myGraph.Edges.size();
			v = myGraph.Vertices.size();
			
			maxEdges = (int) (java.lang.Math.ceil((beta*e)/v));	
			maxEdges = maxEdges - 1; // we consume one edge in node step	
			nodeStep(myGraph, nodeID);		
			edgeStep(myGraph, maxEdges); //edge step	
			
			if((i+1)%1000 == 0) System.out.println("Network Size: "+(i+1));
			if((myGraph.Vertices.size())%interval == 0) {
				System.out.println("Temporal Graph: TG = "+graphNum);
				out.openFile(OutputFile.outPutPath+"TempGraph_TG"+graphNum+".txt");
				out.PrintEdges(myGraph.Edges);	
				out.closeFile();
				myGraph.DisplayGraph();
				graphNum++;
			}
		}

	}
	/************************************************************************
	 * Node Step
	 * A new node is added to the network. It connects with an existing node
	 * using preferential attachment rule.
	 * @param myGraph
	 * @param nodeX
	 ***********************************************************************/
	public void nodeStep(ReadGraph myGraph, int nodeX){
		
		int vDeg=0, vID=0, nodeY=0;
		ArrayList<Vertex> verList = new ArrayList<Vertex>();
		Random rnd = new Random();
		
		//Make list of vertices according to their degree
		for(int i=0; i<myGraph.Vertices.size(); i++){
			vDeg = myGraph.Vertices.get(i).NeighborList.size();
			vID  = myGraph.Vertices.get(i).vertexID;
			for(int j=0; j<vDeg; j++){
				verList.add(new Vertex(vID));
			}
		}
		nodeY = rnd.nextInt(verList.size()); //nodeY index
		nodeY = verList.get(nodeY).vertexID; //nodeY ID
		myGraph.Vertices.add(new Vertex(nodeX));
		myGraph.Edges.add(new Edge(nodeX,nodeY));
		makeNeighbors(myGraph,nodeX, nodeY);
	}
	
	/******************************************************************
	 * Edge Step
	 * NodeX is picked u.a.r. from G
	 * NodeY is a friend of NodeX and picked using Preferential Attachment
	 * NodeZ is a friend of X and picked u.a.r.
	 * An edge is created between NodeY and Z
	 * @param myGraph
	 * @param maxEdges
	 ******************************************************************/
	public void edgeStep(ReadGraph myGraph, int maxEdges){
		
		int indexNodeX=0,degNodeX=0;
		int friendX=0, indexfriendX=0, degFriendX=0;
		int nodeY=0, nodeZ=0;
		
		ArrayList<Vertex> friendsX = new ArrayList<Vertex>();
		Random rnd = new Random();
		
		for(int k=0; k<maxEdges; k++){
			indexNodeX = rnd.nextInt(myGraph.Vertices.size());
			degNodeX = myGraph.Vertices.get(indexNodeX).NeighborList.size();
			if(degNodeX < 2) {k=k-1; continue;}
			for(int i=0; i<degNodeX; i++){ //Preferential Attachment
				friendX = myGraph.Vertices.get(indexNodeX).NeighborList.get(i).neighborID;
				indexfriendX = friendX-1; //index is -1 of node ID
				degFriendX = myGraph.Vertices.get(indexfriendX).NeighborList.size();
				for(int j=0; j<degFriendX; j++){
					friendsX.add(new Vertex(friendX));
				}
			}
			nodeY = rnd.nextInt(friendsX.size()); //nodeY index
			nodeY = friendsX.get(nodeY).vertexID; //nodeY ID
			
			do{
				nodeZ = rnd.nextInt(degNodeX);
				nodeZ = myGraph.Vertices.get(indexNodeX).NeighborList.get(nodeZ).neighborID;
			}while(nodeY == nodeZ);
			
			
			myGraph.Edges.add(new Edge(nodeY, nodeZ));
			makeNeighbors(myGraph, nodeY, nodeZ);
			friendsX.clear();
		}
	}
	
	public void makeNeighbors(ReadGraph myGraph, int node1, int node2){
		int index1= node1-1;
		int index2= node2-1;
		
		myGraph.Vertices.get(index1).NeighborList.add(new Neighbor(node2));
		myGraph.Vertices.get(index2).NeighborList.add(new Neighbor(node1));
	}
	
}