
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;

record NodeWeightPair(int node, int weight) {}

record Graph(Map<Integer, List<NodeWeightPair>> adjacentList, int numberOfNodes) {

  public Graph(int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++) 
	 adjacentList.put(node, new ArrayList<>());    
  }

  public void addEdge(int node1, int node2, int distance) {
    this.adjacentList.get(node1).add(new NodeWeightPair(node2, distance));
    this.adjacentList.get(node2).add(new NodeWeightPair(node1, distance));
  }  

  public List<List<String>> processPrimsMinSpanningTree() {
   
    int initialNode = 0; // This node can be any node between 0 to (numberOfNodes - 1) inclusive.

    int[] distance = new int[this.numberOfNodes]; // Distance shared with any parent node.
    int[] parent = new int[this.numberOfNodes]; // Parent node.
            
    Arrays.fill(distance, Integer.MAX_VALUE); // Assuminmg the maximum possible distance as infinity.
    Arrays.fill(parent, -1);
    distance[initialNode] = 0; // Distance of initial node from initial node is zero(0).

    Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwp1, nwp2) -> nwp1.weight() - nwp2.weight()); // Min heap priority queue.
   
    minHeap.add(new NodeWeightPair(initialNode, 0)); 

    while (!minHeap.isEmpty()) {

      NodeWeightPair currentPair = minHeap.remove();
      int currentNode = currentPair.node();
    
      List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode);
    
      for (NodeWeightPair neighbourPair : neighbourPairList) {
	  int neighbourNode = neighbourPair.node();
	  int distanceOfNeighbourNodeFromCurrentNode = neighbourPair.weight();
         
	  if (distance[neighbourNode] > distanceOfNeighbourNodeFromCurrentNode) {
	      distance[neighbourNode] = distanceOfNeighbourNodeFromCurrentNode;
	      parent[neighbourNode] = currentNode;
	      minHeap.add(new NodeWeightPair(neighbourNode, distance[neighbourNode]));
	  }
      }
    }

    List<List<String>> result = new ArrayList<>();
    for (int node = 0; node < initialNode; node++)
	 result.add(List.of("Node: " + node, "Parent_Node: " + parent[node], "Edge: " + distance[node])); 
    for (int node = initialNode + 1; node < this.numberOfNodes; node++)   
         result.add(List.of("Node: " + node, "Parent_Node: " + parent[node], "Edge: " + distance[node]));

    return result;
  }
  	
}

public class PrimsAlgo {
  
  public static void main(String[] args) {
    
    System.out.println("\n --- Prim's Minimum Spanning Tree Algorithm ---\n"); 	

     Scanner inputScanner = new Scanner(System.in);

     System.out.print("Enter the number of nodes: ");
     int numberOfNodes = inputScanner.nextInt();

     Graph graph = new Graph(numberOfNodes); // Creating a new graph .....

     System.out.print("Enter the number of relations to be entered: ");
     int numberOfRelations = inputScanner.nextInt();

     while (numberOfRelations-- > 0) {
       System.out.print("Enter [node_1, node_2, distance]: ");
       int node1 = inputScanner.nextInt();
       int node2 = inputScanner.nextInt();
       int distance = inputScanner.nextInt();
       graph.addEdge(node1, node2, distance); // Adding new edge to the graph .....
     }

     inputScanner.close();

     // Processing Prim's minimum spanning tree algorithm .....
     List<List<String>> resultList = graph.processPrimsMinSpanningTree();

     System.out.println("Result::");
     for (List<String> result : resultList)  System.out.println(result);   

     System.out.println("\n");  
                                                  
  }

}
