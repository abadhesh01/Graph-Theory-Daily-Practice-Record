
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;

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

  public int[] processShortestPath (int sourceNode) {
    int[] distance = new int[this.numberOfNodes]; // Distance of each node from source node.
    Arrays.fill(distance, Integer.MAX_VALUE); // Assuming the minimum distance beween each node and source node is infinity.
    distance[sourceNode] = 0; // Distance between source node to source node is always zero(0).

    // Min heap implementation of priority queue to keep pairs of node and it's distance from source node in the sorted order of distance.
    Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwp1, nwp2) -> nwp1.edgeWeight() - nwp2.edgeWeight());
    minHeap.add(new NodeWeightPair(sourceNode, 0)); // Distance between source node to source node is always zero(0).

    while (!minHeap.isEmpty()) {
      NodeWeightPair poppedPair = minHeap.remove();
      int currentNode = poppedPair.node();

      List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode);
      
      for (NodeWeightPair neighbourPair : neighbourPairList) {
        int neighbourNode = neighbourPair.node();
	int distanceFromNeighbourNodeToCurrentNode = neighbourPair.edgeWeight();
	int distanceFromNeighbourNodeToSourceNode = distanceFromNeighbourNodeToCurrentNode + distance[currentNode];

        if (distance[neighbourNode] > distanceFromNeighbourNodeToSourceNode) {
	  distance[neighbourNode] = distanceFromNeighbourNodeToSourceNode;
	  minHeap.add(new NodeWeightPair(neighbourNode, distance[neighbourNode]));
	}	
      }   
    }
  
    return distance;	    
  }

}

public class DijkstraAlgo {

  public static void main (String[] args) {
  
    System.out.println("\n--- Dijkstra's Shortest Path Algorithm ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input. ]");

    Scanner keyboardInput = new Scanner(System.in); // Opening the keyboard input.

    System.out.print("\nEnter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nEnter the edge(s) of the graph in the following format \"node_1 node_2 edge_weight\" respectively ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      int edgeWeight = keyboardInput.nextInt();
      if (edgeWeight < 0) break;
      edgeList.add(List.of(node1, node2, edgeWeight));
    }

    System.out.print("\nEnter the source node of your graph: ");
    int sourceNode = keyboardInput.nextInt();    
    keyboardInput.close(); // Closing the keyboard input.
    if (sourceNode < 0) return;

    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...

    for (List<Integer> edge : edgeList) graph.addEdge(edge.get(0), edge.get(1), edge.get(2)); // Adding edges to the graph ...

    // Computing and printing the shortest distance between each node and source node ..
    System.out.println("\nShortest distance of each node from source node '" + sourceNode + "' ->");
    int[] result = graph.processShortestPath(sourceNode); 
    for (int node = 0; node < numberOfNodes; node++) System.out.println("Distance of node '" + node + "': " + result[node]);
    System.out.println("\n");
  
  }

}

