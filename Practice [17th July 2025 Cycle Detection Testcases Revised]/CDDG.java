
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

  public boolean hasCycle (int node, boolean[] recurrence, boolean[] visited) {
    visited[node] = true;
    recurrence[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) {
      if ((!visited[neighbourNode] && hasCycle(neighbourNode, recurrence, visited)) || recurrence[neighbourNode]) {
        return true;
      }
    } 
    recurrence[node] = false;
    return false;
  }

  public List<?> detectCycle () {
    boolean[] visited = new boolean[this.numberOfNodes];
    boolean[] recurrence = new boolean[this.numberOfNodes];
    for (int node = 0; node < this.numberOfNodes; node++) {
      if (hasCycle(node, recurrence, visited)) 
        return List.of(true, "[:)] -----> The provided graph is a cyclic graph.");	      
    }
    return List.of(false, "[:(] -----> The provided graph is an acyclic graph.");
  }

} 

public class CDDG {

  public static void main (String[] args) {
  
    System.out.println("\n--- Cycle Detection In Directed Graph ---\n"); 
    System.out.println("[NOTE - Entering any negative integer at any point of time will close the input.]\n");

    Scanner inputScanner = new Scanner(System.in);
    
    System.out.print("Enter the number of nodes in your graph: ");
    int numberOfNodes = inputScanner.nextInt();
    if (numberOfNodes < 0) return;
    
    System.out.println("Add edges to the graph::");
    List<List<Integer>> nodePairList = new ArrayList<>();
    while (true) {
      System.out.print("Enter new edge [ node1 node2 ]: ");	    
      int node1 = inputScanner.nextInt();
      if (node1 < 0) break;
      int node2 = inputScanner.nextInt();
      if (node2 < 0) break;
      nodePairList.add(List.of(node1, node2));
    }
    
    inputScanner.close();

    DirectedGraph graph = new DirectedGraph(numberOfNodes); // Creating a new graph ...

    for (List<Integer> nodePair : nodePairList) 
      graph.addEdge(nodePair.get(0), nodePair.get(1)); // Adding a new edge to the graph ...

    System.out.println("Output::");
    System.out.println(graph.detectCycle() + "\n"); // Computing and printing the result ...    
    
  }

}
