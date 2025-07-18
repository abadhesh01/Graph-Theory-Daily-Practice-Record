
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

record Distance (int node1, int node2, int shortestDistance) {

  public String toString () {
    return "[ node1 = " + node1 + ", node2 = " + node2 + ", shortestDistance = " + shortestDistance + " ]";
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

  public List<Distance> computeShortestPath (int sourceNode) {

    int[] distance = new int[this.numberOfNodes]; // Keeps track of the distance of each node from source node.
    Arrays.fill(distance, Integer.MAX_VALUE); // Assume the maximum distance is infinity.
    distance[sourceNode] = 0; // The distance from source node to source node is always zero(0).

    // Min Heap implementation of priority queue. 
    Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwp1, nwp2) -> nwp1.edgeWeight() - nwp2.edgeWeight()); 
    minHeap.add(new NodeWeightPair(sourceNode, 0)); // The distance from source node to source node is always zero(0).

    while (!minHeap.isEmpty()) {

      NodeWeightPair poppedPair = minHeap.remove();
      
      int currentNode = poppedPair.node();
      //int distanceOfCurrentNodeFromSourceNode = poppedPair.edgeWeight(); // Not required.
      
      //if (distanceOfCurrentNodeFromSourceNode > distance[currentNode]) continue; // Bullshit. :(
      
      List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode);  
      
      for (NodeWeightPair neighbourPair : neighbourPairList) {  
	
	int neighbourNode = neighbourPair.node();
	int distanceOfNeighbourNodeFromCurrentNode = neighbourPair.edgeWeight();

	int distanceOfNeighbourNodeFromSourceNode = distance[currentNode] + distanceOfNeighbourNodeFromCurrentNode;

	if (distance[neighbourNode] > distanceOfNeighbourNodeFromSourceNode) {
	  distance[neighbourNode] = distanceOfNeighbourNodeFromSourceNode;
	  minHeap.add(new NodeWeightPair(neighbourNode, distance[neighbourNode]));
	}
           
      }

    }

    // Processing and returning result ...
    List<Distance> resultList = new ArrayList<>();
    for (int node = 0; node < this.numberOfNodes; node++)
      resultList.add(new Distance(sourceNode,  node, distance[node])); 

    return resultList;

  }

}

public class DijkstraAlgo {

  public static void main (String[] args) {

    System.out.println("\n--- Dijkstra's Shortest Path Algorithm ---\n");
    System.out.println("[NOTE - Entering any negative number at any point of time will close the input.]\n");    
    
    Scanner inputScanner = new Scanner(System.in);

    System.out.print("Enter the number of nodes in your graph: ");
    int numberOfNodes = inputScanner.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("Add edges to the graph::");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      System.out.print("Enter new edge [ node_1 node_2 edge_weight ]: ");	    
      int node1 = inputScanner.nextInt();
      if (node1 < 0) break;
      int node2 = inputScanner.nextInt();
      if (node2 < 0) break;
      int edgeWeight = inputScanner.nextInt();
      if (edgeWeight < 0) break;      
      edgeList.add(List.of(node1, node2, edgeWeight));
    }

    System.out.print("Enter the source node: ");
    int sourceNode = inputScanner.nextInt();
    if (sourceNode < 0) return;

    inputScanner.close();

    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...

    for (List<Integer> edge : edgeList) // Adding new edges to the graph ...
      graph.addEdge(edge.get(0), edge.get(1), edge.get(2));

    List<Distance> resultList = graph.computeShortestPath(sourceNode); // Computing result ...

    // Printing result ...
    System.out.println("\n[Output]::");
    for (Distance result : resultList) System.out.println(result);
    System.out.println("\n");

  }

}  
