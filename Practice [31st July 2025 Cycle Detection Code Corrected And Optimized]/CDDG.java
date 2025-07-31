
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
    this.adjacentList.get(node1).add(node2); // Error prone.
  }

  public boolean hasCycle (int node, boolean[] visited, boolean[] recurrence) {
    visited[node] = true;
    recurrence[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) {
      if (!visited[neighbourNode] && hasCycle(neighbourNode, visited, recurrence)) return true;
      if (recurrence[neighbourNode]) return true;
    }
    recurrence[node] = false; // Error prone.
    return false;
  }

  public List<?> detectCycle () {
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of the visted node(s).
    boolean[] recurrence = new boolean[this.numberOfNodes]; // Keeps track of the recurring node(s).
    for (int node  = 0; node < this.numberOfNodes; node++) {
      //if (hasCycle(node, visited, recurrence)) // [OLD LINE]
      if (!visited[node] && hasCycle(node, visited, recurrence))  // [NEW LINE]
        return List.of(true, "Cycle found!!! -----> The given graph is a cyclic graph.");
    } 
    return List.of(false, "Cycle not found!!! -----> The given graph is an acyclic graph.");
  } 

}

public class CDDG {

  public static void main (String[] args) {
  
    System.out.println("\n--- Cycle Detection In Directed Graph ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input. ]");
 
    Scanner keyboardInput = new Scanner(System.in);

    System.out.print("\nEnter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nEnter the edge(s) of graph in the format \"node_1 node_2\" ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }

    keyboardInput.close();

    DirectedGraph graph = new DirectedGraph(numberOfNodes); // Creating a new graph ...

    // Adding edge(s) to the graph ...
    for (List<Integer> edge : edgeList)
      graph.addEdge(edge.get(0), edge.get(1));

    // Computing and printing` the result ...
    System.out.println("\nResult ->\n" + graph.detectCycle() + "\n");

  }

}

