
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

record UndirectedGraph (Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {

  public UndirectedGraph (int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++) 
      this.adjacentList.put(node, new ArrayList<>());
  }

  public void addEdge (int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    this.adjacentList.get(node2).add(node1);
  }

  public boolean hasCycle (int node, int parentNode, boolean[] visited) {
    visited[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) {
      if (visited[neighbourNode]) {
        if (neighbourNode != parentNode) return true;
      } else {
        if (hasCycle(neighbourNode, node, visited)) return true;
      }
    }
    return false;
  }

  public List<?> detectCycle () {
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of all the visited node(s). [NEW LINE 1]
    for (int node = 0; node < this.numberOfNodes; node++) {
      //boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of all the visited node(s). [OLD LINE 1]
      //if (hasCycle(node, -1, visited)) // [OLD LINE 2]
      if (!visited[node] && hasCycle(node, -1, visited)) // [NEW LINE 2]
        return List.of(true, "Cycle found!!! -----> The given graph is a cyclic graph.");
    }
    return List.of(false, "Cycle not found!!! -----> The given graph is an acyclic graph.");
  }

}

public class CDUG {

  public static void main (String[] args) {
  
    System.out.println("\n--- Cycle Detection In Undirected Graph ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input. ]");

    Scanner keyboardInput = new Scanner(System.in);

    System.out.print("\nEnter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nEnter the edge(s) of the graph in the format \"node_1 node_2\" ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }

    keyboardInput.close();

    UndirectedGraph graph = new UndirectedGraph(numberOfNodes); // Creating a new graph ...

    // Adding edge(s) to the graph ...
    for (List<Integer> edge : edgeList)
      graph.addEdge(edge.get(0), edge.get(1));

    // Computing and printing the output ...
    System.out.println("\nResult ->\n" + graph.detectCycle() + "\n");
     
  }

}

