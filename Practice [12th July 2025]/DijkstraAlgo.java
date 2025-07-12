
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
	 this.adjacentList.put(node, new ArrayList<>());    
  }

  public void addEdge(int node1, int node2, int distance) {
    this.adjacentList.get(node1).add(new NodeWeightPair(node2, distance));
    this.adjacentList.get(node2).add(new NodeWeightPair(node1, distance));
  }

  public List<List<String>> processDijkstraShortestPath(int sourceNode) {
    
      int[] distance = new int[this.numberOfNodes]; // Distance of each node from source node.
      Arrays.fill(distance, Integer.MAX_VALUE); // Assuming the distance of each node from source node is infinity.
      distance[sourceNode] = 0; // Distance from source node to source node is zero(0).

      Queue<NodeWeightPair> minHeap = new PriorityQueue<>((nwp1, nwp2) -> nwp1.weight() - nwp2.weight()); // Min heap priority queue.
      minHeap.add(new NodeWeightPair(sourceNode, 0)); 

      while(!minHeap.isEmpty()) {
      
        NodeWeightPair currentPair = minHeap.remove();
        int currentNode = currentPair.node(); // Currently popped node.
        int distanceOfCurrentNodeFromSourceNode = currentPair.weight();	

        if (distanceOfCurrentNodeFromSourceNode > distance[currentNode]) continue;

	// List of neighbour nodes and their distance from currently popped node from the min heap priority queue.
        List<NodeWeightPair> neighbourPairList = this.adjacentList.get(currentNode); 

	for (NodeWeightPair neighbourPair : neighbourPairList) {
	   
	    int neighbourNode = neighbourPair.node();
	    int distanceOfNeighbourNodeFromCurrentNode = neighbourPair.weight();

	    int distanceOfNeighbourNodeFromSourceNode = distance[currentNode] + distanceOfNeighbourNodeFromCurrentNode; 

            if (distance[neighbourNode] > distanceOfNeighbourNodeFromSourceNode) {
	        distance[neighbourNode] = distanceOfNeighbourNodeFromSourceNode;
	        minHeap.add(new NodeWeightPair(neighbourNode, distance[neighbourNode]));	
	    }
	} 
      
      }

      List<List<String>> result = new ArrayList<>();
      result.add(List.of("Source node = '" + sourceNode + "'")); 
      for (int node = 0; node < this.numberOfNodes; node++) {
           result.add(List.of("Node = '" + node + "'" , "Distance from source node '" + sourceNode + "' = '" + distance[node] + "'"));
      }      
   
      return result;
  }

}


public class DijkstraAlgo {

  public static void main(String[] args) {
     System.out.println("\n --- Dijkstra'a Shortest Path Algorithm --- \n"); 

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

     System.out.print("Enter the source node: ");
     // Processing Dijkstra's shortest path algorithm .....
     List<List<String>> resultList = graph.processDijkstraShortestPath(inputScanner.nextInt());

     inputScanner.close();

     System.out.println("Result:: ");
     for (List<String> result: resultList) System.out.println(result);

     System.out.println("\n");
  }

}
