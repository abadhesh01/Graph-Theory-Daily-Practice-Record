
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

record DirectedGraph (Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {
  
  public DirectedGraph (int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++)
      this.adjacentList.put(node, new ArrayList<>());
  }

  public void addEdge (int node1, int node2) { 
    this.adjacentList.get(node1).add(node2);
  } 

  public boolean hasCycle (int node, boolean[] visited, boolean[] recurrence) {
    visited[node] = true;
    recurrence[node] = true;
    for (int neighbourNode : this.adjacentList.get(node)) {
      if (!visited[neighbourNode] && hasCycle(neighbourNode, visited, recurrence)) return true;
      if (recurrence[neighbourNode]) return true;
    }
    recurrence[node] = false;
    return false;
  } 

  public List<?> containsCycle () {
    boolean[] visited = new boolean[this.numberOfNodes];
    boolean[] recurrence = new boolean[this.numberOfNodes];
    for (int node = 0; node < this.numberOfNodes; node++) {
      if (!visited[node] && hasCycle(node, visited, recurrence))
        return List.of(true, "Cycle Detected!!! The given graph contains a cycle.");
    }
    return List.of(false, "Cycle not found!!! The given graph does not contain a cycle.");
  }

}

public class CDDG {

  public static void main (String[] args) {
  
    System.out.println("\n--- Cycle Detection In Directed Graph ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input.]");

    Scanner keyboardInput = new Scanner(System.in); // Opening the keyboard input...

    System.out.print("\nEnter the number of node(s) in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nEnter the edge(s) in your graph in the format \"node_1 node_2\" respectively ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }

    keyboardInput.close(); // Closing the keybaord input ...

    DirectedGraph graph = new DirectedGraph(numberOfNodes); // Creating a new graph ...
    for (List<Integer> edge : edgeList) graph.addEdge(edge.get(0), edge.get(1)); // Adding edge(s) to the graph ...
    System.out.println("\nOutput ->\n" + graph.containsCycle() + "\n"); // Detecting cycle in the graph and printing the result ...

  }

}
