
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;

record Distance (int node1, int node2, int distance) {

  @Override	
  public String toString () {
    return "[ The distance from Node-'" + node1 + "' to Node-'" + node2 + "' is '" + distance + "'. ]";
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

  public List<Distance> processShortestPath (int sourceNode) {
    int[] distance = new int[this.numberOfNodes]; // Keeps track of the distance of each node from source node.
    Arrays.fill(distance, Integer.MAX_VALUE); // Assume the maximum distance of each node from source node is infinity.
    distance[sourceNode] = 0; // The distance from source node to source node is always zero(0).

    // Min heap implementation of priority queue to keep track of nodes and their distance from source node
    // in the ascending order of distance where 'node' represents node and 'edgeWeight' represents distance from source node.
    Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwp1, nwp2) -> nwp1.edgeWeight() - nwp2.edgeWeight());
    minHeap.add(new NodeWeightPair(sourceNode, 0)); // The distance from source node to source node is always zero(0).

    while (!minHeap.isEmpty()) {
      NodeWeightPair poppedPair = minHeap.remove();
      
      int currentNode = poppedPair.node();
      
      List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode);

      for (NodeWeightPair neighbourPair : neighbourPairList) {
        int neighbourNode = neighbourPair.node();
	int neighbourToCurrentDistance = neighbourPair.edgeWeight(); // Distance between neighbour node and current node.
	
	int neighbourToSourceDistance = distance[currentNode] + neighbourToCurrentDistance; // Distance between neighbour node and source node.
	
	if (distance[neighbourNode] > neighbourToSourceDistance) {
	  distance[neighbourNode] = neighbourToSourceDistance;
	  minHeap.add(new NodeWeightPair(neighbourNode, distance[neighbourNode]));
	}
      }
    }

    // Processing the result ...
    List<Distance> resultList = new ArrayList<>();
    for (int node = 0; node < this.numberOfNodes; node++) 
      resultList.add(new Distance(sourceNode, node, distance[node]));	

    // Returning the result ...    
    return resultList;
  }

}

public class DijkstraAlgo {

  public static void main (String[] args) {
  
    System.out.println("\n--- Dijkstra's Shortest Path Algorithm  ---\n");
    System.out.println("[ NOTE - Entering any negative number at any point of time will close the input. ]\n");

    Scanner keyboardInput = new Scanner(System.in); // Creating and opening keyboard input ,,,

    System.out.print("Enter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("Add new edges to the graph::");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      System.out.print("Enter a new node [ node_1 node_2 edge_weight ]: ");
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      int edgeWeight = keyboardInput.nextInt();
      if (edgeWeight < 0) break;
      edgeList.add(List.of(node1, node2, edgeWeight)); 
    } 

    System.out.print("Enter the source node: ");
    int sourceNode = keyboardInput.nextInt();
    keyboardInput.close(); // Closing keyboard input ...
    if (sourceNode < 0) return;

    // Craetinga new graph ...
    Graph graph = new Graph(numberOfNodes);  

    // Adding edge(s) to the graph ...
    for (List<Integer> edge : edgeList)
      graph.addEdge(edge.get(0), edge.get(1), edge.get(2));   

    // Computing result through Dijkstra's shortest path algorithm ...
    List<Distance> resultList = graph.processShortestPath(sourceNode);

    // Printing the results ...
    System.out.println("\nMinimum distance of each node from source node '" + sourceNode + "' ->");
    for (Distance result : resultList)
      System.out.println(result);
    System.out.println("\n");
    
  }

}
