
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

    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of all the visited node(s).
    // NOTE - Mark a node as visited only after adding it to the result and pushing it into the queue.

    Queue<Integer> nodeQueue = new LinkedList<>();
    result.add(sourceNode);
    nodeQueue.add(sourceNode);
    visited[sourceNode] = true;

    while (!nodeQueue.isEmpty()) {
      int node = nodeQueue.remove();	    
      List<Integer> neighbourNodeList = this.adjacentList.get(node);
      for (int neighbourNode : neighbourNodeList) {
        if (visited[neighbourNode]) continue;
	result.add(neighbourNode);
	nodeQueue.add(neighbourNode);
	visited[neighbourNode] = true;
      }
    }

    return result;
  }

  public void traverseDFS (int node, boolean[] visited, List<Integer> result) { // DFS Helper Function
    // NOTE - Only pass univisited node(s) to the function. Mark a node as visited only after adding it to the result.
    result.add(node);
    visited[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);    
    for (int neighbourNode : neighbourNodeList) {
      if (visited[neighbourNode]) continue;
      this.traverseDFS(neighbourNode, visited, result);
    }
  } 

  public List<Integer> traverseDFS (int sourceNode) { // DFS Calling Function
    List<Integer> result = new ArrayList<>();
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of all the visited node(s).
    this.traverseDFS(sourceNode, visited, result);
    return result;
  }

}

public class GraphTraversal {

  public static void main (String[] args) {
 
    System.out.println("\n--- Graph Traversal Algorithms ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking the input will close the input. ]");

    Scanner keyboardInput = new Scanner(System.in);

    System.out.print("\nEnter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nEnter the edge(s) of your graph in the format \"node_1 node_2\" respectively ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }

    System.out.print("\nEnter the source node of your graph: ");
    int sourceNode = keyboardInput.nextInt();
    if (sourceNode < 0) return;

    keyboardInput.close();

    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...
    
    for (List<Integer> edge : edgeList) graph.addEdge(edge.get(0), edge.get(1)); // Adding new edges to the graph ...

    // Computing and printing BFS (Bredth First Search) traversal of the graph ...
    System.out.println("\nBFS (Bredth First Search) Traversal of the given graph: " + graph.traverseBFS(sourceNode));
    // Computing and printing DFS (Depth First Search) traversal of the graph ...
    System.out.println("\nDFS (Depth First Search) Traversal of the given graph: " + graph.traverseDFS(sourceNode));
    
    System.out.println("\n");

  }

}

