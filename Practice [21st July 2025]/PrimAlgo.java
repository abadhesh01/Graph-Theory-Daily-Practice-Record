
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;

record MinEdge (int childNode, int parentNode, int edgeWeight) {

  @Override	
  public String toString () {
    return this.getClass().getName() + " [ Child_Node: '" + childNode + "', Parent_Node: '" + parentNode + "', Edge_Weight: '" + edgeWeight + "' ]";
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

  public List<MinEdge> processMinSpanningTree (int initialNode) {
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of the nodes visited as parent node.	  
    int[] weight = new int[this.numberOfNodes]; // Keeps track of the minimum weight associated with the child node. 
    int[] parent = new int[this.numberOfNodes]; // Keeps track of the parent node associated with the child node.
    Arrays.fill(weight, Integer.MAX_VALUE); // Assume the maximum weight associated with each child node is infinity.
    Arrays.fill(parent, -1); // Fill the parent nodes with -1 as we do not know the parent nodes of the child nodes.
    weight[initialNode] = 0; // The weight associated with initial node to initial node is always zero(0).
    
    // Min heap implementation of priority queue to keep track of mimum weight associated with child node in the ascending order of weight.
    Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwpA, nwpB) -> nwpA.edgeWeight() - nwpB.edgeWeight());
    minHeap.add(new NodeWeightPair(initialNode, 0)); // The weight associated with inital node to initial node is always zero(0).

    while (!minHeap.isEmpty()) {
      NodeWeightPair poppedPair = minHeap.remove();
      int currentNode =  poppedPair.node(); // Potential Parent Node.
  
      visited[currentNode] = true;

      List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode); 

      for (NodeWeightPair neighbourPair : neighbourPairList) {
        int neighbourNode =  neighbourPair.node(); // Child Node.
        int edgeWeightBetweenNeighbourNodeAndCurrentNode = neighbourPair.edgeWeight();
        
	if (!visited[neighbourNode] && (weight[neighbourNode] > edgeWeightBetweenNeighbourNodeAndCurrentNode)) {
	  weight[neighbourNode] = edgeWeightBetweenNeighbourNodeAndCurrentNode;
	  parent[neighbourNode] = currentNode;
	  minHeap.add(new NodeWeightPair(neighbourNode, weight[neighbourNode]));
	}	
      }   
    }

    // Processing the result ...
    List<MinEdge> resultList = new ArrayList<>();
    for (int node = 0; node < initialNode; node++) 
      resultList.add(new MinEdge(node, parent[node], weight[node])); 
    for (int node = initialNode + 1; node < this.numberOfNodes; node++)
      resultList.add(new MinEdge(node, parent[node], weight[node])); 
    
    // Returning the processed result ...
    return resultList;
  }

}

public class PrimAlgo {

  public static void main (String[] args) {
  
    System.out.println("\n--- Prim's Minimum Spanning Tree Algorithm ---\n");
    System.out.println("[ NOTE - Entering any negative number at any point of time will close the input. ]\n");

    Scanner keyboardInput = new Scanner(System.in);

    System.out.print("Enter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("Add edges to the graph::");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      System.out.print("Enter new edge [ node_1 node_2 edge_weight ]: ");
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      int edgeWeight = keyboardInput.nextInt();
      if (edgeWeight < 0) break;
      edgeList.add(List.of(node1, node2, edgeWeight));
    }

    System.out.print("Enter the node from which you want to start your calculation: ");
    int initialNode = keyboardInput.nextInt();

    keyboardInput.close();

    if (initialNode < 0) return;

    // Creting a new graph ...
    Graph graph = new Graph(numberOfNodes);

    // Adding edges to the graph ...
    for (List<Integer> edge : edgeList)
      graph.addEdge(edge.get(0), edge.get(1), edge.get(2));

    // Processing result ...
    List<MinEdge> resultList = graph.processMinSpanningTree(initialNode); 

    // Printing the result ...
    System.out.println("\nMinimum Spanning Tree -> ");
    for (MinEdge result : resultList)
      System.out.println(result);
    System.out.println("\n");

  }

}
