
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

  public List<Integer> traverseByBFS (int sourceNode) {
    List<Integer> result = new ArrayList<>();

    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of visited node(s).
    Queue<Integer> nodeQueue = new LinkedList<>();
    nodeQueue.add(sourceNode); // Adding the source node to the freshly created queue.
    visited[sourceNode] = true; // Marking the source node as visited. (New Line 1.)

    while (!nodeQueue.isEmpty()) {
      int node = nodeQueue.remove();
      result.add(node);
      //visited[node] = true; // Never mark here after removing the node from the queue.
      List<Integer> neighbourNodeList = this.adjacentList.get(node);
      for (int neighbourNode : neighbourNodeList) {
        if (visited[neighbourNode]) continue;
	nodeQueue.add(neighbourNode);
	visited[neighbourNode] = true; // Marking the neighbour node as visited. (New Line 2.)
      }
    }

    return result;
  }

  public void traverseByDFS (int node, boolean[] visited, List<Integer> result) /* DFS helper function. */ {
    // NOTE - Only pass unvisited node.
    result.add(node);
    visited[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) {
      if (visited[neighbourNode]) continue;
      this.traverseByDFS(neighbourNode, visited, result);
    }
  }

  public List<Integer> traverseByDFS (int sourceNode) /* DFS calling function. */ {
    List<Integer> result = new ArrayList<>();
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of visited node(s).
    this.traverseByDFS(sourceNode, visited, result);
    return result;
  }

}

public class CommonGraphTraversal {

  public static void main (String[] args) {
  
    System.out.println("\n--- Traversing Through The Graph ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input. ]");

    Scanner keyboardInput = new Scanner(System.in);

    System.out.print("\nEnter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nAdd edge(s) to the graph ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      System.out.print("Eneter a new edge [ node_1 node_2 ]: "); 	    
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }    

    System.out.print("\nEnter the source node of your graph: ");
    int sourceNode = keyboardInput.nextInt();
    if (sourceNode < 0) return;

    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...

    // Adding edge(s) to the graph ...
    for (List<Integer> edge : edgeList) 
      graph.addEdge(edge.get(0), edge.get(1)); 

    // BFS (Bredth First Search) traversal of the graph ...
    System.out.println("\nBFS (Bredth First Search) traversal of the graph: " + graph.traverseByBFS(sourceNode));

    // DFS (Depeth First Search) traversal of the graph ...
    System.out.println("\nDFS (Depeth First Search) traversal of the graph: " + graph.traverseByDFS(sourceNode));    

    System.out.println("\n");
  }

}
