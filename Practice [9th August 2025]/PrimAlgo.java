
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;

record Edge (int node1, int node2, int edgeWeight) {

  @Override	
  public String toString () {
    return "<< Node_1: '" + node1 + "', Node_2: '" + node2 + "', Edge_Weight: '" + edgeWeight + "' >>";
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

  public List<Edge> computeMinSpanningTree(int initialNode) {
    int[] parent = new int[this.numberOfNodes]; // Keeps track of the parent node(s).	  
    int[] weight = new int[this.numberOfNodes]; // Keeps track of edge weight shared with parent node(s). 
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of visited node(s).
						
    Arrays.fill(parent, -1); // Filling all the parents as '-1' because we do not know who are the parents.
    Arrays.fill(weight, Integer.MAX_VALUE); // Assuming the maximum edge weight shared with each node as infinity. 
    weight[initialNode] = 0; // The weight between initial node and initial node is always zero(0).

    // Min heap implementation of priority queue to sort node and edge weight pair in the order of edge weight.
    Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwp1, nwp2) -> nwp1.edgeWeight() - nwp2.edgeWeight());
    minHeap.add(new NodeWeightPair(initialNode, 0)); // The weight between initial node and initial node is always zero(0).
    
    while (!minHeap.isEmpty()) {
      NodeWeightPair poppedPair = minHeap.remove();
      int currentNode = poppedPair.node();

      visited[currentNode] = true;
      List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode);

      for (NodeWeightPair neighbourPair : neighbourPairList) {
        int neighbourNode = neighbourPair.node();
	int edgeWeight = neighbourPair.edgeWeight();

	if (!visited[neighbourNode] && weight[neighbourNode] > edgeWeight) {
	  weight[neighbourNode] = edgeWeight;
	  parent[neighbourNode] = currentNode;
	  minHeap.add(new NodeWeightPair(neighbourNode, weight[neighbourNode]));
        }
      }
    }

    // Processing and returning the results ...
    List<Edge> resultList = new ArrayList<>();
    for (int node = 0; node < initialNode; node++) resultList.add(new Edge(node, parent[node], weight[node]));
    for (int node = initialNode + 1; node < this.numberOfNodes; node++) resultList.add(new Edge(node, parent[node], weight[node]));

    return resultList;
  }

}

public class PrimAlgo {
  
  public static void main (String[] args) {
  
    System.out.println("\n--- Prim's Minimum Spanning Tree Algorithm ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input. ]");

    Scanner keyboardInput = new Scanner(System.in); // Opening then keyboard input ...

    System.out.print("\nEnter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nEnter the edge(s) of your graph in the order \"node_1 node_2 edge_weight\" respectively -> ");
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

    keyboardInput.close(); // Closing the keyboard input ...

    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...

    for (List<Integer> edge : edgeList) graph.addEdge(edge.get(0), edge.get(1), edge.get(2)); // Adding edge(s) to the graph ...

    // Computing and printing minimum spanning tree ...
    System.out.println("\nMinimum Spanning Tree ->");
    List<Edge> resultList = graph.computeMinSpanningTree(0);
    for (Edge result : resultList) System.out.println(result);
    System.out.println("\n");

  }

}

