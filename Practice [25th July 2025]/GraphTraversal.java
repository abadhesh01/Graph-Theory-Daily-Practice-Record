
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

record Graph (Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {

  public Graph (int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++)
      this.adjacentList.put(node, new ArrayList<>()); 
  }

  public void addEdge (int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    this.adjacentList.get(node2).add(node1);
  }

  public List<Integer> traverseBFS (int sourceNode) {
    List<Integer> result = new ArrayList<>();
	 
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of visited node as soon as they are touched.
    Queue<Integer> nodeQueue = new LinkedList<>();
   
    visited[sourceNode] = true;
    result.add(sourceNode);
    nodeQueue.add(sourceNode);
    
    while (!nodeQueue.isEmpty()) {
      int nodeCount = nodeQueue.size();

      while (nodeCount-- > 0) {
        int node = nodeQueue.remove();
        List<Integer> neighbourNodeList = this.adjacentList.get(node);
       
       	for (int neighbourNode : neighbourNodeList) {
          if (visited[neighbourNode]) continue;
          visited[neighbourNode] = true;
	  result.add(neighbourNode);
 	  nodeQueue.add(neighbourNode);
        } 
      }
    }

    return result;
  }

  public void traverseDFS(int node, boolean[] visited, List<Integer> result) { // Main DFS traversal function.
    result.add(node);
    visited[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) {
      if (visited[neighbourNode]) continue;
      this.traverseDFS(neighbourNode, visited, result);  
    }  
  } 

  public List<Integer> traverseDFS (int sourceNode) { // Calling DFS traversal function.
    List<Integer> result = new ArrayList<>();
    boolean[] visited = new boolean[this.numberOfNodes]; // keeps track of visited node as current / parent node.
    this.traverseDFS(sourceNode, visited, result);
    return result;
  }

}

public class GraphTraversal {

  public static void main (String[] args) {
  
    System.out.println("\n--- Graph Traversal Algirithms ---\n");
    System.out.println("[ NOTE - Entering any negative integer while taking integer input at any point of time will close the input. ]\n");
    System.out.println("Enter the number of nodes in your graph first and then enter the edge(s) 'node_1 node_2' respectively:");

    Scanner keyboardInput = new Scanner(System.in);

    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }

    System.out.print("\nEnter the source node: ");
    int sourceNode = keyboardInput.nextInt();
    if (sourceNode < 0) return;

    keyboardInput.close();

    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...

    // Adding edge(s) to the graph ...
    for (List<Integer> edge : edgeList)
      graph.addEdge(edge.get(0), edge.get(1));

    // Taversing BFS and DFS and printing the result ... 
    System.out.println("\nBFS (/ Bredth First Search) for source node '" + sourceNode + "': " + graph.traverseBFS(sourceNode));
    System.out.println("\nDFS (/ Depth First Search) for source node '" + sourceNode + "': " + graph.traverseDFS(sourceNode));   
    System.out.println("\n");

  }  

}

