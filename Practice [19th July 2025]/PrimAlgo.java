
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;

record Edge (int node, int parentNode, int edgeWeight) {
  
  @Override	
  public String toString () {
    return "[ node: " + this.node + ", parentNode: " + this.parentNode + ", edgeWeight: " + this.edgeWeight + " ]";
  }

}

record NodeWeightPair (int node, int edgeWeight) {}

record Graph (Map<Integer, List<NodeWeightPair>> adjacentList, int numberOfNodes) {
  
  public Graph (int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++)
      this.adjacentList.put(node, new ArrayList<>());
  }

  public void addEdge (int node1, int node2, int edgeWeight) {
    this.adjacentList.get(node1).add(new NodeWeightPair(node2, edgeWeight));
    this.adjacentList.get(node2).add(new NodeWeightPair(node1, edgeWeight));
  }

  public List<Edge> processMinSpanningTree (int initialNode) {
    // initialNode -> The node from which the calculation begins.
    
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of each node visited as parent node.	  
    int[] distance = new int[this.numberOfNodes]; // Distance of each node.
    int[] parent = new int[this.numberOfNodes]; // Parent node of each node.
    Arrays.fill(distance, Integer.MAX_VALUE); // Assume the maximum distance of each node from it's parent node is infinity.
    Arrays.fill(parent, -1); // Assuming each node has same parent i.e. -1.
    distance[initialNode] = 0; // The distance from initial node to initial node is always zero(0).
    
    // Min heap implementation of priority queue accepting 'NodeWeightPair' and sorting them in the order of 'edgeWeight'.
    Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwp1, nwp2) -> nwp1.edgeWeight() - nwp2.edgeWeight());
    minHeap.add(new NodeWeightPair(initialNode, 0)); // The distance from initial node to initial node is always zero(0).
						     
    while (!minHeap.isEmpty()) {
      
      NodeWeightPair poppedPair = minHeap.remove();
      
      int currentNode = poppedPair.node();

      visited[currentNode] = true;

      List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode);
      
      for (NodeWeightPair neighbourPair : neighbourPairList) {
        
	int neighbourNode = neighbourPair.node();
	int distanceOfNeighbourNodeFromCurrentNode = neighbourPair.edgeWeight();
	
	if (!visited[neighbourNode] && (distance[neighbourNode] > distanceOfNeighbourNodeFromCurrentNode)) {
	    distance[neighbourNode] = distanceOfNeighbourNodeFromCurrentNode;
	    parent[neighbourNode] = currentNode;
	    minHeap.add(new NodeWeightPair(neighbourNode, distance[neighbourNode]));
	}
      }

    }
   
    // Processing the output ...
    List<Edge> result = new ArrayList<>();
    for (int node = 0; node < initialNode; node++) 
      result.add(new Edge(node, parent[node], distance[node]));
    for (int node = initialNode + 1; node < this.numberOfNodes; node++)	    
      result.add(new Edge(node, parent[node], distance[node]));
   

    return result;
  }

}

public class PrimAlgo {
  
  public static void main (String[] args) {
  
    System.out.println("\n--- Prim's Minimum Spanning Tree Algorithm ---\n");
    System.out.println("[NOTE - Entering any negative integer at any point of time will close the input.]\n");    

    Scanner inputScanner = new Scanner(System.in);

    System.out.print("Enter the number of nodes in your graph: ");
    int numberOfNodes = inputScanner.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("Add edges to the graph::");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      System.out.print("Enter a new edge [ node_1 node_2 edge_weight ]: " );
      int node1 = inputScanner.nextInt();
      if (node1 < 0) break;
      int node2 = inputScanner.nextInt();
      if (node2 < 0) break;
      int edgeWeight  = inputScanner.nextInt();
      if (edgeWeight < 0) break;
      edgeList.add(List.of(node1, node2, edgeWeight));      
    }

    System.out.print("Enter the initial node: ");
    int initialNode = inputScanner.nextInt();
    if (initialNode < 0) return;

    inputScanner.close();

    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...
					    
    // Adding edges to the graph ...
    for (List<Integer> edge : edgeList) 
      graph.addEdge(edge.get(0), edge.get(1), edge.get(2));

    List<Edge> resultList = graph.processMinSpanningTree(initialNode); // Processing the minimum spanning tree ...
    //List<Edge> resultList = graph.processMinSpanningTree(0); // Processing the minimum spanning tree ...
    
    java.util.Collections.sort(resultList, (e1, e2) -> e1.edgeWeight() - e2.edgeWeight()); // Sorting results in the ascending order of edges ...

    System.out.println("\nMinimum Spanning Tree::"); // Printing the result ...
    for (Edge result : resultList) System.out.println(result);	    
    System.out.println("\n"); 
   
  }

}

